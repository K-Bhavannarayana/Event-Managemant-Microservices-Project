package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.Event;
import com.example.demo.dto.EventTicketDTO;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.TicketNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.TicketBooking;
import com.example.demo.openfeign.EventClient;
import com.example.demo.openfeign.UserClient;
import com.example.demo.repository.BookingRepository;
import com.example.demo.service.BookingServiceImpl;

@SpringBootTest
class TicketBookingApplicationTests {

    @Mock
    private BookingRepository repository;

    @Mock
    private EventClient eventClient;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private TicketBooking sampleTicket;
    private Event sampleEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTicket = new TicketBooking();
        sampleTicket.setTicketId(3000); // Booking ID starts from 3000
        sampleTicket.setEventId(1000);  // Event ID starts from 1000
        sampleTicket.setUserId(2000);   // User ID starts from 2000
        sampleTicket.setStatus("booked");

        sampleEvent = new Event();
        sampleEvent.setEventId(1000);
        sampleEvent.setEventName("Tech Meetup");
    }

    @Test
    void testBookTicket_Success() throws EventNotFoundException, UserNotFoundException {
        when(eventClient.getEventPresence(1000)).thenReturn(true);
        when(userClient.getUserPresence(2000)).thenReturn(true);
        when(repository.findByUserIdAndEventId(2000, 1000)).thenReturn(Optional.empty());
        when(repository.save(sampleTicket)).thenReturn(sampleTicket);

        String result = bookingService.bookTicket(sampleTicket);
        assertEquals("Ticket booked successfully", result);

        verify(repository, times(1)).save(sampleTicket);
    }

    @Test
    void testBookTicket_AlreadyBooked() throws EventNotFoundException, UserNotFoundException {
        when(eventClient.getEventPresence(1000)).thenReturn(true);
        when(userClient.getUserPresence(2000)).thenReturn(true);
        when(repository.findByUserIdAndEventId(2000, 1000)).thenReturn(Optional.of(sampleTicket));

        String result = bookingService.bookTicket(sampleTicket);
        assertEquals("Ticket already booked", result);
    }

    @Test
    void testBookTicket_UserNotFound() {
        when(eventClient.getEventPresence(1000)).thenReturn(true);
        when(userClient.getUserPresence(2000)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> bookingService.bookTicket(sampleTicket));
    }

    @Test
    void testBookTicket_EventNotFound() {
        when(eventClient.getEventPresence(1000)).thenReturn(false);

        assertThrows(EventNotFoundException.class, () -> bookingService.bookTicket(sampleTicket));
    }

    @Test
    void testCancelTicket() {
        when(repository.save(sampleTicket)).thenReturn(sampleTicket);

        String result = bookingService.cancelTicket(sampleTicket);
        assertEquals("Tickets canceled successfully", result);

        verify(repository, times(1)).save(sampleTicket);
    }

    @Test
    void testGetTicketById_Found() throws TicketNotFoundException, EventNotFoundException {
        when(repository.findById(3000)).thenReturn(Optional.of(sampleTicket));
        when(eventClient.getEvent(1000)).thenReturn(sampleEvent);

        EventTicketDTO result = bookingService.getTicketById(3000);
        assertNotNull(result);
        assertEquals(1000, result.getEvent().getEventId());

        verify(repository, times(1)).findById(3000);
        verify(eventClient, times(1)).getEvent(1000);
    }

    @Test
    void testGetTicketById_NotFound() {
        when(repository.findById(3000)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> bookingService.getTicketById(3000));
    }

    @Test
    void testViewAllTickets() {
        when(repository.findAll()).thenReturn(Arrays.asList(sampleTicket));

        List<TicketBooking> tickets = bookingService.viewAllTickets();
        assertEquals(1, tickets.size());
        assertEquals(3000, tickets.get(0).getTicketId());

        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetAllUserIdsByEventId() {
        when(repository.getAllUserIdsByEventId(1000)).thenReturn(Arrays.asList(2000, 2001));

        List<Integer> userIds = bookingService.getAllUserIdsByEventId(1000);
        assertEquals(2, userIds.size());
        assertTrue(userIds.contains(2000));

        verify(repository, times(1)).getAllUserIdsByEventId(1000);
    }

    @Test
    void testCancelTicketsByEventId() {
        doNothing().when(repository).cancelTicketsByEventId(1000);

        bookingService.cancelTicketsByEventId(1000);

        verify(repository, times(1)).cancelTicketsByEventId(1000);
    }

    @Test
    void testGetBookingsByEventId() {
        when(repository.findByEventId(1000)).thenReturn(Arrays.asList(sampleTicket));

        List<TicketBooking> bookings = bookingService.getBookingsByEventId(1000);
        assertEquals(1, bookings.size());

        verify(repository, times(1)).findByEventId(1000);
    }

    @Test
    void testCheckBooking_UserHasBooking() {
        when(repository.findByUserIdAndEventId(2000, 1000)).thenReturn(Optional.of(sampleTicket));

        assertTrue(bookingService.checkBooking(2000, 1000));
    }

    @Test
    void testCheckBooking_NoBooking() {
        when(repository.findByUserIdAndEventId(2000, 1000)).thenReturn(Optional.empty());

        assertFalse(bookingService.checkBooking(2000, 1000));
    }
}
	