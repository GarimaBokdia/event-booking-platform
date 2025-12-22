// event-service/src/main/java/com/eventbooking/eventservice/dto/VenueClientResponse.java
package com.eventbooking.bookingservice.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueClientResponse {
    private Long id;
    private String name;
    private String city;
    private Integer capacity;
}
