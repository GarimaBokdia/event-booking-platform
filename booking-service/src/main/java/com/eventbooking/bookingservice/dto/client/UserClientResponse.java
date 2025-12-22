package com.eventbooking.bookingservice.dto.client;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserClientResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
}
