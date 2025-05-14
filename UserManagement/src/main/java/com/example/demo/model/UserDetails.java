package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
	@SequenceGenerator(name = "event_seq", sequenceName = "event_sequence", initialValue = 2000, allocationSize = 1)
	private int userId;
	@NotBlank(message = "Username cannot be empty")
    private String userName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String userEmail;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotBlank(message = "Password cannot be empty")
    private String userPassword;

    @Size(min = 8, message = "Contact number must be 10 digits")
    private String userContactNumber;
}
