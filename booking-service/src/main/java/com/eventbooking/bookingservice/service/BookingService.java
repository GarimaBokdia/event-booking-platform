package com.eventbooking.bookingservice.service;

import com.eventbooking.bookingservice.dto.BookingRequest;
import com.eventbooking.bookingservice.dto.BookingResponse;
import java.util.List;

public interface BookingService {
    public BookingResponse createBooking(BookingRequest req);
    public BookingResponse getBooking(Long id);

    void deleteBooking(Long id);

    List<BookingResponse> getAllBookings();

    BookingResponse confirmBooking(Long bookingId);

    // --- NEW METHOD: Cancel (Simulates User cancelling or Timeout) ---
    void cancelBooking(Long bookingId);
}