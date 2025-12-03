package com.eventbooking.eventservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Valid
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequest {

    @NotBlank(message = "Event title is required")
    @Size(max = 100, message = "Event title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Event description is required")
    @Size(max = 300, message = "Event description cannot exceed 300 characters")
    private String description;

    @NotNull(message = "Venue ID is required")
    @Positive(message = "Venue ID must be positive")
    private Long venueId;

    @NotNull(message = "Start time is required")
    @Positive(message = "Start time must be a positive epoch value")
    private Long startTime;

    @NotNull(message = "End time is required")
    @Positive(message = "End time must be a positive epoch value")
    private Long endTime;

    @NotNull(message = "Event price is required")
    @PositiveOrZero(message = "Event price must be non-negative")
    private Double price;

    @NotNull(message = "Event capacity is required")
    @Positive(message = "Event capacity must be positive")
    private Integer capacity;

    @NotNull(message = "Organizer ID is required")
    @Positive(message = "Organizer ID must be positive")
    private Long organizerId;

    @NotNull(message = "Active flag is required")
    private Boolean isActive;

    @NotBlank(message = "Event City should be defined")
    @Size(max = 50,message = "City name can be of max 50 characters")
    private String city;
}
