package com.example.demo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketBooking {

	int ticketId;
	int eventId;
	int userId;
	Date bookingDate;
	String status;
}
