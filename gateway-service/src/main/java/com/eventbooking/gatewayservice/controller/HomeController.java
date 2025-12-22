package com.eventbooking.gatewayservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return "You are not logged in!";
        }
        // This proves we have the Token!
        return "<h1>Login Successful!</h1>" +
                "<h2>Welcome, " + principal.getAttribute("name") + "</h2>" +
                "<p>Email: " + principal.getAttribute("email") + "</p>";
    }
}