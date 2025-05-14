package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.UserDetails;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

	UserService service;

	@PostMapping("/add")
	String addUser(@Valid @RequestBody UserDetails userDetails) {
		return service.addUser(userDetails);
	}

	@PutMapping("/update")
	String updateUser(@Valid @RequestBody UserDetails userDetails) {
		return service.updateUser(userDetails);
	}

	@GetMapping("/getUserById/{uid}")
	UserDetails getUserDetails(@PathVariable("uid") int userId) throws UserNotFoundException {
		return service.getUserDetails(userId);
	}

	@DeleteMapping("/removeUserById/{uid}")
	String removeUser(@PathVariable("uid") int userId) {
		return service.removeUser(userId);
	}

	@GetMapping("/getAllUsers")
	List<UserDetails> getAllUsersDetails() {
		return service.getAllUsersDetails();
	}
	
	@GetMapping("/getUserPresence/{uid}")
	boolean getUserPresence(@PathVariable("uid") int userId) {
		return service.getUserPresence(userId);
	}
}

