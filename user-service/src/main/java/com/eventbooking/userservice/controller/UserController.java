package com.eventbooking.userservice.controller;

import com.eventbooking.userservice.dto.UserPreferencesRequest;
import com.eventbooking.userservice.dto.UserPreferencesResponse;
import com.eventbooking.userservice.dto.UserRequest;
import com.eventbooking.userservice.dto.UserResponse;
import com.eventbooking.userservice.entity.User;
import com.eventbooking.userservice.entity.UserPreferences;
import com.eventbooking.userservice.mapper.UserMapper;
import com.eventbooking.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // Create user — preferences optional in request
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        User userEntity = userMapper.toEntity(request);
        UserPreferences prefsEntity = null;
        if (request.getPreferences() != null) {
            prefsEntity = userMapper.preferencesRequestToEntity(request.getPreferences());
        }

        User created = userService.createUser(userEntity, prefsEntity);
        return ResponseEntity.ok(userMapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> list = userService.getAllUsers().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        log.info("Received request to fetch user with ID: {}", id);
        return userService.getUserById(id)
                .map(userMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("User with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // Preferences endpoints
    @GetMapping("/{id}/preferences")
    public ResponseEntity<UserPreferencesResponse> getPreferences(@PathVariable Long id) {
        return userService.getPreferences(id)
                .map(userMapper::preferencesEntityToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/preferences")
    public ResponseEntity<UserPreferencesResponse> updatePreferences(
            @PathVariable Long id,
            @RequestBody UserPreferencesRequest prefsReq) {

        UserPreferences prefsEntity = userMapper.preferencesRequestToEntity(prefsReq);
        UserPreferences updated = userService.updatePreferences(id, prefsEntity);
        return ResponseEntity.ok(userMapper.preferencesEntityToResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
