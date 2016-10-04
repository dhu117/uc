package com.dhu.uc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dhu.uc.model.Admin;
import com.dhu.uc.model.AdminRole;
import com.dhu.uc.model.Role;

@Repository
public interface AdminDao {
	
	/**
	 * 搜索已有的角色
	 * @param pMap
	 * @return
	 */
	public List<AdminRole>listAdminRoles(String userName);
	
	/**
	 * 搜索该用户还没有的角色
	 * @param userName
	 * @return
	 */
	public List<Role>listUnAuthRoles(String userName);
	
	public void addAdminRole(AdminRole adminRole);
	
	public List<Admin> findAdmin(Map<String, Object> pMap);

	void insertSelective(Admin record);

	Admin getAdminByUserName(String userName);

	int updateAdmin(Admin admin);
	
	 int deleteByUserName(String userName);

	public List<Admin> listAdminByParameter(Map<String, Object> pMap);

	public Integer countAdminByParameter(Map<String, Object> pMap);

	public void deleteAdminRole(Map<String, Object> pMap);

}