package com.example.demo.dto;

import com.example.demo.model.TicketBooking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventTicketDTO {

	private Event event;
	private TicketBooking ticket;
}
