package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.EventNotFoundException;
import com.example.demo.model.Event;
import com.example.demo.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	EventService service;

	@PostMapping("/add")
	String addEvent(@RequestBody Event event) {
		return service.addEvent(event);
	}

	@PutMapping("/update")
	String updateEvent(@RequestBody Event event) {
		return service.updateEvent(event);
	}

	@DeleteMapping("/delete/{eid}")
	String deleteEvent(@PathVariable("eid") int eventId) throws EventNotFoundException {
		return service.deleteEvent(eventId);
	}

	@GetMapping("/getEventById/{eid}")
	Event getEvent(@PathVariable("eid") int eventId) throws EventNotFoundException {
		return service.getEvent(eventId);
	}

	@GetMapping("/getAllEvents")
	List<Event> getAllEvents() {
		return service.getAllEvents();
	}

}
