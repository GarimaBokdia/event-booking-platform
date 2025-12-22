package com.eventbooking.notificationservice.strategy.impl;

import com.eventbooking.notificationservice.dto.NotificationRequest;
import com.eventbooking.notificationservice.strategy.NotificationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WhatsAppStrategy implements NotificationStrategy {

    @Override
    public boolean supports(String type) {
        return "WHATSAPP".equalsIgnoreCase(type);
    }

    @Override
    public void send(NotificationRequest request) {
        log.info("💬 [WHATSAPP] Sending to: {}", request.getRecipient());
        log.info("   Payload: {}", request.getMessage());
        // Meta API integration logic goes here
    }
}