package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Event;
import com.example.demo.dto.EventTicketDTO;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.TicketNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.TicketBooking;
import com.example.demo.openfeign.EventClient;
import com.example.demo.openfeign.UserClient;
import com.example.demo.repository.BookingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    BookingRepository repository;
    EventClient eventClient;
    UserClient userClient;

    /**
     * Books a ticket for an event if the event and user exist and the ticket is available.
     * @param ticket The ticket booking request.
     * @return Success or failure message based on ticket availability.
     * @throws EventNotFoundException If the event does not exist.
     * @throws UserNotFoundException If the user does not exist.
     */
    @Override
    public String bookTicket(TicketBooking ticket) throws EventNotFoundException, UserNotFoundException {
        logger.info("Attempting to book ticket for user ID {} at event ID {}", ticket.getUserId(), ticket.getEventId());
        if (eventClient.getEventPresence(ticket.getEventId())) {
            if (userClient.getUserPresence(ticket.getUserId())) {
                Optional<TicketBooking> optional = repository.findByUserIdAndEventId(ticket.getUserId(), ticket.getEventId());
                if (optional.isEmpty() || optional.get().getStatus().equals("cancelled")) {
                    TicketBooking tb = repository.save(ticket);
                    if (tb == ticket) {
                        logger.info("Ticket booked successfully for user ID {} at event ID {}", ticket.getUserId(), ticket.getEventId());
                        return "Ticket booked successfully";
                    } else {
                        logger.warn("No tickets left for event ID {}", ticket.getEventId());
                        return "No tickets left";
                    }
                } else {
                    logger.warn("User ID {} has already booked a ticket for event ID {}", ticket.getUserId(), ticket.getEventId());
                    return "Ticket already booked";
                }
            } else {
                logger.error("User ID {} not found", ticket.getUserId());
                throw new UserNotFoundException("User Not Found");
            }
        }
        logger.error("Event ID {} not found", ticket.getEventId());
        throw new EventNotFoundException("Event Not Found");
    }

    /**
     * Cancels a booked ticket.
     * @param ticket The ticket to be cancelled.
     * @return Success message after cancellation.
     */
    @Override
    public String cancelTicket(TicketBooking ticket) {
        logger.info("Cancelling ticket ID {}", ticket.getTicketId());
        repository.save(ticket);
        logger.info("Tickets cancelled successfully for ticket ID {}", ticket.getTicketId());
        return "Tickets canceled successfully";
    }

    /**
     * Retrieves ticket details along with event details by ticket ID.
     * @param ticketId The ID of the ticket.
     * @return Event and ticket details.
     * @throws TicketNotFoundException If the ticket does not exist.
     * @throws EventNotFoundException If the event does not exist.
     */
    @Override
    public EventTicketDTO getTicketById(int ticketId) throws TicketNotFoundException, EventNotFoundException {
        logger.info("Fetching ticket details for ticket ID {}", ticketId);
        Optional<TicketBooking> optional = repository.findById(ticketId);
        if (optional.isPresent()) {
            TicketBooking ticket = optional.get();
            Event event = eventClient.getEvent(ticket.getEventId());
            logger.info("Ticket and event details retrieved for ticket ID {}", ticketId);
            return new EventTicketDTO(event, ticket);
        } else {
            logger.error("Ticket ID {} not found", ticketId);
            throw new TicketNotFoundException("Ticket not found with given ticket id");
        }
    }

    /**
     * Retrieves all tickets from the database.
     * @return List of all booked tickets.
     */
    @Override
    public List<TicketBooking> viewAllTickets() {
        logger.info("Fetching all tickets");
        return repository.findAll();
    }

    /**
     * Retrieves all user IDs who have booked tickets for a particular event.
     * @param eventId The ID of the event.
     * @return List of user IDs.
     */
    @Override
    public List<Integer> getAllUserIdsByEventId(int eventId) {
        logger.info("Fetching all user IDs for event ID {}", eventId);
        return repository.getAllUserIdsByEventId(eventId);
    }

    /**
     * Cancels all tickets booked for a specific event.
     * @param eventId The ID of the event.
     */
    @Override
    public void cancelTicketsByEventId(int eventId) {
        logger.info("Cancelling all tickets for event ID {}", eventId);
        repository.cancelTicketsByEventId(eventId);
        logger.info("All tickets cancelled for event ID {}", eventId);
    }

    /**
     * Retrieves all ticket bookings for a particular event.
     * @param eventId The ID of the event.
     * @return List of ticket bookings.
     */
    @Override
    public List<TicketBooking> getBookingsByEventId(int eventId) {
        logger.info("Fetching ticket bookings for event ID {}", eventId);
        return repository.findByEventId(eventId);
    }

    /**
     * Checks if a user has a booking for a specific event.
     * @param userId The ID of the user.
     * @param eventId The ID of the event.
     * @return True if the user has a booking, false otherwise.
     */
    @Override
    public boolean checkBooking(int userId, int eventId) {
        logger.info("Checking ticket booking status for user ID {} at event ID {}", userId, eventId);
        Optional<TicketBooking> optional = repository.findByUserIdAndEventId(userId, eventId);
        boolean isBooked = optional.isPresent();
        logger.info("Ticket booking presence for user ID {} at event ID {}: {}", userId, eventId, isBooked);
        return isBooked;
    }
}