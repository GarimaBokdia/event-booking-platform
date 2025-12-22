package com.eventbooking.notificationservice.controller;

import com.eventbooking.notificationservice.dto.NotificationRequest;
import com.eventbooking.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService; // <--- The generic service

    @PostMapping("/send")
    public void sendNotification(@RequestBody NotificationRequest request) {
        notificationService.sendNotification(request);
    }
}
