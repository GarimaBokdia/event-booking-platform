package com.eventbooking.bookingservice.mapper;

import com.eventbooking.bookingservice.dto.BookingResponse;
import com.eventbooking.bookingservice.dto.client.EventClientResponse;
import com.eventbooking.bookingservice.dto.client.UserClientResponse;
import com.eventbooking.bookingservice.dto.client.VenueClientResponse;
import com.eventbooking.bookingservice.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class BookingMapper {

    // 1. MapStruct will now automatically use the 'mapInstantToLong' method below
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "venue", ignore = true)
    public abstract BookingResponse mapBooking(Booking booking);

    // 2. The Main Method (Logic)
    public BookingResponse toResponseWithClients(Booking booking,
                                                 UserClientResponse user,
                                                 EventClientResponse event,
                                                 VenueClientResponse venue) {
        BookingResponse response = mapBooking(booking);
        response.setUser(user);
        response.setEvent(event);
        response.setVenue(venue);
        return response;
    }

    // 3. THE FIX: Custom Helper to convert Instant -> Long
    // MapStruct finds this signature matching (Instant -> Long) and uses it automatically
    protected Long map(Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.toEpochMilli();
    }
}