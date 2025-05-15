package com.example.demo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.EventUsersListDTO;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.model.Event;
import com.example.demo.openfeign.FeedbackClient;
import com.example.demo.openfeign.NotificationClient;
import com.example.demo.openfeign.TicketBookingClient;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.EventServiceImpl;

@SpringBootTest
class EventManagementApplicationTests {

    @Mock
    private EventRepository repository;

    @Mock
    private TicketBookingClient ticketBookingClient;

    @Mock
    private NotificationClient notificationClient;

    @Mock
    private FeedbackClient feedbackClient;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event sampleEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleEvent = new Event();
        sampleEvent.setEventId(1);
        sampleEvent.setEventName("Tech Conference");
        sampleEvent.setEventDate(new Date(System.currentTimeMillis() + 86400000)); // Future Date
    }

    @Test
    void testAddEvent_Success() {
        when(repository.save(sampleEvent)).thenReturn(sampleEvent);
        String result = eventService.addEvent(sampleEvent);
        assertEquals("Event Details Saved Successfully", result);
        verify(repository, times(1)).save(sampleEvent);
    }

    @Test
    void testUpdateEvent_Success() {
        when(repository.save(sampleEvent)).thenReturn(sampleEvent);
        when(ticketBookingClient.getAllUserIdsByEventId(sampleEvent.getEventId())).thenReturn(Arrays.asList(1, 2, 3));
        
        String result = eventService.updateEvent(sampleEvent, "Updated details");
        
        assertEquals("Event Details Updated Successfully", result);
        verify(repository, times(1)).save(sampleEvent);
        verify(notificationClient, times(1)).sendNotifications(any(EventUsersListDTO.class));
    }

    @Test
    void testDeleteEvent_EventNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(1, "Event is canceled");
        });

        assertEquals("Event not found with given Event Id", exception.getMessage());
        verify(repository, never()).deleteById(1);
    }

    @Test
    void testGetEvent_FutureEvent() throws EventNotFoundException {
        when(repository.findById(1)).thenReturn(Optional.of(sampleEvent));
        Object event = eventService.getEvent(1);
        assertTrue(event instanceof Event);
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testGetBookingsByEventId_EventNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.getBookingsByEventId(1);
        });

        assertEquals("Event not found with given Event Id", exception.getMessage());
    }

    @Test
    void testGetEventPresence_True() {
        when(repository.findById(1)).thenReturn(Optional.of(sampleEvent));
        assertTrue(eventService.getEventPresence(1));
    }

    @Test
    void testGetEventPresence_False() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertFalse(eventService.getEventPresence(1));
    }
}

