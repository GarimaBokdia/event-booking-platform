package com.eventbooking.venueservice.mapper;

import com.eventbooking.venueservice.dto.VenueResponse;
import com.eventbooking.venueservice.dto.VenueRequest;
import com.eventbooking.venueservice.entity.Venue;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VenueMapper {

    Venue toEntity(VenueRequest dto);

    @Mapping(target = "createdAt", expression = "java(venue.getCreatedAt() != null ? venue.getCreatedAt().toEpochMilli() : 0L)")
    @Mapping(target = "updatedAt", expression = "java(venue.getUpdatedAt() != null ? venue.getUpdatedAt().toEpochMilli() : 0L)")
    VenueResponse toResponse(Venue venue);
}
