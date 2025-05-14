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

import com.example.demo.dto.EventTicketDTO;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.TicketNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.TicketBooking;
import com.example.demo.service.BookingService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {
	
	BookingService service;
	
	@PostMapping("/bookTicket")
	String bookTicket(@Valid @RequestBody TicketBooking ticket) throws EventNotFoundException, UserNotFoundException {
		return service.bookTicket(ticket);
	}
	
	@PutMapping("/cancelTicket")
	String cancelTicket(@Valid @RequestBody TicketBooking ticket) {
		return service.cancelTicket(ticket);
	}
	
	@GetMapping("/getTicketById/{tid}")
	EventTicketDTO getTicketById(@PathVariable("tid") int ticketid) throws TicketNotFoundException, EventNotFoundException {
		return service.getTicketById(ticketid);
	}
	
	@GetMapping("/getAllTickets")
	List<TicketBooking> getAllTickets(){
		return service.viewAllTickets();
	}
	
	@GetMapping("/getAllUserIdsByEventId/{eid}")
	List<Integer> getAllUserIdsByEventId(@PathVariable("eid") int eventId){
		return service.getAllUserIdsByEventId(eventId);
	}
	
	@DeleteMapping("/cancelTicketsByEventId/{eid}")
	void cancelTicketsByEventId(@PathVariable("eid") int eventId) {
		service.cancelTicketsByEventId(eventId);
	}
	
	@GetMapping("/getBookingsByEventId/{eid}")
	List<TicketBooking> getBookingsByEventId(@PathVariable("eid") int eventId){
		return service.getBookingsByEventId(eventId);
	}
	
	@GetMapping("/checkBooking/{uid}/{eid}")
	boolean checkBooking(@PathVariable("uid") int userId, @PathVariable("eid") int eventId) {
		return service.checkBooking(userId, eventId);
	}

}
