package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

	@Id
	@GeneratedValue
	private int feedbackId;
	private int eventId;
	private int userId;
	private float rating;
	private String comment;
	private Date submittedTimestamp;
}
