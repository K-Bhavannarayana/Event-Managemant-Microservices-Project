package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.Event;
import com.example.demo.dto.UserDetails;
import com.example.demo.dto.UserNotificationDTO;
import com.example.demo.model.Notification;
import com.example.demo.openfeign.UserClient;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.service.NotificationServiceImpl;

@SpringBootTest
class NotificationsAndRemaindersApplicationTests {

    @Mock
    private NotificationRepository repository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private Notification sampleNotification;
    private Event sampleEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleNotification = new Notification();
        sampleNotification.setNotificationId(4000); // Notification ID starts from 4000
        sampleNotification.setEventId(1000);        // Event ID starts from 1000
        sampleNotification.setUserId(2000);         // User ID starts from 2000
        sampleNotification.setMessage("Event updated");
        sampleNotification.setSentTimeStamp(new Date());

        sampleEvent = new Event();
        sampleEvent.setEventId(1000);
        sampleEvent.setEventName("Tech Meetup");
        sampleEvent.setEventDate(new Date());
    }

    @Test
    void testAddNotification_Success() {
        when(repository.save(sampleNotification)).thenReturn(sampleNotification);

        String result = notificationService.addNotification(sampleNotification);
        assertEquals("Notification Sent Successfully", result);

        verify(repository, times(1)).save(sampleNotification);
    }

    @Test
    void testRemoveNotification() {
        doNothing().when(repository).deleteById(4000);

        String result = notificationService.removeNotification(4000);
        assertEquals("Notification removed", result);

        verify(repository, times(1)).deleteById(4000);
    }

    @Test
    void testViewAllNotifications() {
        when(repository.findAll()).thenReturn(Arrays.asList(sampleNotification));

        List<Notification> notifications = notificationService.viewAllNotifications();
        assertEquals(1, notifications.size());
        assertEquals(4000, notifications.get(0).getNotificationId());

        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetNotification_Found() {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(2000);
        userDetails.setUserName("John Doe");

        when(repository.findById(4000)).thenReturn(Optional.of(sampleNotification));
        when(userClient.getUserDetails(2000)).thenReturn(userDetails);

        UserNotificationDTO result = notificationService.getNotification(4000);
        assertNotNull(result);
        assertEquals("John Doe", result.getUserDetails().getUserName());

        verify(repository, times(1)).findById(4000);
        verify(userClient, times(1)).getUserDetails(2000);
    }

    @Test
    void testGetNotification_NotFound() {
        when(repository.findById(4000)).thenReturn(Optional.empty());

        UserNotificationDTO result = notificationService.getNotification(4000);
        assertNull(result);

        verify(repository, times(1)).findById(4000);
        verify(userClient, never()).getUserDetails(anyInt());
    }

}
