package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.UserDetails;

public interface UserService {

	String addUser(UserDetails userDetails);

	String updateUser(UserDetails userDetails);

	UserDetails getUserDetails(int userId) throws UserNotFoundException;

	String removeUser(int userId);

	List<UserDetails> getAllUsersDetails();

}
