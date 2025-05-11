package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
	@SequenceGenerator(name = "event_seq", sequenceName = "event_sequence", initialValue = 3000, allocationSize = 1)
	private int ticketId;

    @Min(value = 1000, message = "Event ID must be greater than 999")
    private int eventId;

    @Min(value = 2000, message = "User ID must be greater than 999")
    private int userId;

    @FutureOrPresent(message = "Booking date cannot be in the past")
    private Date bookingDate;

    @NotBlank(message="Booking status cannot be blank")
    private String status;

}
