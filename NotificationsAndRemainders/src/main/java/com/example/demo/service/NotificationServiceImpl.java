package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    NotificationRepository repository;
    UserClient userClient;

    /**
     * Adds a new notification to the database.
     * @param note The notification object to be saved.
     * @return Success message if saved successfully, otherwise failure message.
     */
    @Override
    public String addNotification(Notification note) {
        logger.info("Adding notification for user ID {} and event ID {}", note.getUserId(), note.getEventId());
        Notification n = repository.save(note);
        if (n == note) {
            logger.info("Notification sent successfully for user ID {}", note.getUserId());
            return "Notification Sent Successfully";
        } else {
            logger.warn("Notification could not be sent for user ID {}", note.getUserId());
            return "Notification not sent";
        }
    }

    /**
     * Removes a notification by ID.
     * @param notificationId The ID of the notification to remove.
     * @return Success message after removing the notification.
     */
    @Override
    public String removeNotification(int notificationId) {
        logger.info("Removing notification with ID {}", notificationId);
        repository.deleteById(notificationId);
        logger.info("Notification removed successfully");
        return "Notification removed";
    }

    /**
     * Retrieves all notifications stored in the database.
     * @return List of all notifications.
     */
    @Override
    public List<Notification> viewAllNotifications() {
        logger.info("Fetching all notifications");
        return repository.findAll();
    }

    /**
     * Retrieves a notification along with user details by notification ID.
     * @param notificationId The ID of the notification to retrieve.
     * @return User and notification details if found, otherwise null.
     */
    @Override
    public UserNotificationDTO getNotification(int notificationId) {
        logger.info("Fetching notification with ID {}", notificationId);
        Optional<Notification> optional = repository.findById(notificationId);
        if (optional.isPresent()) {
            Notification notification = optional.get();
            logger.info("Notification found for user ID {}", notification.getUserId());
            UserDetails userDetails = userClient.getUserDetails(notification.getUserId());
            logger.info("User details retrieved for user ID {}", notification.getUserId());
            return new UserNotificationDTO(userDetails, notification);
        }
        logger.warn("Notification not found for ID {}", notificationId);
        return null;
    }

    /**
     * Sends notifications to multiple users about changes in an event.
     * @param eventUsersListDto Contains event details and list of user IDs to be notified.
     */
    @Override
    public void sendNotifications(EventUsersListDTO eventUsersListDto) {
        Date date = new Date();
        Event event = eventUsersListDto.getEvent();
        List<Integer> usersList = eventUsersListDto.getUsersList();
        logger.info("Sending notifications for event ID {} to {} users", event.getEventId(), usersList.size());
        for (int userId : usersList) {
            Notification notification = new Notification();
            notification.setEventId(event.getEventId());
            notification.setUserId(userId);
            notification.setMessage(eventUsersListDto.getMessage());
            notification.setSentTimeStamp(date);
            logger.info("Sending notification to user ID {}", userId);
            addNotification(notification);
        }
        logger.info("All notifications sent successfully for event ID {}", event.getEventId());
    }
}
