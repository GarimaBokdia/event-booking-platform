//package com.eventbooking.bookingservice.service;
//
//import com.eventbooking.bookingservice.client.EventClient;
//import com.eventbooking.bookingservice.client.UserClient;
//import com.eventbooking.bookingservice.client.VenueClient;
//import com.eventbooking.bookingservice.dto.BookingRequest;
//import com.eventbooking.bookingservice.dto.BookingResponse;
//import com.eventbooking.bookingservice.dto.client.EventClientResponse;
//import com.eventbooking.bookingservice.dto.client.UserClientResponse;
//import com.eventbooking.bookingservice.dto.client.VenueApiResponse;
//import com.eventbooking.bookingservice.dto.client.VenueClientResponse;
//import com.eventbooking.bookingservice.entity.Booking;
//import com.eventbooking.bookingservice.exception.BookingNotFoundException;
//import com.eventbooking.bookingservice.mapper.BookingMapper;
//import com.eventbooking.bookingservice.repository.BookingRepository;
//import com.eventbooking.bookingservice.service.impl.BookingServiceImpl;
//import com.eventbooking.bookingservice.service.impl.NotificationManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mapstruct.factory.Mappers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.Instant;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class BookingServiceImplTest {
//
//    @Mock
//    private BookingRepository bookingRepository;
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
//    @Mock
//    private BookingMapper bookingMapper;
//
//    @Mock // <--- ADD THIS
//    private NotificationManager notificationManager;
//
//    private BookingServiceImpl bookingService;
//
//    @BeforeEach
//    void setup() {
//        bookingService = new BookingServiceImpl(
//                bookingRepository,
//                userClient,
//                eventClient,
//                venueClient,
//                bookingMapper
//        );
//    }
//
//    @Test
//    void createBooking_success() {
//        // arrange
//        BookingRequest req = new BookingRequest();
//        req.setUserId(1L);
//        req.setEventId(10L);
//
//        UserClientResponse user = new UserClientResponse(1L, "User", "email", "USER");
//        EventClientResponse event = new EventClientResponse(10L, "Event", "event@mail", "NONE", 100L);
//        VenueClientResponse venueData = new VenueClientResponse(100L, "Venue", "City", 500);
//        VenueApiResponse venue = new VenueApiResponse(true, venueData);
//
//        when(userClient.getUser(1L)).thenReturn(user);
//        when(eventClient.getEvent(10L)).thenReturn(event);
//        when(venueClient.getVenue(100L)).thenReturn(venue);
//
//        Booking saved = Booking.builder()
//                .id(999L)
//                .userId(1L)
//                .eventId(10L)
//                .venueId(100L)
//                .status("BOOKED")
//                .createdAt(Instant.now())
//                .build();
//
//        when(bookingRepository.save(Mockito.any())).thenReturn(saved);
//
//        // 💥 FIX: mock mapper
//        when(bookingMapper.toResponseWithClients(
//                any(Booking.class),
//                any(UserClientResponse.class),
//                any(EventClientResponse.class),
//                any(VenueClientResponse.class)
//        )).thenReturn(
//                BookingResponse.builder()
//                        .id(999L)
//                        .status("BOOKED")
//                        .user(user)
//                        .event(event)
//                        .venue(venueData)
//                        .build()
//        );
//
//        // act
//        BookingResponse response = bookingService.createBooking(req);
//
//        // assert
//        assertEquals(999L, response.getId());
//        assertEquals("BOOKED", response.getStatus());
//        assertEquals(1L, response.getUser().getId());
//        assertEquals(10L, response.getEvent().getId());
//        assertEquals(100L, response.getVenue().getId());
//
//        verify(bookingRepository).save(Mockito.any());
//        verify(bookingMapper).toResponseWithClients(any(), any(), any(), any());
//    }
//
//
//    @Test
//    void createBooking_fails_whenUserNotFound() {
//        BookingRequest req = new BookingRequest();
//        req.setUserId(1L);
//        req.setEventId(10L);
//
//        when(userClient.getUser(1L)).thenReturn(null);
//
//        assertThrows(IllegalArgumentException.class, () ->
//                bookingService.createBooking(req)
//        );
//
//        verify(userClient).getUser(1L);
//        verifyNoInteractions(eventClient, venueClient, bookingRepository);
//    }
//
//
//    @Test
//    void createBooking_fails_whenEventNotFound() {
//        BookingRequest req = new BookingRequest();
//        req.setUserId(1L);
//        req.setEventId(10L);
//
//        when(userClient.getUser(1L))
//                .thenReturn(new UserClientResponse(1L, "User", "email", "USER"));
//
//        when(eventClient.getEvent(10L)).thenReturn(null);
//
//        assertThrows(IllegalArgumentException.class, () ->
//                bookingService.createBooking(req)
//        );
//
//        verify(eventClient).getEvent(10L);
//        verifyNoInteractions(venueClient, bookingRepository);
//    }
//
//    @Test
//    void createBooking_fails_whenVenueApiFails() {
//        BookingRequest req = new BookingRequest();
//        req.setUserId(1L);
//        req.setEventId(10L);
//
//        UserClientResponse user = new UserClientResponse(1L, "User", "email", "USER");
//        EventClientResponse event = new EventClientResponse(10L, "Event", "mail", "NONE", 100L);
//
//        when(userClient.getUser(1L)).thenReturn(user);
//        when(eventClient.getEvent(10L)).thenReturn(event);
//        when(venueClient.getVenue(100L))
//                .thenReturn(new VenueApiResponse(false, null)); // ❌ success=false
//
//        assertThrows(IllegalArgumentException.class, () ->
//                bookingService.createBooking(req)
//        );
//
//        verifyNoInteractions(bookingRepository);
//    }
//
//    @Test
//    void createBooking_fails_whenVenueResponseNull() {
//        BookingRequest req = new BookingRequest();
//        req.setUserId(1L);
//        req.setEventId(10L);
//
//        UserClientResponse user = new UserClientResponse(1L, "User", "email", "USER");
//        EventClientResponse event = new EventClientResponse(10L, "Event", "mail", "NONE", 100L);
//
//        when(userClient.getUser(1L)).thenReturn(user);
//        when(eventClient.getEvent(10L)).thenReturn(event);
//        when(venueClient.getVenue(100L)).thenReturn(null); // ❌ NULL
//
//        assertThrows(IllegalArgumentException.class, () ->
//                bookingService.createBooking(req)
//        );
//
//        verifyNoInteractions(bookingRepository);
//    }
//
//    @Test
//    void createBooking_fails_whenSavingThrowsException() {
//        BookingRequest req = new BookingRequest();
//        req.setUserId(1L);
//        req.setEventId(10L);
//
//        UserClientResponse user = new UserClientResponse(1L, "User", "email", "USER");
//        EventClientResponse event = new EventClientResponse(10L, "Event", "mail", "NONE", 100L);
//        VenueClientResponse venueData = new VenueClientResponse(100L, "Venue", "City", 500);
//        VenueApiResponse venue = new VenueApiResponse(true, venueData);
//
//        when(userClient.getUser(1L)).thenReturn(user);
//        when(eventClient.getEvent(10L)).thenReturn(event);
//        when(venueClient.getVenue(100L)).thenReturn(venue);
//
//        when(bookingRepository.save(Mockito.any()))
//                .thenThrow(new RuntimeException("DB error"));
//
//        assertThrows(RuntimeException.class, () ->
//                bookingService.createBooking(req)
//        );
//    }
//
//
//    @Test
//    void getBooking_success() {
//        Long id = 10L;
//
//        Booking booking = Booking.builder()
//                .id(id)
//                .userId(1L)
//                .eventId(2L)
//                .venueId(3L)
//                .status("BOOKED")
//                .createdAt(Instant.now())
//                .build();
//
//        UserClientResponse user = new UserClientResponse(1L, "User", "email", "USER");
//        EventClientResponse event = new EventClientResponse(10L, "Event", "mail", "NONE", 100L);
//        VenueClientResponse venueData = new VenueClientResponse(100L, "Venue", "City", 500);
//        VenueApiResponse venue = new VenueApiResponse(true, venueData);
//
//        BookingResponse expectedResponse = BookingResponse.builder()
//                .id(id)
//                .status("BOOKED")
//                .user(user)
//                .event(event)
//                .venue(venueData)
//                .build();
//
//        // Mocks
//        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));
//        when(userClient.getUser(1L)).thenReturn(user);
//        when(eventClient.getEvent(2L)).thenReturn(event);
//        when(venueClient.getVenue(3L)).thenReturn(venue);
//        when(bookingMapper.toResponseWithClients(booking, user, event, venueData)).thenReturn(expectedResponse);
//
//        // Execute
//        BookingResponse result = bookingService.getBooking(id);
//
//        // Verify
//        assertEquals(expectedResponse, result);
//        verify(bookingRepository).findById(id);
//        verify(userClient).getUser(1L);
//        verify(eventClient).getEvent(2L);
//        verify(venueClient).getVenue(3L);
//        verify(bookingMapper).toResponseWithClients(booking, user, event, venueData);
//    }
//
//    @Test
//    void getBooking_notFound() {
//        Long id = 200L;
//
//        when(bookingRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(BookingNotFoundException.class,
//                () -> bookingService.getBooking(id));
//
//        verify(bookingRepository).findById(id);
//        verifyNoInteractions(userClient, eventClient, venueClient, bookingMapper);
//    }
//
//    @Test
//    void getBooking_userClientFailure() {
//        Long id = 10L;
//
//        Booking booking = Booking.builder()
//                .id(id)
//                .userId(1L)
//                .eventId(2L)
//                .venueId(3L)
//                .status("BOOKED")
//                .createdAt(Instant.now())
//                .build();
//
//        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));
//        when(userClient.getUser(1L)).thenThrow(new RuntimeException("User service down"));
//
//        assertThrows(RuntimeException.class, () -> bookingService.getBooking(id));
//
//        verify(bookingRepository).findById(id);
//        verify(userClient).getUser(1L);
//        verifyNoMoreInteractions(eventClient, venueClient, bookingMapper);
//    }
//
//
//}
