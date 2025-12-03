package com.eventbooking.eventservice.mapper;

import com.eventbooking.eventservice.dto.EventRequest;
import com.eventbooking.eventservice.dto.EventResponse;
import com.eventbooking.eventservice.entity.Event;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T15:03:23+0530",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public Event toEntity(EventRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.title( dto.getTitle() );
        event.description( dto.getDescription() );
        event.city( dto.getCity() );
        event.price( dto.getPrice() );
        event.capacity( dto.getCapacity() );
        event.organizerId( dto.getOrganizerId() );
        event.venueId( dto.getVenueId() );
        event.isActive( dto.getIsActive() );

        event.startTime( toInstant(dto.getStartTime()) );
        event.endTime( toInstant(dto.getEndTime()) );

        return event.build();
    }

    @Override
    public EventResponse toResponse(Event entity) {
        if ( entity == null ) {
            return null;
        }

        EventResponse.EventResponseBuilder eventResponse = EventResponse.builder();

        eventResponse.id( entity.getId() );
        eventResponse.title( entity.getTitle() );
        eventResponse.description( entity.getDescription() );
        eventResponse.city( entity.getCity() );
        eventResponse.price( entity.getPrice() );
        eventResponse.capacity( entity.getCapacity() );
        eventResponse.isActive( entity.getIsActive() );
        eventResponse.venueId( entity.getVenueId() );
        eventResponse.organizerId( entity.getOrganizerId() );

        eventResponse.startTime( toEpochMilli(entity.getStartTime()) );
        eventResponse.endTime( toEpochMilli(entity.getEndTime()) );
        eventResponse.createdAt( toEpochMilli(entity.getCreatedAt()) );
        eventResponse.updatedAt( toEpochMilli(entity.getUpdatedAt()) );

        return eventResponse.build();
    }
}
