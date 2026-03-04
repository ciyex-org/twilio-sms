package org.ciyex.sms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (SecurityContextHolder.getContext().getAuthentication() != null
                    && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Map<String, Object> claims = parseJwtPayload(token);

                if (claims != null && !claims.isEmpty()) {
                    String username = (String) claims.getOrDefault("preferred_username", claims.get("email"));

                    @SuppressWarnings("unchecked")
                    List<String> groups = (List<String>) claims.getOrDefault("groups", List.of());
                    List<SimpleGrantedAuthority> authorities = groups.stream()
                            .map(group -> new SimpleGrantedAuthority("ROLE_" + group))
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication", e);
        }
        filterChain.doFilter(request, response);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseJwtPayload(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            if (parts.length < 2) return Map.of();
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(payload, Map.class);
        } catch (Exception e) {
            return Map.of();
        }
    }
}
