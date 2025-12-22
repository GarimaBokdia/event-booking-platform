package com.eventbooking.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 1. Get the current incoming HTTP request
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    // 2. Extract the Authorization header (Bearer token)
                    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

                    if (authorization != null) {
                        // 3. Inject it into the outgoing Feign request
                        template.header(HttpHeaders.AUTHORIZATION, authorization);
                    }
                }
            }
        };
    }
}