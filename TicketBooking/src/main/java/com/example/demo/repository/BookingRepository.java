package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.TicketBooking;

public interface BookingRepository extends JpaRepository<TicketBooking, Integer>{

}
