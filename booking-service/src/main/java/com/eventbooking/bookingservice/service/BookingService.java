package com.eventbooking.bookingservice.service;

import com.eventbooking.bookingservice.dto.BookingRequest;
import com.eventbooking.bookingservice.dto.BookingResponse;
import com.eventbooking.common.dto.PagedResponse;

import java.util.List;

public interface BookingService {
    public BookingResponse createBooking(BookingRequest req);
    public BookingResponse getBooking(Long id);

    void deleteBooking(Long id);

    PagedResponse<BookingResponse> getAllBookings(int page, int size);

    BookingResponse confirmBooking(Long bookingId);

    // --- NEW METHOD: Cancel (Simulates User cancelling or Timeout) ---
    void cancelBooking(Long bookingId);

    String unstableMethod();
}