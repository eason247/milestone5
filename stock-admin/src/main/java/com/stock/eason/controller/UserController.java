package com.stock.eason.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stock.eason.bean.User;
import com.stock.eason.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
    @Autowired
    private UserService userService;
	
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public String register(@RequestBody User user) {
		return userService.register(user);
	}

	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String login(@RequestBody User user) {
		
		if(userService.login(user)) {
			
		}
		
		return null;
	}
	
	
}
