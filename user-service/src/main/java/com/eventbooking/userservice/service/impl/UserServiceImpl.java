package com.eventbooking.userservice.service.impl;

import com.eventbooking.userservice.entity.User;
import com.eventbooking.userservice.entity.UserPreferences;
import com.eventbooking.userservice.repository.UserPreferencesRepository;
import com.eventbooking.userservice.repository.UserRepository;
import com.eventbooking.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPreferencesRepository preferencesRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user, UserPreferences maybePrefs) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email already exists");
        }
        if (user.getPasswordHash() != null) {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        }

        // preferences precedence: provided > default
        UserPreferences prefs = maybePrefs;
        if (prefs == null) {
            prefs = new UserPreferences();
            prefs.setNotificationsEnabled(true);
            prefs.setTheme("light");
            prefs.setLanguage("en");
            prefs.setExtras("{}");
        }
        prefs.setUser(user);
        user.setPreferences(prefs);

        // cascade will save preferences as well
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        log.debug("Querying database for user with ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        log.debug("User found? {}", user.isPresent());
        return user;
        //return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserPreferences> getPreferences(Long userId) {
        return preferencesRepository.findByUserId(userId);
    }

    @Override
    public UserPreferences updatePreferences(Long userId, UserPreferences newPrefs) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserPreferences prefs = preferencesRepository.findByUserId(userId)
                .orElse(new UserPreferences());
        prefs.setUser(user);
        prefs.setNotificationsEnabled(newPrefs.getNotificationsEnabled());
        prefs.setTheme(newPrefs.getTheme());
        prefs.setLanguage(newPrefs.getLanguage());
        prefs.setExtras(newPrefs.getExtras());

        return preferencesRepository.save(prefs);
    }
}
