package com.eventbooking.userservice.dto;

import lombok.Data;

@Data
public class UserPreferencesResponse {
    private String id;
    private Boolean notificationsEnabled;
    private String theme;
    private String language;
    private String extras;
}
