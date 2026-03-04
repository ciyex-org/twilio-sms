package org.ciyex.sms.config;

import org.ciyex.sms.security.JwtAuthenticationFilter;
import org.ciyex.sms.security.KeycloakJwtAuthenticationConverter;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired(required = false)
    @Nullable
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private KeycloakJwtAuthenticationConverter keycloakJwtAuthenticationConverter;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${cors.allowed-origins:http://localhost:3000}")
    private String corsAllowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/sms/health/**",
                    "/api/sms/webhooks/**",
                    "/actuator/**"
                ).permitAll()
                .requestMatchers("/api/sms/test-connection").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth -> oauth
                .jwt(jwt -> jwt
                    .decoder(combinedJwtDecoder())
                    .jwtAuthenticationConverter(keycloakJwtAuthenticationConverter)
                )
            );

        if (jwtAuthenticationFilter != null) {
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        }

        return http.build();
    }

    @Bean
    public JwtDecoder combinedJwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        NimbusJwtDecoder hmacDecoder = NimbusJwtDecoder.withSecretKey(secretKey).build();

        NimbusJwtDecoder keycloakDecoder;
        try {
            keycloakDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);
        } catch (Exception e) {
            return hmacDecoder;
        }

        final NimbusJwtDecoder kcDecoder = keycloakDecoder;
        return token -> {
            try {
                return kcDecoder.decode(token);
            } catch (Exception e) {
                return hmacDecoder.decode(token);
            }
        };
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        for (String origin : corsAllowedOrigins.split(",")) {
            config.addAllowedOriginPattern(origin.trim());
        }
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
