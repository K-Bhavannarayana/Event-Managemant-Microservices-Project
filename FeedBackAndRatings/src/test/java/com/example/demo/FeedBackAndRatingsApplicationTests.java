package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Feedback;
import com.example.demo.openfeign.BookingClient;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.service.FeedbackServiceImpl;

@SpringBootTest
class FeedBackAndRatingsApplicationTests {


    @Mock
    private FeedbackRepository repository;

    @Mock
    private BookingClient bookingClient;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    private Feedback sampleFeedback;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleFeedback = new Feedback();
        sampleFeedback.setFeedbackId(5000); 
        sampleFeedback.setEventId(1000);    
        sampleFeedback.setUserId(2000);     
        sampleFeedback.setComment("Great event!");
        sampleFeedback.setRating(5);
    }

    @Test
    void testAddFeedback_Success() {
        when(repository.findByEventIdAndUserId(1000, 2000)).thenReturn(Optional.empty());
        when(bookingClient.checkBooking(2000, 1000)).thenReturn(true);
        when(repository.save(sampleFeedback)).thenReturn(sampleFeedback);

        String result = feedbackService.addFeedback(sampleFeedback);
        assertEquals("Feedback submitted successfully", result);

        verify(repository, times(1)).save(sampleFeedback);
    }

    @Test
    void testAddFeedback_AlreadySubmitted() {
        when(repository.findByEventIdAndUserId(1000, 2000)).thenReturn(Optional.of(sampleFeedback));

        String result = feedbackService.addFeedback(sampleFeedback);
        assertEquals("You have already given the feedback", result);
    }

    @Test
    void testAddFeedback_UserDidNotAttend() {
        when(repository.findByEventIdAndUserId(1000, 2000)).thenReturn(Optional.empty());
        when(bookingClient.checkBooking(2000, 1000)).thenReturn(false);

        String result = feedbackService.addFeedback(sampleFeedback);
        assertEquals("You have not attended the event, so you cannot give feedback", result);
    }

    @Test
    void testGetEventRating() {
        when(repository.getEventRating(1000)).thenReturn(4.5f);

        float rating = feedbackService.getEventRating(1000);
        assertEquals(4.5f, rating);
    }

    @Test
    void testGetFeedbackById_Found() {
        when(repository.findById(5000)).thenReturn(Optional.of(sampleFeedback));

        Feedback feedback = feedbackService.getFeedbackById(5000);
        assertNotNull(feedback);
        assertEquals("Great event!", feedback.getComment());
    }

    @Test
    void testGetFeedbackById_NotFound() {
        when(repository.findById(5000)).thenReturn(Optional.empty());

        Feedback feedback = feedbackService.getFeedbackById(5000);
        assertNull(feedback);
    }

    @Test
    void testGetAllFeedbacksByEventId() {
        when(repository.findByEventId(1000)).thenReturn(Arrays.asList(sampleFeedback));

        assertEquals(1, feedbackService.getAllFeedbacksByEventId(1000).size());
    }

    @Test
    void testGetAllFeedbacksByUserId() {
        when(repository.findByUserId(2000)).thenReturn(Arrays.asList(sampleFeedback));

        assertEquals(1, feedbackService.getAllFeedbacksByUserId(2000).size());
    }
}