package com.eventbooking.bookingservice.service.impl;

import com.eventbooking.bookingservice.client.EventClient;
import com.eventbooking.bookingservice.client.UserClient;
import com.eventbooking.bookingservice.client.VenueClient;
import com.eventbooking.bookingservice.dto.BookingRequest;
import com.eventbooking.bookingservice.dto.BookingResponse;
import com.eventbooking.bookingservice.dto.client.EventClientResponse;
import com.eventbooking.bookingservice.dto.client.UserClientResponse;
import com.eventbooking.bookingservice.dto.client.VenueApiResponse;
import com.eventbooking.bookingservice.dto.client.VenueClientResponse;
import com.eventbooking.bookingservice.entity.Booking;
import com.eventbooking.bookingservice.exception.BookingNotFoundException;
import com.eventbooking.bookingservice.mapper.BookingMapper;
import com.eventbooking.bookingservice.repository.BookingRepository;
import com.eventbooking.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserClient userClient;
    private final EventClient eventClient;
    private final VenueClient venueClient;
    private final BookingMapper mapper;

    @Override
    public BookingResponse createBooking(BookingRequest req) {

        // fetch user
        UserClientResponse user = userClient.getUser(req.getUserId());
        if (user == null) throw new IllegalArgumentException("User not found");

        // fetch event
        EventClientResponse event = eventClient.getEvent(req.getEventId());
        if (event == null) throw new IllegalArgumentException("Event not found");

        // fetch venue
        VenueApiResponse venueRes = venueClient.getVenue(event.getVenueId());
        if (venueRes == null || !venueRes.isSuccess())
            throw new IllegalArgumentException("Venue not found");

        VenueClientResponse venue = venueRes.getData();

        // save booking
        Booking booking = Booking.builder()
                .userId(req.getUserId())
                .eventId(req.getEventId())
                .venueId(venue.getId())
                .status("BOOKED")
                .createdAt(Instant.now())
                .build();

        Booking saved = bookingRepository.save(booking);

        // map response
        BookingResponse res = mapper.toResponseWithClients(saved, user, event, venue);


        return res;
    }

    @Override
    public BookingResponse getBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));

        UserClientResponse user = userClient.getUser(booking.getUserId());
        EventClientResponse event = eventClient.getEvent(booking.getEventId());
        VenueApiResponse venueRes = venueClient.getVenue(booking.getVenueId());
        VenueClientResponse venue = venueRes != null ? venueRes.getData() : null;

        BookingResponse res = mapper.toResponseWithClients(booking, user, event, venue);


        return res;
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<BookingResponse> getAllBookings() {

        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(booking -> {
            // fetch user
            UserClientResponse user = userClient.getUser(booking.getUserId());

            // fetch event
            EventClientResponse event = eventClient.getEvent(booking.getEventId());

            // fetch venue
            VenueApiResponse venueRes = venueClient.getVenue(booking.getVenueId());
            VenueClientResponse venue = venueRes != null ? venueRes.getData() : null;

            // map
            return mapper.toResponseWithClients(booking, user, event, venue);

        }).collect(Collectors.toList());
    }

}

