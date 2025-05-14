package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Event;
import com.example.demo.dto.EventTicketDTO;
import com.example.demo.exception.EventNotFoundException;
import com.example.demo.exception.TicketNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.TicketBooking;
import com.example.demo.openfeign.EventClient;
import com.example.demo.openfeign.UserClient;
import com.example.demo.repository.BookingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService{

	BookingRepository repository;
	
	EventClient eventClient;
	
	UserClient userClient;
	
	//This will add ticket to database taking ticket object and returning string
	@Override
	public String bookTicket(TicketBooking ticket) throws EventNotFoundException, UserNotFoundException {
		if(eventClient.getEventPresence(ticket.getEventId())) {
			if(userClient.getUserPresence(ticket.getUserId())) {
				Optional<TicketBooking> optional= repository.findByUserIdAndEventId(ticket.getUserId(),ticket.getEventId());
				if(optional.isEmpty() || optional.get().getStatus().equals("cancelled")) {
					TicketBooking tb = repository.save(ticket);
					if(tb==ticket) {
						return "Ticket booked successfully";
					}
					else {
						return "No tickets left";
					}
				}
				else {
					return "Ticket already booked";
				}
			}
			else {
				throw new UserNotFoundException("User Not Found");
			}
		}
		throw new EventNotFoundException("Event Not Found");
	}

	//This method will change the status of ticket to cancelled based on ticket object
	@Override
	public String cancelTicket(TicketBooking ticket) {
		repository.save(ticket);
		return "Tickets canceled successfully";
	}

	//This method will display ticket details along with event details by taking ticket Id parameter
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

	//This will display all ticket details
	@Override
	public List<TicketBooking> viewAllTickets() {
		return repository.findAll();
	}

	//Returns all users based on their booking made made to that event based on event Id
	@Override
	public List<Integer> getAllUserIdsByEventId(int eventId) {
		return repository.getAllUserIdsByEventId(eventId);
	}

	//cancel tickets booked for a particular event based on event Id
	@Override
	public void cancelTicketsByEventId(int eventId) {
		repository.cancelTicketsByEventId(eventId);
	}
	
	//Returns bookings to a particular event based on event Id
	@Override
	public List<TicketBooking> getBookingsByEventId(int eventId){
		return repository.findByEventId(eventId);
	}

	@Override
	public boolean checkBooking(int userId, int eventId) {
		Optional<TicketBooking> optional = repository.findByUserIdAndEventId(userId, eventId);
		return optional.isPresent();
	}
	

}
