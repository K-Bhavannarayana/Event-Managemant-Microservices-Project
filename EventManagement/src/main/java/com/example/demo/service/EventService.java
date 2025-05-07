package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.EventNotFoundException;
import com.example.demo.model.Event;

public interface EventService {

	String addEvent(Event event);

	String updateEvent(Event event);

	String deleteEvent(int eventId) throws EventNotFoundException;

	Event getEvent(int eventId) throws EventNotFoundException;

	List<Event> getAllEvents();
}
