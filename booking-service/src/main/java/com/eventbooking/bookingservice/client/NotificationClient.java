package com.eventbooking.bookingservice.client;


import com.eventbooking.bookingservice.dto.client.NotificationRequest;
import com.eventbooking.bookingservice.dto.client.UserClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "${notification.service.url}")
public interface NotificationClient {
    @PostMapping("/api/notifications/send")
    void sendNotification(@RequestBody NotificationRequest request);
}
