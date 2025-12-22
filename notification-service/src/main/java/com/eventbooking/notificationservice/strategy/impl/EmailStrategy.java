package com.eventbooking.notificationservice.strategy.impl;

import com.eventbooking.notificationservice.dto.NotificationRequest;
import com.eventbooking.notificationservice.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailStrategy implements NotificationStrategy {

    private final JavaMailSender mailSender;

    // Use a fixed sender email (must match your spring.mail.username)
    private final String SENDER_EMAIL = "your_email@gmail.com";

    @Override
    public boolean supports(String type) {
        return "EMAIL".equalsIgnoreCase(type);
    }

    @Override
    public void send(NotificationRequest request) {
        log.info("📧 [EMAIL SERVICE] Preparing to send to: {}", request.getRecipient());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(SENDER_EMAIL);
            message.setTo(request.getRecipient());
            message.setSubject(request.getSubject());
            message.setText(request.getMessage());

            mailSender.send(message);

            log.info("✅ Email sent successfully to {}", request.getRecipient());

        } catch (Exception e) {
            log.error("❌ Failed to send email to {}: {}", request.getRecipient(), e.getMessage());
            // In a real system, you might throw this to trigger a retry mechanism
            throw new RuntimeException("Email sending failed", e);
        }
    }
}