//package com.eventbooking.bookingservice.service;
//
//import com.eventbooking.bookingservice.client.EventClient;
//import com.eventbooking.bookingservice.client.UserClient;
//import com.eventbooking.bookingservice.client.VenueClient;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//
//import com.eventbooking.bookingservice.dto.client.*;
//
//@TestConfiguration
//public class TestConfig {
//
//    @Bean
//    public UserClient fakeUserClient() {
//        return userId -> new UserClientResponse(
//                userId, "TestUser", "user@test.com", "USER"
//        );
//    }
//
//    @Bean
//    public EventClient fakeEventClient() {
//        return eventId -> new EventClientResponse(
//                eventId, "TestEvent", "event@test.com", "NONE", 500L
//        );
//    }
//
//    @Bean
//    public VenueClient fakeVenueClient() {
//        return venueId -> new VenueApiResponse(
//                true,
//                new VenueClientResponse(venueId, "TestVenue", "City", 1000)
//        );
//    }
//}
