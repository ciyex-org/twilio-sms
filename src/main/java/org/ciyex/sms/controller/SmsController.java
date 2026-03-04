package org.ciyex.sms.controller;

import lombok.RequiredArgsConstructor;
import org.ciyex.sdk.notifications.NotificationProvider;
import org.ciyex.sdk.notifications.NotificationProvider.NotificationRequest;
import org.ciyex.sms.dto.SendSmsRequest;
import org.ciyex.sms.dto.SendSmsResponse;
import org.ciyex.sms.dto.SmsMessageDto;
import org.ciyex.sms.repository.DeliveryEventRepository;
import org.ciyex.sms.repository.SmsMessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class SmsController {

    private final NotificationProvider notificationProvider;
    private final SmsMessageRepository smsMessageRepository;
    private final DeliveryEventRepository deliveryEventRepository;

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestHeader("X-Org-Alias") String orgAlias,
                                  @RequestBody SendSmsRequest request) {
        var result = notificationProvider.send(new NotificationRequest(
                orgAlias, "SMS", request.to(), null, null, request.body(),
                null, null, null,
                request.patientId() != null ? Map.of("patientId", request.patientId()) : null
        ));

        var msg = smsMessageRepository.findByTwilioSid(result.notificationId());
        if (msg.isPresent()) {
            return ResponseEntity.ok(new SendSmsResponse(msg.get().getId(), result.notificationId(), result.status()));
        }
        return ResponseEntity.ok(Map.of("notificationId", result.notificationId(), "status", result.status()));
    }

    @PostMapping("/send-batch")
    public ResponseEntity<?> sendBatch(@RequestHeader("X-Org-Alias") String orgAlias,
                                       @RequestBody List<SendSmsRequest> requests) {
        var notifRequests = requests.stream()
                .map(r -> new NotificationRequest(orgAlias, "SMS", r.to(), null, null, r.body(),
                        null, null, null,
                        r.patientId() != null ? Map.of("patientId", r.patientId()) : null))
                .toList();

        var result = notificationProvider.sendBatch(notifRequests);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/messages")
    public ResponseEntity<Page<SmsMessageDto>> listMessages(
            @RequestHeader("X-Org-Alias") String orgAlias,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String patientId) {

        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<SmsMessageDto> result;

        if (patientId != null && !patientId.isBlank()) {
            result = smsMessageRepository.findByOrgAliasAndPatientId(orgAlias, patientId, pageable)
                    .map(SmsMessageDto::from);
        } else {
            result = smsMessageRepository.findByOrgAlias(orgAlias, pageable)
                    .map(SmsMessageDto::from);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> getMessage(@RequestHeader("X-Org-Alias") String orgAlias,
                                        @PathVariable UUID id) {
        var msg = smsMessageRepository.findByIdAndOrgAlias(id, orgAlias);
        if (msg.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var events = deliveryEventRepository.findByMessageIdOrderByCreatedAtDesc(id);
        return ResponseEntity.ok(SmsMessageDto.from(msg.get(), events));
    }

    @GetMapping("/delivery-status/{twilioSid}")
    public ResponseEntity<?> deliveryStatus(@PathVariable String twilioSid) {
        var status = notificationProvider.getDeliveryStatus(twilioSid);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/test-connection")
    public ResponseEntity<?> testConnection(@RequestBody Map<String, String> config) {
        var status = notificationProvider.testConnection(config);
        return status.connected()
                ? ResponseEntity.ok(Map.of("connected", true, "message", status.message()))
                : ResponseEntity.badRequest().body(Map.of("connected", false, "message", status.message()));
    }
}
