package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.TicketNotFoundException;
import com.example.demo.model.TicketBooking;
import com.example.demo.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService{

	@Autowired
	BookingRepository repository;
	
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
	public TicketBooking getTicketById(int ticketId) throws TicketNotFoundException {
		Optional<TicketBooking> optional = repository.findById(ticketId);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			throw new TicketNotFoundException("Ticket not found with given ticket id");
		}
	}

	@Override
	public List<TicketBooking> viewAllTickets() {
		return repository.findAll();
	}

}
