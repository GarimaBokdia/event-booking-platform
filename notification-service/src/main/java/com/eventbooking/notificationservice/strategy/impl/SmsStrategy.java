package com.eventbooking.notificationservice.strategy.impl;

import com.eventbooking.notificationservice.dto.NotificationRequest;
import com.eventbooking.notificationservice.strategy.NotificationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsStrategy implements NotificationStrategy {

    @Override
    public boolean supports(String type) {
        return "SMS".equalsIgnoreCase(type);
    }

    @Override
    public void send(NotificationRequest request) {
        log.info("📱 [SMS SERVICE] Sending to: {}", request.getRecipient());
        log.info("   Message: {}", request.getMessage());
        // Twilio integration logic goes here
    }
}