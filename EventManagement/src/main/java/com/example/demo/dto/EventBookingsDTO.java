package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventBookingsDTO {

	Event event;
	List<TicketBooking> bookingsList;
}
