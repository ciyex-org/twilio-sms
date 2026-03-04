package org.ciyex.sms.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.Map;

@Service
@Slf4j
public class CredentialService {

    private final RestClient restClient;
    private final String ciyexApiUrl;

    private final Cache<String, TwilioCredentials> cache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(Duration.ofMinutes(5))
            .build();

    public CredentialService(RestClient restClient,
                             @Value("${ciyex.platform.api-url}") String ciyexApiUrl) {
        this.restClient = restClient;
        this.ciyexApiUrl = ciyexApiUrl;
    }

    public TwilioCredentials resolve(String orgAlias) {
        TwilioCredentials cached = cache.getIfPresent(orgAlias);
        if (cached != null) return cached;

        Map<String, Object> config = fetchConfig(orgAlias);
        if (config == null || !config.containsKey("twilio_account_sid")) {
            throw new IllegalStateException("Twilio not configured for org: " + orgAlias +
                    ". Please configure Twilio credentials in Settings.");
        }

        TwilioCredentials creds = new TwilioCredentials(
                (String) config.get("twilio_account_sid"),
                (String) config.get("twilio_auth_token"),
                (String) config.get("twilio_from_number"),
                (String) config.get("twilio_messaging_service_sid")
        );
        cache.put(orgAlias, creds);
        return creds;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> fetchConfig(String orgAlias) {
        try {
            Map<String, Object> response = restClient.get()
                    .uri(ciyexApiUrl + "/api/app-installations/twilio-sms")
                    .header("X-Org-Alias", orgAlias)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            if (response != null && response.get("data") instanceof Map<?, ?> data) {
                return (Map<String, Object>) data.get("config");
            }
            return null;
        } catch (Exception e) {
            log.error("Failed to fetch Twilio config for org {}: {}", orgAlias, e.getMessage());
            return null;
        }
    }

    public void invalidate(String orgAlias) {
        cache.invalidate(orgAlias);
    }

    public record TwilioCredentials(
            String accountSid,
            String authToken,
            String fromNumber,
            String messagingServiceSid
    ) {}
}
