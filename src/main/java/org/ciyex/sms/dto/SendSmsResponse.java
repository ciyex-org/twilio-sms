package org.ciyex.sms.dto;

import java.util.UUID;

public record SendSmsResponse(
        UUID id,
        String twilioSid,
        String status
) {}
