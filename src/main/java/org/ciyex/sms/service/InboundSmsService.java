package org.ciyex.sms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ciyex.sms.model.SmsMessage;
import org.ciyex.sms.repository.SmsMessageRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class InboundSmsService {

    private final SmsMessageRepository smsMessageRepository;

    public void processInbound(Map<String, String> params) {
        String from = params.get("From");
        String to = params.get("To");
        String body = params.get("Body");
        String messageSid = params.get("MessageSid");

        var entity = SmsMessage.builder()
                .orgAlias("__SYSTEM__") // Will be resolved by matching the "To" number
                .direction("INBOUND")
                .toNumber(to)
                .fromNumber(from)
                .body(body != null ? body : "")
                .status("RECEIVED")
                .twilioSid(messageSid)
                .build();
        smsMessageRepository.save(entity);

        log.info("Inbound SMS received from {} to {}, SID={}", from, to, messageSid);
    }
}
