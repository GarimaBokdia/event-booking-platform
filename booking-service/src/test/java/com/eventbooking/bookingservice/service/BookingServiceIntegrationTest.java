//package com.eventbooking.bookingservice.service;
//
//import com.eventbooking.bookingservice.config.TestPostgresConfig;
//import com.eventbooking.bookingservice.dto.BookingRequest;
//import com.eventbooking.bookingservice.dto.client.*;
//import com.eventbooking.bookingservice.repository.BookingRepository;
//import com.eventbooking.bookingservice.client.*;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//
//import static org.assertj.core.api.Assertions.assertThat;
//@SpringBootTest
//@ContextConfiguration(
//        initializers = TestPostgresConfig.Initializer.class,
//        classes = TestConfig.class
//)
//class BookingServiceIntegrationTest {
//
//    @Autowired
//    private BookingService bookingService;
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @Autowired
//    private UserClient userClient;
//
//    @Autowired
//    private EventClient eventClient;
//
//    @Autowired
//    private VenueClient venueClient;
//
//    @Test
//    void createBooking_success() {
//
//        // Mock User
//        Mockito.when(userClient.getUser(1L))
//                .thenReturn(new UserClientResponse(1L, "John", "john@example.com", "USER"));
//
//        // Mock Event
//        Mockito.when(eventClient.getEvent(10L))
//                .thenReturn(new EventClientResponse(10L, "Concert", "concert@mail.com", "NONE",5L));
//
//        // Mock Venue
//        VenueClientResponse venueData =
//                new VenueClientResponse(5L, "Novotel", "Hyderabad", 500);
//
//        Mockito.when(venueClient.getVenue(5L))
//                .thenReturn(new VenueApiResponse(true, venueData));
//
//        BookingRequest req = new BookingRequest();
//        req.setUserId(1L);
//        req.setEventId(10L);
//
//        // CALL SERVICE
//        var response = bookingService.createBooking(req);
//
//        // Assertions
//        assertThat(response).isNotNull();
//        assertThat(response.getStatus()).isEqualTo("BOOKED");
//        assertThat(response.getUser().getId()).isEqualTo(1L);
//        assertThat(response.getEvent().getId()).isEqualTo(10L);
//        assertThat(response.getVenue().getId()).isEqualTo(5L);
//    }
//}
