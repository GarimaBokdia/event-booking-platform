package com.eventbooking.userservice.dto;

import com.eventbooking.userservice.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // prefer flattened preference fields in response
    private Boolean notificationsEnabled;
    private String theme;
    private String language;
}
