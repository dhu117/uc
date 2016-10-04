package com.dhu.uc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dhu.uc.model.AdminAuth;
import com.dhu.uc.model.Role;
import com.dhu.uc.model.RoleAuth;

@Repository
public interface RoleDao {

	public void insertRole(Role role);

	public List<Role> listRoleByParameter(Map<String, Object> pMap);

	public Integer countRoleByParameter(Map<String, Object> pMap);

	public void updateRole(Role role);

	public void deleteRoleByCode(String roleCode);

	public void deleteRoleAuth(Map<String, Object> pMap);

	public void addRoleAuth(RoleAuth roleAuth);

	public List<AdminAuth> listUnAuthByRoleCode(String roleCode);

	public List<RoleAuth> listRoleAuths(String roleCode);

	public Role getRoleByRoleCode(String roleCode);
	

	
	

}