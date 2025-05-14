package com.example.demo.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="USERMANAGEMENT", path="/user")
public interface UserClient {

	@GetMapping("/getUserPresence/{uid}")
	boolean getUserPresence(@PathVariable("uid") int userId);
}
