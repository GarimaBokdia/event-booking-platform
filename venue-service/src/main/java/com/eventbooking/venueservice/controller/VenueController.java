package com.eventbooking.venueservice.controller;

import com.eventbooking.venueservice.dto.*;
import com.eventbooking.venueservice.mapper.VenueMapper;
import com.eventbooking.venueservice.model.ApiResponse;
import com.eventbooking.venueservice.service.VenueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
@Tag(name = "Venue Management", description = "CRUD operations for venues")
public class VenueController {

    private final VenueService venueService;
    private final VenueMapper venueMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<VenueResponse>> createVenue(@Valid @RequestBody VenueRequest request) {

        return ResponseEntity.ok(ApiResponse.success(venueService.createVenue(request)));
    }

    @GetMapping
    public ResponseEntity<List<VenueResponse>> getAllVenues() {
        List<VenueResponse> lstVenues = venueService.getAllVenues();
        return ResponseEntity.ok(lstVenues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VenueResponse>> getVenueById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(venueService.getVenueById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VenueResponse>> updateVenue(@PathVariable Long id,
                                                                  @Valid @RequestBody VenueRequest request) {
        return ResponseEntity.ok(ApiResponse.success(venueService.updateVenue(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return ResponseEntity.ok(ApiResponse.success("Venue Deleted Successfully"));
    }
}
