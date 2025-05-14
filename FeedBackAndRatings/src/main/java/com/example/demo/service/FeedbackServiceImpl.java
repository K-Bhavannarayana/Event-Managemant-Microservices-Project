package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Feedback;
import com.example.demo.openfeign.BookingClient;
import com.example.demo.repository.FeedbackRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService{

	FeedbackRepository repository;
	
	BookingClient bookingClient;
	
	//Takes feedback as input, saves it in database and returns a string
	@Override
	public String addFeedback(Feedback feedback) {
		Optional<Feedback> optional = repository.findByEventIdAndUserId(feedback.getEventId(),feedback.getUserId());
		if(optional.isPresent()) {
			return "you have already given the feedback";
		}
		if(bookingClient.checkBooking(feedback.getUserId(),feedback.getEventId())) {
			Feedback fb = repository.save(feedback);
			if(fb==feedback) {
				return "Feedback submitted successfully";
			}
			else {
				return "Not able to submit, try later";
			}
		}
		else {
			return "You have not attended the event, so you can not give feedback";
		}
	}

	//Returns event rating based on event Id
	@Override
	public float getEventRating(int eventId) {
		return repository.getEventRating(eventId);
	}
	
	//Returns feedback based on feedback Id
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

	//Returns all feedbacks given to an event by Id
	@Override
	public List<Feedback> getAllFeedbacksByEventId(int eventId) {
		return repository.findByEventId(eventId);
	}

	//returns all feedbacks given by a user by taking user Id
	@Override
	public List<Feedback> getAllFeedbacksByUserId(int userId) {
		return repository.findByUserId(userId);
	}

}
