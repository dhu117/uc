package com.dhu.uc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhu.framework.cache.annotation.CheckCache;
import com.dhu.uc.dao.UserDao;
import com.dhu.uc.model.User;

@Service
public class UserService {
	@Autowired
	UserDao userDao;

	/**
	 * 
	 * 通过用户名或者手机号查找用户信息
	 */
	@CheckCache(timeToLive = 60 * 60 * 4)
	public User findUser(String userName, String phone) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("userName", userName);
		pMap.put("phone", phone);
		return userDao.findUser(pMap);
	}

	/**
	 * 
	 * 插入用户
	 */
	public void insertAndGetId(User user) {
		userDao.insertAndGetId(user);
	}
	
	public Integer countUserByParameter(Integer type, Integer gender, String keyword) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("keyword", keyword);
		pMap.put("type", type);
		pMap.put("gender",gender);
		return userDao.countUserByParameter(pMap);
	}
	
	public List<User> listUserByParameter(Integer type, Integer gender, String keyword,Integer pageSize,Integer offset) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("keyword", keyword);
		pMap.put("type", type);
		pMap.put("gender",gender);
		pMap.put("pageSize",pageSize);
		pMap.put("offset",offset);
		return userDao.listUserByParameter(pMap);
	}

	public void update(User user) {
		userDao.updateUser(user);
	}

	public void deleteUserById(Long ucid) {
		userDao.deleteUserById(ucid);
	}

}
