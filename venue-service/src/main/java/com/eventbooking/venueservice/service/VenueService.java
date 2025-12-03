package com.eventbooking.venueservice.service;

import com.eventbooking.venueservice.dto.VenueRequest;
import com.eventbooking.venueservice.dto.VenueResponse;

import java.util.List;

public interface VenueService {

    VenueResponse createVenue(VenueRequest request);

    List<VenueResponse> getAllVenues();

    VenueResponse getVenueById(Long id);

    VenueResponse updateVenue(Long id, VenueRequest request);

    void deleteVenue(Long id);
}
