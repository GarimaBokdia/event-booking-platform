package com.eventbooking.eventservice.mapper;

import com.eventbooking.eventservice.dto.*;
import com.eventbooking.eventservice.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface EventMapper {

    // --- Request → Entity ---
    @Mapping(target = "startTime", expression = "java(toInstant(dto.getStartTime()))")
    @Mapping(target = "endTime", expression = "java(toInstant(dto.getEndTime()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Event toEntity(EventRequest dto);

    // --- Entity → Response ---
    @Mapping(target = "startTime", expression = "java(toEpochMilli(entity.getStartTime()))")
    @Mapping(target = "endTime", expression = "java(toEpochMilli(entity.getEndTime()))")
    @Mapping(target = "createdAt", expression = "java(toEpochMilli(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(toEpochMilli(entity.getUpdatedAt()))")
    EventResponse toResponse(Event entity);

    // --- Custom conversion helpers ---
    default Instant toInstant(long epochMilli) {
        return epochMilli > 0 ? Instant.ofEpochMilli(epochMilli) : null;
    }

    default long toEpochMilli(Instant instant) {
        return instant != null ? instant.toEpochMilli() : 0L;
    }
}
