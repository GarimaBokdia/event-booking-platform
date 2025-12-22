package com.eventbooking.bookingservice.dto;

import com.eventbooking.bookingservice.dto.client.EventClientResponse;
import com.eventbooking.bookingservice.dto.client.UserClientResponse;
import com.eventbooking.bookingservice.dto.client.VenueClientResponse;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private String status;
    private Long createdAt;

    private UserClientResponse user;
    private EventClientResponse event;
    private VenueClientResponse venue;
}
