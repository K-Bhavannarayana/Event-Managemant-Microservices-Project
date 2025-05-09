package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventUsersListDTO {

	private Event event;
	private List<Integer> usersList;
	private String message;
}
