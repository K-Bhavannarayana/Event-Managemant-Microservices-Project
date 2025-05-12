package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
	@SequenceGenerator(name = "event_seq", sequenceName = "event_sequence", initialValue = 5000, allocationSize = 1)
	private int feedbackId;
	
	@Min(value = 1000, message = "Event ID must be at least 1000")
    private int eventId;

    @Min(value = 2000, message = "User ID must be at least 2000")
    private int userId;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "5.0", message = "Rating must not exceed 5")
    private float rating;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    private Date submittedTimestamp;}
