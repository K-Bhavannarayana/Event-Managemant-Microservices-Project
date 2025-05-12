package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Feedback;
import com.example.demo.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
	
	@Autowired
	FeedbackService service;
	
	@PostMapping("/add")
	String addFeedback(@RequestBody Feedback feedback) {
		return service.addFeedback(feedback);
	}

	@GetMapping("/getEventRating/{eid}")
	float getEventRating(@PathVariable("eid") int eventId) {
		return service.getEventRating(eventId);
	}
	
	@GetMapping("/getById/{fid}")
	Feedback getFeedbackById(@PathVariable("fid") int feedbackId) {
		return service.getFeedbackById(feedbackId);
	}
	
	@GetMapping("/getAllFeedbacksByEventId/{eid}")
	List<Feedback> getAllFeedbacksByEventId(@PathVariable("eid") int eventId){
		return service.getAllFeedbacksByEventId(eventId);
	}
	
	@GetMapping("/getAllFeedbackByUserId/{uid}")
	List<Feedback> getAllFeedbacksByUserId(@PathVariable("uid") int userId){
		return service.getAllFeedbacksByUserId(userId);
	}
}
