package com.eventbooking.bookingservice.client;

import com.eventbooking.bookingservice.dto.client.EventClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service", url = "${event.service.url}")
public interface EventClient {
    @GetMapping("/api/events/{id}")
    EventClientResponse getEvent(@PathVariable Long id);
}
