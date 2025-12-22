package com.eventbooking.bookingservice.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long userId;
    private Long eventId;
    private Integer ticketCount;
}
