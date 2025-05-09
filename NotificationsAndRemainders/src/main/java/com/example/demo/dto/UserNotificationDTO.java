package com.example.demo.dto;

import com.example.demo.model.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNotificationDTO {

	private UserDetails userDetails;
	private Notification notification;
}
