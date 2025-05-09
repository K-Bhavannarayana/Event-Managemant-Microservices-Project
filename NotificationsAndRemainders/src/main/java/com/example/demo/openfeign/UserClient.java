package com.example.demo.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.UserDetails;

@FeignClient(name="USERREGISTRATION", path="/user")
public interface UserClient {

	@GetMapping("/getUserById/{uid}")
	UserDetails getUserDetails(@PathVariable("uid") int userId);
}
