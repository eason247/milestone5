package com.stock.eason.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.stock.eason.bean.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserServiceImpl implements UserService{

	@Override
	public String register(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
