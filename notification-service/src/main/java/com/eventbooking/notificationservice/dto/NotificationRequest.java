package com.eventbooking.notificationservice.dto;

import lombok.Data;

@Data
public class NotificationRequest {
    private String type;         // "EMAIL", "SMS", "WHATSAPP"
    private String recipient;    // Contains Email OR Phone Number
    private String subject;      // Used for Email
    private String message;      // Body content
}