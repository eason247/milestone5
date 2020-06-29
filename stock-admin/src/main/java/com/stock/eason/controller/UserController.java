package com.stock.eason.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String register( User user) {
		return userService.register(user);
	}

	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String login( User user,HttpSession session) {
		
		if(userService.login(user)) {
			session.setAttribute("user", user);
			return "redirect:/admin/index";
		}else {
			return "redirect:/admin/loginerror";
		}
		
	}
	
	@RequestMapping("/users/{id}")
	public User getUserById(@PathVariable(value = "id") Integer id) {
		return userService.findById(id);
	}

	@RequestMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable Integer id) {
		return userService.deleteUser(id);
	}
	
	
}
