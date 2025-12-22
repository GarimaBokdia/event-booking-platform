package com.eventbooking.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF for now (easier testing)
                .authorizeExchange(exchange -> exchange
                        // Allow anyone to see the login page or Swagger UI
                        .pathMatchers("/login", "/webjars/**", "/v3/api-docs/**").permitAll()
                        // Lock everything else
                        .anyExchange().authenticated()
                )
                // Enable "Login with Google"
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}