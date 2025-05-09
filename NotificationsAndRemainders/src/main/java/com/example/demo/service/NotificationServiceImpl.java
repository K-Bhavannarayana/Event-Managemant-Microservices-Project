package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Event;
import com.example.demo.dto.EventUsersListDTO;
import com.example.demo.dto.UserDetails;
import com.example.demo.dto.UserNotificationDTO;
import com.example.demo.model.Notification;
import com.example.demo.openfeign.UserClient;
import com.example.demo.repository.NotificationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{

	NotificationRepository repository;
	
	UserClient userClient;
	
	@Override
	public String addNotification(Notification note) {
		Notification n = repository.save(note);
		if(n==note) {
			return "Notification Send Successfully";
		}
		else {
			return "Notification not sent";
		}
	}

	@Override
	public String removeNotification(int notificationId) {
		repository.deleteById(notificationId);
		return "Notification removed";
	}

	@Override
	public List<Notification> viewAllNotifications() {
		return repository.findAll();
	}

	@Override
	public UserNotificationDTO getNotification(int notificationId) {
		Optional<Notification> optional = repository.findById(notificationId);
		if(optional.isPresent()) {
			Notification notification = optional.get();
			UserDetails userDetails = userClient.getUserDetails(notification.getUserId());
			return new UserNotificationDTO(userDetails,notification);
			
		}
		return null;
	}

	@Override
	public void sendNotifications(EventUsersListDTO eventUsersListDto) {
		Date date=new Date();
		Event event=eventUsersListDto.getEvent();
		List<Integer> usersList= eventUsersListDto.getUsersList();
		for(int userId: usersList) {
			Notification notification = new Notification();
			notification.setEventId(event.getEventId());
			notification.setUserId(userId);
			notification.setMessage(eventUsersListDto.getMessage());
			notification.setSentTimeStamp(date);
			addNotification(notification);
		}
	}

}
