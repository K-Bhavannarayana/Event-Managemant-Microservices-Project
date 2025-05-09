package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventFeedbackRatingDTO {

	Event event;
	float rating;
	List<Feedback> feedbackList;
}
