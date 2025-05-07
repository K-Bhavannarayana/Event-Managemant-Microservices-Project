package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserDetails {

	@Id
	@GeneratedValue
	private int UserId;
	private String userName;
	private String userEmail;
	private String userPassword;
	private long userContactNumber;
}
