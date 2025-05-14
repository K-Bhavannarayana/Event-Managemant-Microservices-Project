package com.example.demo.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.Event;
import com.example.demo.exception.EventNotFoundException;

//@FeignClient(name="DEPARTMENTSERVICE",url="http://localhost:1432/department")
@FeignClient(name="EVENTMANAGEMENT",path="/event")
public interface EventClient {
	
	@GetMapping("/getEventById/{eid}")
	Event getEvent(@PathVariable("eid") int eventId) throws EventNotFoundException ;
	
	@GetMapping("/getEventPresence/{eid}")
	boolean getEventPresence(@PathVariable("eid") int eventId);
}