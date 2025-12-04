package com.eventbooking.bookingservice.mapper;

import com.eventbooking.bookingservice.dto.BookingResponse;
import com.eventbooking.bookingservice.dto.client.EventClientResponse;
import com.eventbooking.bookingservice.dto.client.UserClientResponse;
import com.eventbooking.bookingservice.dto.client.VenueClientResponse;
import com.eventbooking.bookingservice.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt().toEpochMilli())")
    BookingResponse toResponse(Booking entity);

    default BookingResponse toResponseWithClients(Booking entity,
                                                  UserClientResponse user,
                                                  EventClientResponse event,
                                                  VenueClientResponse venue) {
        BookingResponse res = toResponse(entity);
        res.setUser(user);
        res.setEvent(event);
        res.setVenue(venue);
        return res;
    }
}
