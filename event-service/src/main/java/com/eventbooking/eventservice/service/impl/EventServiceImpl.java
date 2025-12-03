package com.eventbooking.eventservice.service.impl;

import com.eventbooking.eventservice.client.UserClient;
import com.eventbooking.eventservice.client.VenueClient;
import com.eventbooking.eventservice.dto.*;
import com.eventbooking.eventservice.entity.Event;
import com.eventbooking.eventservice.exception.EventNotFoundException;
import com.eventbooking.eventservice.mapper.EventMapper;
import com.eventbooking.eventservice.repository.EventRepository;
import com.eventbooking.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository repository;
    private final EventMapper mapper;

    @Override
    public EventResponse createEvent(EventRequest request) {
        Event event = mapper.toEntity(request);
        event = repository.save(event);
        log.info("Created event: {}", event.getId());
        return mapper.toResponse(event);
    }

    @Override
    public EventResponse updateEvent(Long id, EventRequest request) {
        Event existing = repository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + id));

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setStartTime(Instant.ofEpochMilli(request.getStartTime()));
        existing.setEndTime(Instant.ofEpochMilli(request.getEndTime()));
        existing.setVenueId(request.getVenueId());
        existing.setOrganizerId(request.getOrganizerId());
        existing.setPrice(request.getPrice());
        existing.setCapacity(request.getCapacity());
        existing.setIsActive(request.getIsActive());
        existing.setCity(request.getCity());

        existing = repository.save(existing);
        log.info("Updated event with ID: {}", id);

        return mapper.toResponse(existing);
    }


    @Override
    public void deleteEvent(Long id) {
        if (!repository.existsById(id)) {
            throw new EventNotFoundException("Event not found with id " + id);
        }
        repository.deleteById(id);
        log.info("Deleted event: {}", id);
    }

    @Autowired
    private VenueClient venueClient;

    @Autowired
    private UserClient userClient;

    @Override
    public EventResponse getEventById(Long id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + id));

        EventResponse response = mapper.toResponse(event);

        try {
            VenueClientResponse venue = venueClient.getVenueById(event.getVenueId()).getData();
            response.setVenueName(venue.getName());
            response.setCity(venue.getCity());
        } catch (Exception e) {
            log.warn("Failed to fetch venue details for venueId={}", event.getVenueId(), e);
        }

        try {
            UserClientResponse user = userClient.getUserById(event.getOrganizerId());
            response.setOrganizerId(user.getId());
            response.setOrganizerName(user.getName());
        } catch (Exception e) {
            log.warn("Failed to fetch user details for organizerId={}", event.getOrganizerId(), e);
        }

        return response;
    }


    @Override
    public List<EventResponse> getAllEvents() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventResponse> getActiveEvents() {
        return repository.findByIsActiveTrue().stream().map(mapper ::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<EventResponse> getEventsByOrganizer(Long organizerId) {
        return repository.findByOrganizerId(organizerId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}
