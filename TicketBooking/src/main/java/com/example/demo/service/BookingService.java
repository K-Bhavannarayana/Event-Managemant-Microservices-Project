package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.TicketNotFoundException;
import com.example.demo.model.TicketBooking;

public interface BookingService {

	String bookTicket(TicketBooking ticket);
	
	String cancelTicket(TicketBooking ticket);
	
	TicketBooking getTicketById(int ticketId) throws TicketNotFoundException;
	
	List<TicketBooking> viewAllTickets();
}
