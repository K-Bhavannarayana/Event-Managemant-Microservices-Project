package com.example.demo.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.EventNotFoundException;
import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository repository;

	@Override
	public String addEvent(Event event) {
		Event e = repository.save(event);
		if (e == event) {
			return "Event Details Saved Successfully";
		} else {
			return "Event Details Not Saved";
		}
	}

	@Override
	public String updateEvent(Event event) {
		Event e = repository.save(event);
		return "Event Details Updated Successfully";
		
	}

	@Override
	public String deleteEvent(int eventId) throws EventNotFoundException {
		Optional<Event> optional = repository.findById(eventId);
		if (optional.isPresent()) {
			repository.delete(optional.get());
			return "Event Deleted";
		} else {
			throw new EventNotFoundException("Event not found with given Event Id");
		}
	}

	@Override
	public Event getEvent(int eventId) throws EventNotFoundException {
		Optional<Event> optional = repository.findById(eventId);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new EventNotFoundException("Event not found with given Event Id");
	}

	@Override
	public List<Event> getAllEvents() {
		return repository.findAll();
	}

}
