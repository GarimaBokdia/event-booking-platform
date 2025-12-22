package com.eventbooking.eventservice.service;

import com.eventbooking.eventservice.dto.EventRequest;
import com.eventbooking.eventservice.dto.EventResponse;
import java.util.List;

public interface EventService {
    EventResponse createEvent(EventRequest eventRequest);
    EventResponse getEventById(Long id);
    List<EventResponse> getAllEvents();
    List<EventResponse> getActiveEvents();
    List<EventResponse> getEventsByOrganizer(Long organizerId);
    EventResponse updateEvent(Long id, EventRequest eventRequest);
    void deleteEvent(Long id);

    void reduceSeats(Long id, int quantity);
    void releaseSeats(Long eventId, Integer ticketCount);
}
