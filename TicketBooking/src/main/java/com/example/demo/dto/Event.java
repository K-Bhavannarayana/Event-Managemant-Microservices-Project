package com.example.demo.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Event {
	private int eventId;
	private String eventName;
	private String eventCategory;
	private String eventLocation;
	private Date eventDate;
	private int eventOrganizerId;

}
