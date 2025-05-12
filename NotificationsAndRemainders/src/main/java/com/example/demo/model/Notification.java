package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
	@SequenceGenerator(name = "event_seq", sequenceName = "event_sequence", initialValue = 4000, allocationSize = 1)
	private int notificationId;
	@Min(value = 2000, message = "User ID must be at least 2000")
    private int userId;

    @Min(value = 1000, message = "Event ID must be at least 1000")
    private int eventId;

    @NotBlank(message = "Message cannot be empty")
    private String message;

    private Date sentTimeStamp;
}
