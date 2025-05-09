package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.EventBookingsDTO;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.model.Event;

public interface EventService {

	String addEvent(Event event);

	String updateEvent(Event event, String message);

	String deleteEvent(int eventId,String message) throws EventNotFoundException;

	Object getEvent(int eventId) throws EventNotFoundException;

	List<Event> getAllEvents();
	
	EventBookingsDTO getBookingsByEventId(int eventId) throws EventNotFoundException;
}
