package com.example.demo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Feedback {

	private int feedbackId;
	private int eventId;
	private int userId;
	private float rating;
	private String comment;
	private Date submittedTimestamp;
}
