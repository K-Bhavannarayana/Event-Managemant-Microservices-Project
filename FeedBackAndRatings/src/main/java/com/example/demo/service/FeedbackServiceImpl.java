package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.model.Feedback;
import com.example.demo.openfeign.BookingClient;
import com.example.demo.repository.FeedbackRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService{

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    FeedbackRepository repository;
    BookingClient bookingClient;

    /**
     * Adds feedback for an event if the user has attended the event.
     * @param feedback The feedback object to be saved.
     * @return Success message if submitted, failure messages otherwise.
     */
    @Override
    public String addFeedback(Feedback feedback) {
        logger.info("Attempting to add feedback for event ID {} by user ID {}", feedback.getEventId(), feedback.getUserId());
        Optional<Feedback> optional = repository.findByEventIdAndUserId(feedback.getEventId(), feedback.getUserId());
        if(optional.isPresent()) {
            logger.warn("User ID {} has already given feedback for event ID {}", feedback.getUserId(), feedback.getEventId());
            return "You have already given the feedback";
        }
        if(bookingClient.checkBooking(feedback.getUserId(), feedback.getEventId())) {
            Feedback fb = repository.save(feedback);
            if(fb == feedback) {
                logger.info("Feedback submitted successfully for event ID {} by user ID {}", feedback.getEventId(), feedback.getUserId());
                return "Feedback submitted successfully";
            } else {
                logger.error("Feedback submission failed for event ID {} by user ID {}", feedback.getEventId(), feedback.getUserId());
                return "Not able to submit, try later";
            }
        } else {
            logger.warn("User ID {} has not attended event ID {}. Feedback not allowed.", feedback.getUserId(), feedback.getEventId());
            return "You have not attended the event, so you cannot give feedback";
        }
    }

    /**
     * Retrieves the average rating of an event based on feedback.
     * @param eventId The event ID for which the rating is retrieved.
     * @return The average event rating.
     */
    @Override
    public float getEventRating(int eventId) {
        logger.info("Fetching event rating for event ID {}", eventId);
        return repository.getEventRating(eventId);
    }

    /**
     * Retrieves feedback details based on feedback ID.
     * @param feedbackId The ID of the feedback to be retrieved.
     * @return Feedback object if present, otherwise null.
     */
    @Override
    public Feedback getFeedbackById(int feedbackId) {
        logger.info("Fetching feedback by ID {}", feedbackId);
        Optional<Feedback> optional = repository.findById(feedbackId);
        if(optional.isPresent()) {
            logger.info("Feedback found for feedback ID {}", feedbackId);
            return optional.get();
        } else {
            logger.warn("No feedback found for feedback ID {}", feedbackId);
            return null;
        }
    }

    /**
     * Retrieves all feedbacks given for an event by its event ID.
     * @param eventId The ID of the event.
     * @return List of feedback objects related to the event.
     */
    @Override
    public List<Feedback> getAllFeedbacksByEventId(int eventId) {
        logger.info("Fetching all feedbacks for event ID {}", eventId);
        return repository.findByEventId(eventId);
    }

    /**
     * Retrieves all feedbacks submitted by a user using the user ID.
     * @param userId The ID of the user.
     * @return List of feedback objects provided by the user.
     */
    @Override
    public List<Feedback> getAllFeedbacksByUserId(int userId) {
        logger.info("Fetching all feedbacks given by user ID {}", userId);
        return repository.findByUserId(userId);
    }
}