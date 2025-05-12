package com.example.demo.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.EventUsersListDTO;

@FeignClient(name="NOTIFICATIONSANDREMAINDERS", path="/notifications")
public interface NotificationClient {

	@PostMapping("/sendNotifications")
	void sendNotifications(EventUsersListDTO eventUsersListDTO);
}
