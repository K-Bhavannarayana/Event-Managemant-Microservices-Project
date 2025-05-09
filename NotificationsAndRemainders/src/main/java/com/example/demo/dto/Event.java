package com.example.demo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	private int eventId;
	private String eventName;
	private String eventCategory;
	private String eventLocation;
	private Date eventDate;
	private int eventOrganizerId;
}
