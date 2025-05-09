package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.EventUsersListDTO;
import com.example.demo.dto.UserNotificationDTO;
import com.example.demo.model.Notification;

public interface NotificationService {

		String addNotification(Notification note);
		
		UserNotificationDTO getNotification(int notificationId);
		
		String removeNotification(int notificationId);
		
		List<Notification> viewAllNotifications();
		
		void sendNotifications(EventUsersListDTO eventUsersListDto);
}
