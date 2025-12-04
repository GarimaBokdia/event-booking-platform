package com.eventbooking.bookingservice.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(Long message) {
        super("Booking not found with ID: " + message);
    }
}