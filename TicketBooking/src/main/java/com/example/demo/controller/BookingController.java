package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.TicketNotFoundException;
import com.example.demo.model.TicketBooking;
import com.example.demo.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
	
	@Autowired
	BookingService service;
	
	@PostMapping("/bookTicket")
	String bookTicket(@RequestBody TicketBooking ticket) {
		return service.bookTicket(ticket);
	}
	
	@PutMapping("/cancelTicket")
	String cancelTicket(@RequestBody TicketBooking ticket) {
		return service.cancelTicket(ticket);
	}
	
	@GetMapping("/getTicketById/{tid}")
	TicketBooking getTicketById(@PathVariable("tid") int ticketid) throws TicketNotFoundException {
		return service.getTicketById(ticketid);
	}
	
	@GetMapping("/getAllTickets")
	List<TicketBooking> getAllTickets(){
		return service.viewAllTickets();
	}

}
