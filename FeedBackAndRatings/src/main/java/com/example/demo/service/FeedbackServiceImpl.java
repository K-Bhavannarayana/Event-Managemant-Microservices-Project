package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Feedback;
import com.example.demo.repository.FeedbackRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService{

	@Autowired
	FeedbackRepository repository;
	
	@Override
	public String addFeedback(Feedback feedback) {
		Feedback fb = repository.save(feedback);
		if(fb==feedback) {
			return "Feedback submitted successfully";
		}
		else {
			return "Not able to submit try later";
		}
	}

	@Override
	public float getEventRating(int eventId) {
		return repository.getEventRating(eventId);
	}

	@Override
	public Feedback getFeedbackById(int feedbackId) {
		Optional<Feedback> optional = repository.findById(feedbackId);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			return null;
		}
	}

	@Override
	public List<Feedback> getAllFeedbacksByEventId(int eventId) {
		return repository.findByEventId(eventId);
	}

	@Override
	public List<Feedback> getAllFeedbacksByUserId(int userId) {
		return repository.findByUserId(userId);
	}

}
