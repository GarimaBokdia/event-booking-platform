package com.eventbooking.bookingservice.service.impl;

import com.eventbooking.bookingservice.client.NotificationClient;
import com.eventbooking.bookingservice.dto.client.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationManager {

    private final NotificationClient notificationClient;

    /**
     * Handles the background sending logic.
     * Uses CompletableFuture so the caller doesn't wait.
     */
    public void sendBookingConfirmationAsync(String email, String eventTitle) {

        CompletableFuture.runAsync(() -> {
            try {
                log.info("Async: Preparing notification for {}", email);

                NotificationRequest notif = new NotificationRequest();
                notif.setType("EMAIL"); // Or generic
                notif.setRecipient(email);
                notif.setSubject("Booking Confirmed");
                notif.setMessage("Success! You are going to " + eventTitle);

                notificationClient.sendNotification(notif);

            } catch (Exception e) {
                // We catch it here so it doesn't crash the main thread
                // In a real system, you might log to a "Retry Table"
                log.error("Failed to send async notification", e);
            }
        });
    }
}