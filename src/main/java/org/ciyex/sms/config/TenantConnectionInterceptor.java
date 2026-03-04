package org.ciyex.sms.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class TenantConnectionInterceptor extends OncePerRequestFilter {

    private final JdbcTemplate jdbcTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String orgAlias = request.getHeader("X-Org-Alias");

        if (orgAlias != null && !orgAlias.isBlank()) {
            setTenant(orgAlias);
        } else {
            setTenant("__SYSTEM__");
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            clearTenant();
        }
    }

    public void setTenant(String orgAlias) {
        jdbcTemplate.execute("SET LOCAL app.current_org = '" + sanitize(orgAlias) + "'");
    }

    public void clearTenant() {
        jdbcTemplate.execute("RESET app.current_org");
    }

    private String sanitize(String value) {
        return value.replaceAll("[^a-zA-Z0-9_\\-]", "");
    }
}
