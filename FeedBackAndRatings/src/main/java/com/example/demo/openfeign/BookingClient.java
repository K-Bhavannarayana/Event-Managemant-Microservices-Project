package com.example.demo.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="TICKETBOOKING", path="/booking")
public interface BookingClient {
	
	@GetMapping("/checkBooking/{uid}/{eid}")
	boolean checkBooking(@PathVariable("uid") int userId, @PathVariable("eid") int eventId);

}
