package com.eventbooking.notificationservice.service;

import com.eventbooking.notificationservice.dto.NotificationRequest;
import com.eventbooking.notificationservice.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    // Spring automatically injects ALL classes that implement NotificationStrategy
    private final List<NotificationStrategy> strategies;

    public void sendNotification(NotificationRequest request) {
        // Find the matching strategy
        NotificationStrategy strategy = strategies.stream()
                .filter(s -> s.supports(request.getType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported notification type: " + request.getType()));

        strategy.send(request);
    }
}