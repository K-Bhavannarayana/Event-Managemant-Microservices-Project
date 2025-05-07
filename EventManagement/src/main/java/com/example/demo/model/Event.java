package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	
	@Id
	@GeneratedValue
	private int eventId;
	private String eventName;
	private String eventCategory;
	private String eventLocation;
	private Date eventDate;
	private int eventOrganizerId;
}
