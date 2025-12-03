package com.eventbooking.venueservice.exception;

public class VenueNotFoundException extends RuntimeException {
    public VenueNotFoundException(Long id) {
        super("Venue not found with ID: " + id);
    }
}
