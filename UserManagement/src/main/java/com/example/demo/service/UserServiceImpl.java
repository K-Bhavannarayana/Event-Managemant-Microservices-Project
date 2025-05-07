package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.UserDetails;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;

	@Override
	public String addUser(UserDetails userDetails) {
		UserDetails ud = repository.save(userDetails);
		if (ud != null)
			return "User Details Saved Successfully";
		return "Error saving the user";
	}

	@Override
	public String updateUser(UserDetails userDetails) {
		UserDetails ud = repository.save(userDetails);
		if (ud != null)
			return "User Details Updated Successfully";
		return "Error Updating the user";
	}

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

}
