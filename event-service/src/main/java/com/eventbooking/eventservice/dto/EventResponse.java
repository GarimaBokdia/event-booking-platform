package com.eventbooking.eventservice.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private String venueName;
    private String city;
    private long startTime;
    private long endTime;
    private Double price;
    private Integer capacity;
    private Boolean isActive;
    private Long venueId;
    private Long organizerId;
    private String organizerName;
    private long createdAt;
    private long updatedAt;
}
