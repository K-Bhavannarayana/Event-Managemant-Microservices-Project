package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	EventRepository repository;
	
	TicketBookingClient ticketBookingClient;
	
	NotificationClient notificationClient;
	
	FeedbackClient feedbackClient;

	//Takes Event object as input and save it in database and returns a string
	@Override
	public String addEvent(Event event) {
		Event e = repository.save(event);
		if (e == event) {
			return "Event Details Saved Successfully";
		} else {
			return "Event Details Not Saved";
		}
	}

	//Takes Event Object as input updates the details in the database and returns a string
	@Override
	public String updateEvent(Event event, String message){
		Event event1 = repository.save(event);
		List<Integer> usersList = ticketBookingClient.getAllUserIdsByEventId(event.getEventId());
		notificationClient.sendNotifications(new EventUsersListDTO(event1,usersList,message));
		return "Event Details Updated Successfully";
	}

	@Override
	//Requires event Id and reason message to notify users and cancel the event 
	public String deleteEvent(int eventId, String message) throws EventNotFoundException {
		Optional<Event> optional = repository.findById(eventId);
		if (optional.isPresent()) {
			List<Integer> usersList = ticketBookingClient.getAllUserIdsByEventId(eventId);
			Event event = optional.get();
			notificationClient.sendNotifications(new EventUsersListDTO(event,usersList,message));
			ticketBookingClient.cancelTicketsByEventId(eventId);
			repository.deleteById(eventId);
			return "Event Deleted";
		} else {
			throw new EventNotFoundException("Event not found with given Event Id");
		}
	}

	//if present or future displays event details, else event rating and feedbacks are also displayed
	@Override
	public Object getEvent(int eventId) throws EventNotFoundException {
		Optional<Event> optional = repository.findById(eventId);
		if (optional.isPresent()) {
			Event event = optional.get();
			Date currentDate = new Date();
			if(event.getEventDate().before(currentDate)) {
				return new EventFeedbackRatingDTO(event,feedbackClient.getEventRating(eventId),feedbackClient.getAllFeedbacksByEventId(eventId));
			}
			else {
				return optional.get();
			}
		}
		throw new EventNotFoundException("Event not found with given Event Id");
	}

	//Displays all event details
	@Override
	public List<Event> getAllEvents() {
		return repository.findAll();
	}

	//Takes event Id and returns event and bookings of that particular event
	@Override
	public EventBookingsDTO getBookingsByEventId(int eventId) throws EventNotFoundException {
		Optional<Event> optional = repository.findById(eventId);
		if (optional.isPresent()) {
			return new EventBookingsDTO(optional.get(), ticketBookingClient.getBookingsByEventId(eventId));
		}
		else {
			throw new EventNotFoundException("Event not found with given Event Id");
		}
	}

	@Override
	public boolean getEventPresence(int eventId) {
		Optional<Event> optional=repository.findById(eventId);
		return optional.isPresent();
	}

}
