package com.stock.eason.service;

import com.stock.eason.bean.User;

public interface UserService {

	String register(User user);

	boolean login(User user);

	User findById(Integer id);

	String deleteUser(Integer id);

}
