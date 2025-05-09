package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
	 @SequenceGenerator(name = "event_seq", sequenceName = "event_sequence", initialValue = 1000, allocationSize = 1)
	 private int eventId;

	 @NotBlank(message = "Event name cannot be blank")
	 @Size(min = 5, max = 20, message = "Event name must be between 5 to 20 characters")
	 private String eventName;

	 @NotBlank(message = "Event category cannot be blank")
	 @Size(min = 3, max = 20, message = "Event category must be between 3 to 20 characters")
	 private String eventCategory;

	 @NotBlank(message = "Event location cannot be blank")
	 private String eventLocation;

	 @FutureOrPresent(message = "Event date must be in the present or future")
     private Date eventDate;

	 @NotNull(message = "Organizer Id cannot be null")
	 private int eventOrganizerId;
}
