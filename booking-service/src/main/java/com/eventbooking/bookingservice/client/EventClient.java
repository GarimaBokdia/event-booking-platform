package com.eventbooking.bookingservice.client;

import com.eventbooking.bookingservice.dto.client.EventClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "event-service", url = "${event.service.url}")
public interface EventClient {
    @GetMapping("/api/events/{id}")
    EventClientResponse getEvent(@PathVariable Long id);
    @PostMapping("/api/events/{id}/reduce-seats")
    void reduceSeats(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);
    @PostMapping("/api/events/{id}/release-seats")
    void releaseSeats(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);
}
