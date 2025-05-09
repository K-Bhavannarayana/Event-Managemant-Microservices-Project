package com.example.demo.dto;

import lombok.Data;

@Data
public class UserDetails {

	private int UserId;
	private String userName;
	private String userEmail;
	private String userPassword;
	private long userContactNumber;
}
