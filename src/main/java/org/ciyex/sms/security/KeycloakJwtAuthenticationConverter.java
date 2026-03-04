package org.ciyex.sms.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Set<String> roles = new HashSet<>();

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.get("roles") instanceof Collection) {
            roles.addAll((Collection<String>) realmAccess.get("roles"));
        }

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null && resourceAccess.get("ciyex-app") instanceof Map) {
            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("ciyex-app");
            if (clientAccess.get("roles") instanceof Collection) {
                roles.addAll((Collection<String>) clientAccess.get("roles"));
            }
        }

        if (jwt.hasClaim("role")) {
            roles.add(jwt.getClaimAsString("role"));
        }

        List<String> groups = jwt.getClaimAsStringList("groups");
        if (groups != null) {
            roles.addAll(groups);
        }

        return roles.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(role -> !role.isEmpty())
                .map(role -> role.toUpperCase().startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public static String extractEmail(Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        return (email != null) ? email : jwt.getClaimAsString("preferred_username");
    }

    public static String extractOrganizationAlias(Jwt jwt) {
        Object orgClaim = jwt.getClaim("organization");
        if (orgClaim instanceof List<?> orgArray && !orgArray.isEmpty()) {
            for (Object elem : orgArray) {
                if (elem instanceof String alias && !alias.isBlank()) {
                    return alias;
                }
            }
            for (Object elem : orgArray) {
                if (elem instanceof Map<?, ?> orgDetails) {
                    for (Object key : orgDetails.keySet()) {
                        if (key instanceof String alias && !alias.isBlank()) {
                            return alias;
                        }
                    }
                }
            }
        }
        return null;
    }
}
