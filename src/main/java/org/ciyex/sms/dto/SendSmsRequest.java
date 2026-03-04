package org.ciyex.sms.dto;

public record SendSmsRequest(
        String to,
        String body,
        String patientId
) {}
