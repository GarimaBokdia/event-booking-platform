package com.eventbooking.bookingservice.service.impl;

import com.eventbooking.bookingservice.client.NotificationClient;
import com.eventbooking.bookingservice.dto.client.NotificationRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationManager {

    private final NotificationClient notificationClient;

    // 1. INJECT SELF (The "Spring Proxy" trick)
    // We use @Lazy to avoid a "Circular Dependency" crash on startup
    @Autowired
    @Lazy
    private NotificationManager self;

    /**
     * Method A: The Fire-and-Forget Wrapper
     * This method is NOT protected. It just launches the thread.
     */
    public void sendBookingConfirmationAsync(String email, String eventTitle) {
        CompletableFuture.runAsync(() -> {
            try {
                // 2. CALL SELF
                // Calling 'self' forces the request through Spring's proxy,
                // enabling the Circuit Breaker to see the call.
                self.sendEmailProtected(email, eventTitle);
            } catch (Exception e) {
                // If Circuit is OPEN, the fallback handles it.
                // If unexpected error, we log it here.
                log.error("Async task completed with error: {}", e.getMessage());
            }
        });
    }

    /**
     * Method B: The Risky Logic (Protected)
     * This is what we actually want to monitor.
     */
    @CircuitBreaker(name = "emailBreaker", fallbackMethod = "emailFallback")
    public void sendEmailProtected(String email, String eventTitle) {
        log.info("📧 Attempting to send email to {}", email);

        NotificationRequest notif = new NotificationRequest();
        notif.setType("EMAIL");
        notif.setRecipient(email);
        notif.setSubject("Booking Confirmed");
        notif.setMessage("Success! You are going to " + eventTitle);

        // The actual network call
        notificationClient.sendNotification(notif);
    }

    /**
     * The Fallback
     * Executed when Circuit is OPEN or the Notification Service fails.
     */
    public void emailFallback(String email, String eventTitle, Throwable t) {
        log.warn("🧯 CIRCUIT BREAKER FALLBACK: Notification Service is unavailable. Skipping email for {}", email);
        log.warn("   Reason: {}", t.getMessage());
        // In a real system, send this event to a generic 'Retry Queue' in Kafka/Redis
    }
}