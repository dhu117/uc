package com.dhu.uc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhu.framework.cache.annotation.CheckCache;
import com.dhu.uc.dao.AuthDao;
import com.dhu.uc.dao.AdminDao;
import com.dhu.uc.model.Admin;
import com.dhu.uc.model.AdminAuth;

@Service
public class AuthService {
	@Autowired
	AuthDao authDao;

	/**
	 * 
	 * 查询权限树
	 */
	public List<AdminAuth> getAuthTree(String userName) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("userName", userName);
		return authDao.getAuthTree(pMap);
	}

	
	

}
