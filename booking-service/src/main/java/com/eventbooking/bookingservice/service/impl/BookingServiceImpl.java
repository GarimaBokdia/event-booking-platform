package com.eventbooking.bookingservice.service.impl;

import com.eventbooking.bookingservice.client.EventClient;
import com.eventbooking.bookingservice.client.UserClient;
import com.eventbooking.bookingservice.client.VenueClient;
import com.eventbooking.bookingservice.dto.BookingRequest;
import com.eventbooking.bookingservice.dto.BookingResponse;
import com.eventbooking.bookingservice.dto.client.*;
import com.eventbooking.bookingservice.entity.Booking;
import com.eventbooking.bookingservice.entity.BookingStatus; // <--- Import Enum
import com.eventbooking.bookingservice.exception.BookingNotFoundException;
import com.eventbooking.bookingservice.mapper.BookingMapper;
import com.eventbooking.bookingservice.repository.BookingRepository;
import com.eventbooking.bookingservice.service.BookingService;
import com.eventbooking.common.dto.PagedResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // <--- Added for logging
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserClient userClient;
    private final EventClient eventClient;
    private final VenueClient venueClient;
    private final BookingMapper mapper;
    private final NotificationManager notificationManager; // <--- Inject the new class
    @Override
    @Transactional // <--- Ensures DB consistency
    public BookingResponse createBooking(BookingRequest req) {
        log.info("Creating booking for User: {} Event: {}", req.getUserId(), req.getEventId());

        // 1. Fetch User
        UserClientResponse user = userClient.getUser(req.getUserId());
        if (user == null) throw new IllegalArgumentException("User not found with ID: " + req.getUserId());

        // 2. Fetch Event
        EventClientResponse event = eventClient.getEvent(req.getEventId());
        if (event == null) throw new IllegalArgumentException("Event not found with ID: " + req.getEventId());

        // 3. Validation: Check Availability
        // Note: Assuming EventClientResponse has 'availableSeats' or 'capacity'
        // If your DTO only has 'capacity', change this logic to check capacity vs bookings
        if (event.getCapacity() < req.getTicketCount()) {
            throw new IllegalArgumentException("Not enough seats available. Capacity: " + event.getCapacity());
        }

        // 4. Fetch Venue (Optional for saving, but needed for response)
        VenueApiResponse venueRes = venueClient.getVenue(event.getVenueId());
        VenueClientResponse venue = (venueRes != null && venueRes.isSuccess()) ? venueRes.getData() : null;

        // 5. Calculate Total Amount
        // Ensure event.getPrice() is handled as BigDecimal to avoid money errors
        BigDecimal pricePerTicket = BigDecimal.valueOf(event.getPrice());
        BigDecimal totalAmount = pricePerTicket.multiply(BigDecimal.valueOf(req.getTicketCount()));

        // 2. REDUCE SEATS (The Lock)
        // This call is synchronous. If EventService fails (Sold Out),
        // this line throws an exception, and the Booking is NEVER saved.
        try {
            eventClient.reduceSeats(req.getEventId(), req.getTicketCount());
        } catch (Exception e) {
            throw new IllegalStateException("Booking Failed: Seats not available or Sold Out");
        }

        // 3. Save Booking (Only happens if reduceSeats succeeded)
        // 6. Build and Save Booking
        Booking booking = Booking.builder()
                .userId(req.getUserId())
                .eventId(req.getEventId())
                .venueId(event.getVenueId())
                .ticketCount(req.getTicketCount()) // <--- Added
                .totalAmount(totalAmount)          // <--- Added
                .status(BookingStatus.PENDING)   // <--- Using Enum (Use PENDING if you add Payment later)
                .build();

        Booking saved = bookingRepository.save(booking);


        log.info("Booking created successfully. ID: {}", saved.getId());

        return mapper.toResponseWithClients(saved, user, event, venue);
    }

    @Override
    public BookingResponse getBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));

        UserClientResponse user = userClient.getUser(booking.getUserId());
        EventClientResponse event = eventClient.getEvent(booking.getEventId());
        VenueApiResponse venueRes = venueClient.getVenue(booking.getVenueId());
        VenueClientResponse venue = (venueRes != null) ? venueRes.getData() : null;

        return mapper.toResponseWithClients(booking, user, event, venue);
    }

    @Override
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new BookingNotFoundException(id);
        }
        bookingRepository.deleteById(id);
    }


    @Override
    public PagedResponse<BookingResponse> getAllBookings(int page, int size) {
        // 1. Create PageRequest (Page 0, Size 10, Newest First)
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // 2. Fetch ONLY the slice of data from DB
        Page<Booking> bookingPage = bookingRepository.findAll(pageable);

        // 3. Process only the visible items (e.g., 10 items)
        List<BookingResponse> content = bookingPage.getContent().stream().map(booking -> {
            // This loop now runs only 10 times, not 1000!
            UserClientResponse user = userClient.getUser(booking.getUserId());
            EventClientResponse event = eventClient.getEvent(booking.getEventId());
            VenueApiResponse venueRes = venueClient.getVenue(booking.getVenueId());
            VenueClientResponse venue = (venueRes != null) ? venueRes.getData() : null;

            return mapper.toResponseWithClients(booking, user, event, venue);
        }).collect(Collectors.toList());

        // 4. Wrap result in PagedResponse
        return PagedResponse.<BookingResponse>builder()
                .content(content)
                .pageNumber(bookingPage.getNumber())
                .pageSize(bookingPage.getSize())
                .totalElements(bookingPage.getTotalElements())
                .totalPages(bookingPage.getTotalPages())
                .last(bookingPage.isLast())
                .build();
    }


    @Override
    public BookingResponse confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Booking cannot be confirmed. Current status: " + booking.getStatus());
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        Booking saved = bookingRepository.save(booking);


        UserClientResponse user = userClient.getUser(saved.getUserId());
        EventClientResponse event = eventClient.getEvent(saved.getEventId());
        
        try {
            notificationManager.sendBookingConfirmationAsync(user.getEmail(), event.getTitle());
        } catch (Exception e) {
            log.error("Notification failed", e);
        }

        return getBooking(saved.getId());
    }

    // --- NEW METHOD: Cancel (Simulates User cancelling or Timeout) ---
    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return; // Already cancelled
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

         eventClient.releaseSeats(booking.getEventId(), booking.getTicketCount());
    }

    @CircuitBreaker(name = "testBreaker")
    @Retry(name = "testRetry")
    public String unstableMethod() {
        log.info("🔥 Calling unstable method...");
        throw new RuntimeException("Boom!");
    }
}