package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

	List<Feedback> findByEventId(int eventId);

	List<Feedback> findByUserId(int userId);

	@Query("SELECT COALESCE(AVG(f.rating),0.0) FROM Feedback f where f.eventId= ?1 ")
	float getEventRating(int eventId);

}
