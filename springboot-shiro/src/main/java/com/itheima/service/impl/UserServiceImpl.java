package com.itheima.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.domain.User;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	//注入Mapper接口对象
	@Autowired
	private UserMapper userMapper;
	
	public User findByName(String name) {
		// TODO Auto-generated method stub
		User findByName = userMapper.findByName(name);
		return findByName;
	}
	public User findById(int id) {
		// TODO Auto-generated method stub
		User findByName = userMapper.findById(id);
		return findByName;
	}

}
