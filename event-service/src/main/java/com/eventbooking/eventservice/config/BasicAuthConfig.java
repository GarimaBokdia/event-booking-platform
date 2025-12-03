package com.eventbooking.eventservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class BasicAuthConfig {
    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return requestTemplate ->
                requestTemplate.header("Authorization",
                        "Basic " + Base64.getEncoder().encodeToString("admin:admin123".getBytes()));
    }
}
