// event-service/src/main/java/com/eventbooking/eventservice/dto/VenueClientResponse.java
package com.eventbooking.eventservice.dto;

import lombok.Data;

@Data
public class VenueClientResponse {
    private Long id;
    private String name;
    private String city;
    private Integer capacity;
}
