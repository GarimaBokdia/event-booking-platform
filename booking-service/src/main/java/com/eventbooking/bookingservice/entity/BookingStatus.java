package com.eventbooking.bookingservice.entity;

public enum BookingStatus {
    PENDING,    // Created, waiting for payment
    CONFIRMED,  // Payment successful
    CANCELLED,  // User cancelled
    FAILED      // Payment failed or seats unavailable
}