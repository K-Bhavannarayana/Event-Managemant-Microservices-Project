package com.example.demo.openfeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.Feedback;

@FeignClient(name="FEEDBACKANDRATINGS", path="/feedback")
public interface FeedbackClient {

	@GetMapping("/getEventRating/{eid}")
	float getEventRating(@PathVariable("eid") int eventId);
	
	@GetMapping("/getAllFeedbacksByEventId/{eid}")
	List<Feedback> getAllFeedbacksByEventId(@PathVariable("eid") int eventId);
}
