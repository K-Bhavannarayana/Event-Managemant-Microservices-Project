package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EventBookingsDTO;
import com.example.demo.dto.EventFeedbackRatingDTO;
import com.example.demo.dto.EventUsersListDTO;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.model.Event;
import com.example.demo.openfeign.FeedbackClient;
import com.example.demo.openfeign.NotificationClient;
import com.example.demo.openfeign.TicketBookingClient;
import com.example.demo.repository.EventRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    EventRepository repository;
    TicketBookingClient ticketBookingClient;
    NotificationClient notificationClient;
    FeedbackClient feedbackClient;

    /**
     * Adds a new event to the database.
     * @param event The event object to be saved.
     * @return Success message if saved successfully, otherwise failure message.
     */
    @Override
    public String addEvent(Event event) {
        logger.info("Adding event: {}", event);
        Event e = repository.save(event);
        if (e == event) {
            logger.info("Event details saved successfully");
            return "Event Details Saved Successfully";
        } else {
            logger.warn("Event details not saved");
            return "Event Details Not Saved";
        }
    }
    
    /**
     * Updates an existing event's details and sends notifications to users.
     * @param event The updated event object.
     * @param message Notification message for users.
     * @return Success message after updating the event.
     */
    @Override
    public String updateEvent(Event event, String message) {
        logger.info("Updating event: {}", event);
        Event event1 = repository.save(event);
        List<Integer> usersList = ticketBookingClient.getAllUserIdsByEventId(event.getEventId());
        notificationClient.sendNotifications(new EventUsersListDTO(event1, usersList, message));
        logger.info("Event details updated successfully");
        return "Event Details Updated Successfully";
    }

    /**
     * Deletes an event by ID, notifies users, and cancels tickets.
     * @param eventId The ID of the event to delete.
     * @param message Cancellation reason to notify users.
     * @return Success message if the event is deleted.
     * @throws EventNotFoundException if the event does not exist.
     */
    @Override
    public String deleteEvent(int eventId, String message) throws EventNotFoundException {
        logger.info("Deleting event with ID: {}", eventId);
        Optional<Event> optional = repository.findById(eventId);
        if (optional.isPresent()) {
            Event event = optional.get();
            logger.info("Event found: {}", event);
            List<Integer> usersList = ticketBookingClient.getAllUserIdsByEventId(eventId);
            notificationClient.sendNotifications(new EventUsersListDTO(event, usersList, message));
            ticketBookingClient.cancelTicketsByEventId(eventId);
            repository.deleteById(eventId);
            logger.info("Event deleted successfully");
            return "Event Deleted";
        } else {
            logger.error("Event not found with ID: {}", eventId);
            throw new EventNotFoundException("Event not found with given Event Id");
        }
    }

    /**
     * Retrieves an event by ID. If the event has already occurred, fetches feedback and ratings.
     * @param eventId The ID of the event to retrieve.
     * @return Event details if the event is upcoming, or feedback and ratings if the event is past.
     * @throws EventNotFoundException if the event does not exist.
     */
    @Override
    public Object getEvent(int eventId) throws EventNotFoundException {
        logger.info("Fetching event with ID: {}", eventId);
        Optional<Event> optional = repository.findById(eventId);
        if (optional.isPresent()) {
            Event event = optional.get();
            Date currentDate = new Date();
            if (event.getEventDate().before(currentDate)) {
                logger.info("Fetching feedback and rating for past event ID: {}", eventId);
                return new EventFeedbackRatingDTO(event, feedbackClient.getEventRating(eventId),
                        feedbackClient.getAllFeedbacksByEventId(eventId));
            } else {
                logger.info("Returning upcoming event details");
                return event;
            }
        }
        logger.error("Event not found with ID: {}", eventId);
        throw new EventNotFoundException("Event not found with given Event Id");
    }

    /**
     * Retrieves all events from the database.
     * @return List of all available events.
     */
    @Override
    public List<Event> getAllEvents() {
        logger.info("Fetching all events");
        return repository.findAll();
    }

    /**
     * Retrieves event bookings by event ID.
     * @param eventId The ID of the event.
     * @return Event details along with associated bookings.
     * @throws EventNotFoundException if the event does not exist.
     */
    @Override
    public EventBookingsDTO getBookingsByEventId(int eventId) throws EventNotFoundException {
        logger.info("Fetching bookings for event ID: {}", eventId);
        Optional<Event> optional = repository.findById(eventId);
        if (optional.isPresent()) {
            logger.info("Returning bookings for event ID: {}", eventId);
            return new EventBookingsDTO(optional.get(), ticketBookingClient.getBookingsByEventId(eventId));
        } else {
            logger.error("Event not found with ID: {}", eventId);
            throw new EventNotFoundException("Event not found with given Event Id");
        }
    }

    /**
     * Checks if an event with the given ID is present in the database.
     * @param eventId The ID of the event to check.
     * @return True if the event exists, false otherwise.
     */
    @Override
    public boolean getEventPresence(int eventId) {
        logger.info("Checking presence of event ID: {}", eventId);
        Optional<Event> optional = repository.findById(eventId);
        boolean present = optional.isPresent();
        logger.info("Event presence status for ID {}: {}", eventId, present);
        return present;
    }
}