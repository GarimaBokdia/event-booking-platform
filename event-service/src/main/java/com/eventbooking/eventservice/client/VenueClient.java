package com.eventbooking.eventservice.client;

import com.eventbooking.eventservice.dto.client.VenueApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "venue-service", url = "${venue.service.url}")
public interface VenueClient {
    @GetMapping("/api/venues/{id}")
    VenueApiResponse getVenueById(@PathVariable("id") Long id);
}
