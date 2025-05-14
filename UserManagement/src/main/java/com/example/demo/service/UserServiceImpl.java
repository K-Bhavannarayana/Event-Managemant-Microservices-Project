package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.UserDetails;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	UserRepository repository;

	//Adds user details to database by taking UserDetails object as parameter and returns a string
	public String addUser(UserDetails userDetails) {
		UserDetails ud = repository.save(userDetails);
		if (ud != null)
			return "User Details Saved Successfully";
		return "Error saving the user";
	}

	//Updates user details to database by taking UserDetails object as parameter and returns a string
	@Override
	public String updateUser(UserDetails userDetails) {
		UserDetails ud = repository.save(userDetails);
		if (ud != null)
			return "User Details Updated Successfully";
		return "Error Updating the user";
	}

	//
	@Override
	public UserDetails getUserDetails(int userId) throws UserNotFoundException {
		Optional<UserDetails> optional = repository.findById(userId);
		if (optional.isPresent())
			return optional.get();
		else
			throw new UserNotFoundException("User not found with given user Id");
	}

	@Override
	public String removeUser(int userId) {
		repository.deleteById(userId);
		return "User Details deleted";
	}

	@Override
	public List<UserDetails> getAllUsersDetails() {
		return repository.findAll();
	}

	@Override
	public boolean getUserPresence(int userId) {
		Optional<UserDetails> optional=repository.findById(userId);
		return optional.isPresent();
	}

}
