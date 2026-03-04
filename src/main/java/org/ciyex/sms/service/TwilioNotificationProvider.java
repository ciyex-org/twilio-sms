package org.ciyex.sms.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.http.TwilioRestClient;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ciyex.sdk.notifications.NotificationProvider;
import org.ciyex.sms.model.SmsMessage;
import org.ciyex.sms.repository.SmsMessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwilioNotificationProvider implements NotificationProvider {

    private final CredentialService credentialService;
    private final SmsMessageRepository smsMessageRepository;

    @Value("${webhook.base-url:}")
    private String webhookBaseUrl;

    @Override
    public String vendorId() {
        return "twilio";
    }

    @Override
    public SendResult send(NotificationRequest request) {
        var creds = credentialService.resolve(request.orgAlias());
        var client = new TwilioRestClient.Builder(creds.accountSid(), creds.authToken()).build();

        String fromNumber = request.from() != null ? request.from() : creds.fromNumber();

        var creator = Message.creator(
                new PhoneNumber(request.to()),
                new PhoneNumber(fromNumber),
                request.body()
        );

        if (webhookBaseUrl != null && !webhookBaseUrl.isBlank()) {
            creator.setStatusCallback(URI.create(webhookBaseUrl + "/api/sms/webhooks/status"));
        }

        Message msg = creator.create(client);

        var entity = SmsMessage.builder()
                .orgAlias(request.orgAlias())
                .direction("OUTBOUND")
                .toNumber(request.to())
                .fromNumber(fromNumber)
                .body(request.body())
                .status("SENT")
                .twilioSid(msg.getSid())
                .patientId(request.metadata() != null ? request.metadata().get("patientId") : null)
                .createdBy(request.metadata() != null ? request.metadata().get("createdBy") : null)
                .build();
        smsMessageRepository.save(entity);

        log.info("SMS sent to {} via Twilio, SID={}, org={}", request.to(), msg.getSid(), request.orgAlias());
        return new SendResult(msg.getSid(), "SENT", "SMS", Instant.now());
    }

    @Override
    public BatchResult sendBatch(List<NotificationRequest> requests) {
        int queued = 0;
        int failed = 0;
        List<String> failedRecipients = new java.util.ArrayList<>();

        for (NotificationRequest request : requests) {
            try {
                send(request);
                queued++;
            } catch (Exception e) {
                failed++;
                failedRecipients.add(request.to());
                log.error("Failed to send SMS to {}: {}", request.to(), e.getMessage());
            }
        }

        return new BatchResult(requests.size(), queued, failed, failedRecipients);
    }

    @Override
    public DeliveryStatus getDeliveryStatus(String notificationId) {
        var msgOpt = smsMessageRepository.findByTwilioSid(notificationId);
        if (msgOpt.isEmpty()) {
            return new DeliveryStatus(notificationId, "UNKNOWN", Instant.now(), "Message not found");
        }
        var msg = msgOpt.get();
        return new DeliveryStatus(notificationId, msg.getStatus(),
                msg.getUpdatedAt() != null ? msg.getUpdatedAt().atZone(java.time.ZoneOffset.UTC).toInstant() : Instant.now(), null);
    }

    @Override
    public List<InboundMessage> listInbound(String orgAlias, String channel, int limit, int offset) {
        var page = smsMessageRepository.findByOrgAlias(orgAlias,
                org.springframework.data.domain.PageRequest.of(offset / Math.max(limit, 1), Math.max(limit, 1)));
        return page.getContent().stream()
                .filter(m -> "INBOUND".equals(m.getDirection()))
                .map(m -> new InboundMessage(m.getId().toString(), m.getFromNumber(), m.getToNumber(),
                        m.getBody(), "SMS",
                        m.getCreatedAt().atZone(java.time.ZoneOffset.UTC).toInstant(), false))
                .toList();
    }

    @Override
    public SenderIdentity registerSender(String orgAlias, String channel, String identifier) {
        return new SenderIdentity(identifier, channel, identifier, "VERIFIED", orgAlias);
    }

    @Override
    public ConnectionStatus testConnection(Map<String, String> config) {
        try {
            String accountSid = config.get("twilio_account_sid");
            String authToken = config.get("twilio_auth_token");
            if (accountSid == null || authToken == null) {
                return new ConnectionStatus(false, "Account SID and Auth Token are required");
            }

            var client = new TwilioRestClient.Builder(accountSid, authToken).build();
            com.twilio.rest.api.v2010.Account.fetcher().fetch(client);
            return new ConnectionStatus(true, "Connected to Twilio");
        } catch (Exception e) {
            return new ConnectionStatus(false, "Connection failed: " + e.getMessage());
        }
    }
}
