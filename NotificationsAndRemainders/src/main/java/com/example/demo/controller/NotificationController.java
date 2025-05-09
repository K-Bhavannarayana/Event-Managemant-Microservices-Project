package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserNotificationDTO;
import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {

	NotificationService service;

	@PostMapping("/add")
	String addNotification(@RequestBody Notification note) {
		return service.addNotification(note);
	}

	@GetMapping("/getById/{nid}")
	UserNotificationDTO getNotification(@PathVariable("nid") int notificationId) {
		return service.getNotification(notificationId);
	}

	@DeleteMapping("/delete/{nid}")
	String removeNotification(@PathVariable("nid") int notificationId) {
		return service.removeNotification(notificationId);
	}

	@GetMapping("/getAll")
	List<Notification> viewAllNotifications() {
		return service.viewAllNotifications();
	}
}