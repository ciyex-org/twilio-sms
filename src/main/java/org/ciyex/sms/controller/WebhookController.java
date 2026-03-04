package org.ciyex.sms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ciyex.sms.model.DeliveryEvent;
import org.ciyex.sms.repository.DeliveryEventRepository;
import org.ciyex.sms.repository.SmsMessageRepository;
import org.ciyex.sms.service.InboundSmsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sms/webhooks")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

    private final InboundSmsService inboundSmsService;
    private final SmsMessageRepository smsMessageRepository;
    private final DeliveryEventRepository deliveryEventRepository;

    @PostMapping(value = "/inbound", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> inbound(@RequestParam Map<String, String> params) {
        log.info("Twilio inbound webhook: From={}, To={}", params.get("From"), params.get("To"));
        inboundSmsService.processInbound(params);
        // Return empty TwiML response
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML)
                .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response></Response>");
    }

    @PostMapping(value = "/status", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> statusCallback(@RequestParam Map<String, String> params) {
        String messageSid = params.get("MessageSid");
        String status = params.get("MessageStatus");

        log.info("Twilio status callback: SID={}, status={}", messageSid, status);

        if (messageSid != null) {
            // Update message status
            smsMessageRepository.findByTwilioSid(messageSid).ifPresent(msg -> {
                msg.setStatus(status != null ? status.toUpperCase() : msg.getStatus());
                smsMessageRepository.save(msg);
            });

            // Record delivery event
            smsMessageRepository.findByTwilioSid(messageSid).ifPresent(msg -> {
                var event = DeliveryEvent.builder()
                        .messageId(msg.getId())
                        .status(status != null ? status.toUpperCase() : "UNKNOWN")
                        .rawPayload(Map.copyOf(params))
                        .build();
                deliveryEventRepository.save(event);
            });
        }

        return ResponseEntity.ok().build();
    }
}
