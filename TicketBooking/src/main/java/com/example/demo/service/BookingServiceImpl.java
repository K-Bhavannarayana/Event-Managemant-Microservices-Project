package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Event;
import com.example.demo.dto.EventTicketDTO;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.TicketNotFoundException;
import com.example.demo.model.TicketBooking;
import com.example.demo.openfeign.EventClient;
import com.example.demo.repository.BookingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService{

	BookingRepository repository;
	
	EventClient eventClient;
	
	@Override
	public String bookTicket(TicketBooking ticket) {
		TicketBooking tb = repository.save(ticket);
		if(tb==ticket) {
			return "Ticket booked successfully";
		}
		else {
			return "No tickets left";
		}
	}

	@Override
	public String cancelTicket(TicketBooking ticket) {
		repository.save(ticket);
		return "Tickets canceled successfully";
	}

	@Override
	public EventTicketDTO getTicketById(int ticketId) throws TicketNotFoundException, EventNotFoundException {
		Optional<TicketBooking> optional = repository.findById(ticketId);
		if(optional.isPresent()) {
			TicketBooking ticket = optional.get();
			int eventId = ticket.getEventId();
			Event event = eventClient.getEvent(eventId);
			return new EventTicketDTO(event,ticket);
		}
		else {
			throw new TicketNotFoundException("Ticket not found with given ticket id");
		}
		
	}

	@Override
	public List<TicketBooking> viewAllTickets() {
		return repository.findAll();
	}

	@Override
	public List<Integer> getAllUserIdsByEventId(int eventId) {
		return repository.getAllUserIdsByEventId(eventId);
	}

	@Override
	public void cancelTicketsByEventId(int eventId) {
		repository.cancelTicketsByEventId(eventId);
	}
	
	@Override
	public List<TicketBooking> getBookingsByEventId(int eventId){
		return repository.findByEventId(eventId);
	}
	

}
