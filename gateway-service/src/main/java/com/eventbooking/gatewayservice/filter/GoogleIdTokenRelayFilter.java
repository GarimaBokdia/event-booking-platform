package com.eventbooking.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class GoogleIdTokenRelayFilter extends AbstractGatewayFilterFactory<Object> {

    public GoogleIdTokenRelayFilter() {
        super(Object.class);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            return ReactiveSecurityContextHolder.getContext()
                    .filter(c -> c.getAuthentication() != null)
                    .flatMap(c -> {
                        OAuth2AuthenticationToken oauth2Auth = (OAuth2AuthenticationToken) c.getAuthentication();
                        // 1. Get the user details (OidcUser)
                        OidcUser user = (OidcUser) oauth2Auth.getPrincipal();
                        // 2. Extract the ID Token (This is the real JWT!)
                        String idToken = user.getIdToken().getTokenValue();

                        // 3. Mutate the request to add the header
                        return chain.filter(exchange.mutate()
                                .request(r -> r.header(HttpHeaders.AUTHORIZATION, "Bearer " + idToken))
                                .build());
                    })
                    // If not logged in, just pass the request (Security config will catch it later)
                    .switchIfEmpty(chain.filter(exchange));
        };
    }
}