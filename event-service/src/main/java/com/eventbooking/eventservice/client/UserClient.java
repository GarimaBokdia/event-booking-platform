package com.eventbooking.eventservice.client;

import com.eventbooking.eventservice.dto.client.UserClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserClientResponse getUserById(@PathVariable("id") Long id);
}
