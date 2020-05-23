package com.stock.eason.service;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.stock.eason.bean.User;
import com.stock.eason.util.DBUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserServiceImpl implements UserService{

	@Override
	public String register(User user) {
		DBUtil.saveOrUpdate(user);
		return null;
	}

	@Override
	public boolean login(User user) {
		String sql = "from User where username = "+user.getUsername();
		ArrayList<User> list = DBUtil.selectByParam(sql);
		if(list!=null) {
			User u = list.get(0);
			return u.getPassword()==user.getPassword();
		}else {
			return false;
		}
	}
	
	

}
