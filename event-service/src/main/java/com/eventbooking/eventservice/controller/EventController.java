package com.eventbooking.eventservice.controller;

import com.eventbooking.common.dto.PagedResponse;
import com.eventbooking.eventservice.dto.*;
import com.eventbooking.eventservice.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(@Valid @PathVariable Long id, @Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                     @RequestParam(name = "size", defaultValue = "10") int size) {

        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reduce-seats")
    public ResponseEntity<Void> reduceSeats(@PathVariable Long id, @RequestParam int quantity) {
        eventService.reduceSeats(id, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/release-seats")
    public ResponseEntity<Void> releaseSeats(@PathVariable Long id, @RequestParam int quantity) {
        eventService.releaseSeats(id, quantity);
        return ResponseEntity.ok().build();
    }
}
