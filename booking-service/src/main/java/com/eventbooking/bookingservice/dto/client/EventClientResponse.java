package com.eventbooking.bookingservice.dto.client;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventClientResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private Long venueId;
}
