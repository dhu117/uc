package com.dhu.uc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.dhu.framework.cache.annotation.CheckCache;
import com.dhu.framework.cache.annotation.EvictCache;
import com.dhu.uc.dao.AdminDao;
import com.dhu.uc.model.Admin;
import com.dhu.uc.model.AdminRole;
import com.dhu.uc.model.Role;

@Service
public class AdminService {
	@Autowired
	AdminDao adminDao;

	/**
	 * 
	 * 通过用户名或者手机号查找用户信息
	 */
	@CheckCache(timeToLive = 60 * 60 * 4,cacheNull=false)
	public Admin getAdminByUserName(String userName) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("userName", userName);
		return adminDao.getAdminByUserName(userName);
	}

	/**
	 * 
	 * 插入用户
	 */
	public void insertAndGetId(Admin user) {
		 adminDao.insertSelective(user);
	}

	/**
	 * 
	 * 删除管理员
	 */
	public void deleteAdmin(String userName) {
		 adminDao.deleteByUserName(userName);
	}

	public List<Admin> listAdminByParameter(String keyword, Integer pageSize, Integer offset) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("keyword", keyword);
		pMap.put("pageSize",pageSize);
		pMap.put("offset",offset);
		return adminDao.listAdminByParameter(pMap);
	}

	public Integer countAdminByParameter(String keyword) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("keyword", keyword);
		return adminDao.countAdminByParameter(pMap);
	}

	public void updateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		adminDao.updateAdmin(admin);
		
	}
	@CheckCache(timeToLive=60*60*1)
	public List<AdminRole>  listAdminRoles(String userName){
		return adminDao.listAdminRoles(userName);
	}
	
	@CheckCache(timeToLive=60*60*1)
	public List<Role> listUnAuthRoles(String userName){
		return adminDao.listUnAuthRoles(userName);
	}
	/**
	 * 删除管理员和角色关系，并清除分配角色和未分配角色缓存
	 * @param userName
	 * @param roleCode
	 */
	@EvictCache(key="'AdminService.listUnAuthRoles_'+#userName,'AdminService.listAdminRoles_'+#userName,'AuthService.getAuthTree_'+#userName")
	public void deleteAdminRole(String userName,String roleCode){
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("userName", userName);
		pMap.put("roleCode",roleCode);
		adminDao.deleteAdminRole(pMap);
	}
	/**
	 * 插入管理员和角色关系，并清除分配角色和未分配角色缓存
	 * @param userName
	 * @param roleCode
	 */
	@EvictCache(key= "'AdminService.listUnAuthRoles_'+#adminRole.getUserName(),'AdminService.listAdminRoles_'+#adminRole.getUserName(),'AuthService.getAuthTree_'+#adminRole.getUserName()")
	public void addAdminRole(AdminRole adminRole){
		adminDao.addAdminRole(adminRole);
	}
}
