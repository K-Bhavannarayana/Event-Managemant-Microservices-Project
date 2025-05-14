package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EventBookingsDTO;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.model.Event;
import com.example.demo.service.EventService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

	EventService service;

	@PostMapping("/add")
	String addEvent(@Valid @RequestBody Event event) {
		return service.addEvent(event);
	}

	@PutMapping("/update/{message}")
	String updateEvent(@Valid @RequestBody Event event,@PathVariable("message") String message) {
		return service.updateEvent(event, message);
	}

	@DeleteMapping("/delete/{eid}/{msg}")
	String deleteEvent(@PathVariable("eid") int eventId,@PathVariable("msg") String message) throws EventNotFoundException {
		return service.deleteEvent(eventId,message);
	}

	@GetMapping("/getEventById/{eid}")
	Object getEvent(@PathVariable("eid") int eventId) throws EventNotFoundException {
		return service.getEvent(eventId);
	}

	@GetMapping("/getAllEvents")
	List<Event> getAllEvents() {
		return service.getAllEvents();
	}
	
	@GetMapping("/getBookingsByEventId/{eid}")
	EventBookingsDTO getBookingsByEventId(@PathVariable("eid") int eventId) throws EventNotFoundException{
		return service.getBookingsByEventId(eventId);
	}

	@GetMapping("/getEventPresence/{eid}")
	boolean getEventPresence(@PathVariable("eid") int eventId) {
		return service.getEventPresence(eventId);
	}
}
