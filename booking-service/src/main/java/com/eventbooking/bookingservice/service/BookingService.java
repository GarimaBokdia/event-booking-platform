package com.eventbooking.bookingservice.service;

import com.eventbooking.bookingservice.dto.BookingRequest;
import com.eventbooking.bookingservice.dto.BookingResponse;

public interface BookingService {
    public BookingResponse createBooking(BookingRequest req);
    public BookingResponse getBooking(Long id);
}