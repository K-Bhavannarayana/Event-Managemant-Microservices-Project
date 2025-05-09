package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Feedback;

public interface FeedbackService {
	
	String addFeedback(Feedback feedback);
	
	float getEventRating(int eventId);
	
	Feedback getFeedbackById(int feedbackId);
	
	List<Feedback> getAllFeedbacksByEventId(int eventId);
	
	List<Feedback> getAllFeedbacksByUserId(int userId);

}
