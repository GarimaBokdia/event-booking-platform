package com.eventbooking.bookingservice.dto.client;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventClientResponse {
    private Long id;
    private String title;
    private String description;
    private Long venueId;
    private Integer capacity;
    private Double price;
}
