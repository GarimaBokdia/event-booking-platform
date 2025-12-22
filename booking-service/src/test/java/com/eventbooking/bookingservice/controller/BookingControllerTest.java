//package com.eventbooking.bookingservice.controller;
//
//import com.eventbooking.bookingservice.client.*;
//import com.eventbooking.bookingservice.config.TestPostgresConfig;
//import com.eventbooking.bookingservice.dto.BookingRequest;
//import com.eventbooking.bookingservice.dto.client.*;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ContextConfiguration(initializers = TestPostgresConfig.Initializer.class)
//class BookingControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private UserClient userClient;
//
//    @Mock
//    private EventClient eventClient;
//
//    @Mock
//    private VenueClient venueClient;
//
//    @Test
//    void testCreateBookingAPI() throws Exception {
//
//        // Mock User
//        Mockito.when(userClient.getUser(1L))
//                .thenReturn(new UserClientResponse(1L, "John", "john@example.com", "USER"));
//
//        // Mock Event
//        Mockito.when(eventClient.getEvent(10L))
//                .thenReturn(new EventClientResponse(10L, "Concert", "none", "none",1L));
//
//        // Mock Venue
//        Mockito.when(venueClient.getVenue(5L))
//                .thenReturn(new VenueApiResponse(true,
//                        new VenueClientResponse(5L, "Novotel", "Hyderabad", 500)
//                ));
//
//        String json = """
//        {
//          "userId": 1,
//          "eventId": 10
//        }
//        """;
//
//        mockMvc.perform(post("/api/bookings")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("BOOKED"))
//                .andExpect(jsonPath("$.user.id").value(1))
//                .andExpect(jsonPath("$.event.id").value(10));
//    }
//}
