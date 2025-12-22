package com.eventbooking.bookingservice.exception;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookingNotFound(BookingNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(500)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Map<String, Object>> handleRateLimitException(RequestNotPermitted ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", 429);
        error.put("error", "Too Many Requests");
        error.put("message", "You are booking too fast! Please wait a moment.");
        error.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
    }
}
