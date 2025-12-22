package com.eventbooking.bookingservice.client;

import com.eventbooking.bookingservice.dto.client.VenueApiResponse;
import com.eventbooking.bookingservice.dto.client.VenueClientResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "venue-service", url = "${venue.service.url}")
public interface VenueClient {
    @GetMapping("/api/venues/{id}")
    VenueApiResponse getVenue(@PathVariable Long id);
}
