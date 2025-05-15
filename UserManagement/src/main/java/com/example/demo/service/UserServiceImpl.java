package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.UserDetails;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    UserRepository repository;

    /**
     * Adds user details to the database.
     * @param userDetails The user details to be saved.
     * @return Success message if saved successfully, otherwise error message.
     */
    public String addUser(UserDetails userDetails) {
        logger.info("Adding user with ID {}", userDetails.getUserId());
        UserDetails ud = repository.save(userDetails);
        if (ud != null) {
            logger.info("User details saved successfully for user ID {}", userDetails.getUserId());
            return "User Details Saved Successfully";
        }
        logger.error("Error saving user details for user ID {}", userDetails.getUserId());
        return "Error saving the user";
    }

    /**
     * Updates user details in the database.
     * @param userDetails The user details to be updated.
     * @return Success message if updated successfully, otherwise error message.
     */
    @Override
    public String updateUser(UserDetails userDetails) {
        logger.info("Updating user with ID {}", userDetails.getUserId());
        UserDetails ud = repository.save(userDetails);
        if (ud != null) {
            logger.info("User details updated successfully for user ID {}", userDetails.getUserId());
            return "User Details Updated Successfully";
        }
        logger.error("Error updating user details for user ID {}", userDetails.getUserId());
        return "Error Updating the user";
    }

    /**
     * Retrieves user details based on user ID.
     * @param userId The user ID to search for.
     * @return UserDetails object if found.
     * @throws UserNotFoundException If no user exists with the given ID.
     */
    @Override
    public UserDetails getUserDetails(int userId) throws UserNotFoundException {
        logger.info("Fetching user details for user ID {}", userId);
        Optional<UserDetails> optional = repository.findById(userId);
        if (optional.isPresent()) {
            logger.info("User details found for user ID {}", userId);
            return optional.get();
        } else {
            logger.error("User not found with ID {}", userId);
            throw new UserNotFoundException("User not found with given user Id");
        }
    }

    /**
     * Removes a user from the database based on user ID.
     * @param userId The user ID to remove.
     * @return Success message after deletion.
     */
    @Override
    public String removeUser(int userId) {
        logger.info("Removing user with ID {}", userId);
        repository.deleteById(userId);
        logger.info("User with ID {} deleted successfully", userId);
        return "User Details deleted";
    }

    /**
     * Retrieves all user details stored in the database.
     * @return List of all user details.
     */
    @Override
    public List<UserDetails> getAllUsersDetails() {
        logger.info("Fetching all user details");
        return repository.findAll();
    }

    /**
     * Checks if a user is present in the database based on user ID.
     * @param userId The user ID to check for presence.
     * @return True if the user exists, false otherwise.
     */
    @Override
    public boolean getUserPresence(int userId) {
        logger.info("Checking presence of user ID {}", userId);
        Optional<UserDetails> optional = repository.findById(userId);
        boolean isPresent = optional.isPresent();
        logger.info("User presence status for ID {}: {}", userId, isPresent);
        return isPresent;
    }
}