package com.eventbooking.bookingservice.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueApiResponse {
    private boolean success;
    private VenueClientResponse data;
}
