package com.eventbooking.notificationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Microservices are stateless, no CSRF needed
                .authorizeHttpRequests(auth -> auth
                        // Allow health checks
                        .requestMatchers("/actuator/**").permitAll()
                        // Everything else requires a valid token
                        .anyRequest().authenticated()
                )
                // -----------------------------------------------------------
                // 🚨 THE CRITICAL CHANGE 🚨
                // Remove .oauth2Login()
                // Replace with .oauth2ResourceServer()
                // -----------------------------------------------------------
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }
}