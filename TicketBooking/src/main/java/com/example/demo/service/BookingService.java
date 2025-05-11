package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.EventTicketDTO;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.TicketNotFoundException;
import com.example.demo.model.TicketBooking;

public interface BookingService {

	String bookTicket(TicketBooking ticket);
	
	String cancelTicket(TicketBooking ticket);
	
	EventTicketDTO getTicketById(int ticketId) throws TicketNotFoundException, EventNotFoundException;
	
	List<TicketBooking> viewAllTickets();
	
	List<Integer> getAllUserIdsByEventId(int eventId);
	
	void cancelTicketsByEventId(int eventId);
	
	List<TicketBooking> getBookingsByEventId(int eventId);
}
