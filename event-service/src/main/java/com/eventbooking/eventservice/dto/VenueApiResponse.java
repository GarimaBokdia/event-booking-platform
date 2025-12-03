package com.eventbooking.eventservice.dto;

import lombok.Data;

// VenueApiResponse.java
@Data
public class VenueApiResponse {
    private boolean success;
    private VenueClientResponse data;
}
