package org.ciyex.sms.dto;

import org.ciyex.sms.model.DeliveryEvent;
import org.ciyex.sms.model.SmsMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SmsMessageDto(
        UUID id,
        String direction,
        String toNumber,
        String fromNumber,
        String body,
        String status,
        String twilioSid,
        String patientId,
        String createdBy,
        LocalDateTime createdAt,
        List<DeliveryEventDto> deliveryEvents
) {

    public record DeliveryEventDto(
            UUID id,
            String status,
            LocalDateTime createdAt
    ) {}

    public static SmsMessageDto from(SmsMessage msg, List<DeliveryEvent> events) {
        return new SmsMessageDto(
                msg.getId(), msg.getDirection(), msg.getToNumber(), msg.getFromNumber(),
                msg.getBody(), msg.getStatus(), msg.getTwilioSid(), msg.getPatientId(),
                msg.getCreatedBy(), msg.getCreatedAt(),
                events != null ? events.stream().map(e -> new DeliveryEventDto(e.getId(), e.getStatus(), e.getCreatedAt())).toList() : List.of()
        );
    }

    public static SmsMessageDto from(SmsMessage msg) {
        return from(msg, null);
    }
}
