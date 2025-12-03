package com.eventbooking.eventservice.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super("Event not found with ID: " + message);
    }
}
