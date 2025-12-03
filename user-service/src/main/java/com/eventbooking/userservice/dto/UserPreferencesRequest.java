package com.eventbooking.userservice.dto;

import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class UserPreferencesRequest {
    private Boolean notificationsEnabled;
    private String theme;
    private String language;
    private String extras; // JSON string for now

    @PrePersist
    public void applyDefaults() {
        if (notificationsEnabled == null) notificationsEnabled = true;
        if (theme == null) theme = "light";
        if (language == null) language = "en";
        if (extras == null) extras = "{}";
    }

}
