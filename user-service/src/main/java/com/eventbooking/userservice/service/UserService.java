package com.eventbooking.userservice.service;

import com.eventbooking.userservice.entity.User;
import com.eventbooking.userservice.entity.UserPreferences;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user, UserPreferences maybePrefs);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(Long id);

    // preferences
    Optional<UserPreferences> getPreferences(Long userId);
    UserPreferences updatePreferences(Long userId, UserPreferences newPrefs);
}
