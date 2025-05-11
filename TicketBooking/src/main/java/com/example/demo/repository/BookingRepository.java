package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.TicketBooking;

import jakarta.transaction.Transactional;

public interface BookingRepository extends JpaRepository<TicketBooking, Integer>{

	@Query("SELECT t.userId FROM TicketBooking t WHERE t.eventId= ?1 ")
	List<Integer> getAllUserIdsByEventId(int eventId);
	
	@Modifying
	@Transactional
	@Query("UPDATE TicketBooking t SET t.status='cancelled' where t.eventId = ?1 ")
	void cancelTicketsByEventId(int eventId);

	List<TicketBooking> findByEventId(int eventId);
}
