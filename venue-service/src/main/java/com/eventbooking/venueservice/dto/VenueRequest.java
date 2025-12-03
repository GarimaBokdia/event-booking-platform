package com.eventbooking.venueservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Valid
@Data
public class VenueRequest {

    @NotBlank(message = "Venue name is required")
    @Size(max = 100, message = "Venue name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Venue Address is required")
    @Size(max = 300, message = "Venue address cannot exceed 300 characters")
    private String address;

    @NotBlank(message = "Venue city is required")
    @Size(max = 50, message = "Venue city cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "City name must contain only alphabets and spaces")
    private String city;

    @NotBlank(message = "Venue state is required")
    @Size(max = 50, message = "Venue state cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "State name must contain only alphabets and spaces")
    private String state;

    @NotBlank(message = "Venue country is required")
    @Size(max = 50, message = "Venue country cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Country name must contain only alphabets and spaces")
    private String country;

    @NotNull(message = "Venue capacity is required")
    @Positive
    private Integer capacity;
}
