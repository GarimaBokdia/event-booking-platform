package com.eventbooking.userservice.dto;

import com.eventbooking.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String email;
    private String password;       // plain text input
    private Role role;             // optional, can be null (mapper/service set default)
    private UserPreferencesRequest preferences; // optional
}
