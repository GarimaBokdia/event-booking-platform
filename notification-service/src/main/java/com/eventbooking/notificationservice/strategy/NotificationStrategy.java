package com.eventbooking.notificationservice.strategy;

import com.eventbooking.notificationservice.dto.NotificationRequest;

public interface NotificationStrategy {
    void send(NotificationRequest request);
    boolean supports(String type);
}