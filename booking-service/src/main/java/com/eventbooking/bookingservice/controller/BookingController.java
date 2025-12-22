package com.eventbooking.bookingservice.controller;

import com.eventbooking.bookingservice.dto.BookingRequest;
import com.eventbooking.bookingservice.dto.BookingResponse;
import com.eventbooking.bookingservice.service.BookingService;
import com.eventbooking.common.dto.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Create a new booking
     */
    @PostMapping
    @RateLimiter(name = "bookingLimit")
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request
    ) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.ok(response);
    }

 /*   // Must have same signature as original method + Exception
    public ResponseEntity<BookingResponse> rateLimitFallback(BookingRequest request, Throwable t) {
        // Log the attack/spam
        System.out.println("⚠️ Rate Limit Exceeded for User: " + request.getUserId());

        // Return 429 Too Many Requests (Standard practice)
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }*/
    /**
     * Get booking by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(
            @PathVariable Long id
    ) {
        BookingResponse response = bookingService.getBooking(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Cancel / delete a booking (optional)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(
            @PathVariable Long id
    ) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get booking by ID
     */
    @GetMapping
    public ResponseEntity<PagedResponse<BookingResponse>> getAllBookings(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        PagedResponse<BookingResponse> response = bookingService.getAllBookings(page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Confirm a pending booking (Simulates Payment Success)
     */
    @PostMapping("/{id}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(@PathVariable Long id) {
        BookingResponse response = bookingService.confirmBooking(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Cancel a booking (Simulates Timeout or User Action)
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/testCB")
    public ResponseEntity<Void> testCB() {
        bookingService.unstableMethod();
        return ResponseEntity.noContent().build();
    }
}
