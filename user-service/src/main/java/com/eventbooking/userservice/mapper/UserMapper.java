package com.eventbooking.userservice.mapper;

import com.eventbooking.userservice.dto.UserPreferencesRequest;
import com.eventbooking.userservice.dto.UserPreferencesResponse;
import com.eventbooking.userservice.dto.UserRequest;
import com.eventbooking.userservice.dto.UserResponse;
import com.eventbooking.userservice.entity.User;
import com.eventbooking.userservice.entity.UserPreferences;
import org.mapstruct.*;

@Mapper(componentModel = "spring", imports = {java.time.LocalDateTime.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "role", expression = "java(request.getRole() != null ? request.getRole() : com.eventbooking.userservice.entity.Role.USER)")
    @Mapping(target = "provider", constant = "LOCAL")
    @Mapping(target = "providerId", ignore = true)
        // map preferences manually below (if present)
    User toEntity(UserRequest request);

    @Mapping(target = "notificationsEnabled", source = "preferences.notificationsEnabled")
    @Mapping(target = "theme", source = "preferences.theme")
    @Mapping(target = "language", source = "preferences.language")
    UserResponse toResponse(User user);

    // Map request <-> preferences entity
    UserPreferences preferencesRequestToEntity(UserPreferencesRequest prefsReq);

    UserPreferencesResponse preferencesEntityToResponse(UserPreferences prefs);
}
