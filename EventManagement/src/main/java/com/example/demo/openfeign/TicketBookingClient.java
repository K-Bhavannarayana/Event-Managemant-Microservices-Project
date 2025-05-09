package com.example.demo.openfeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.TicketBooking;

@FeignClient(name="TICKETBOOKING", path="/booking")
public interface TicketBookingClient {

	@GetMapping("/getAllUserIdsByEventId/{eid}")
	List<Integer> getAllUserIdsByEventId(@PathVariable("eid") int eventId);
	
	@DeleteMapping("/cancelTicketsByEventId/{eid}")
	void cancelTicketsByEventId(@PathVariable("eid") int eventId);
	
	@GetMapping("/getBookingsByEventId/{eid}")
	List<TicketBooking> getBookingsByEventId(@PathVariable("eid") int eventId);
}
