package com.dhu.uc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhu.framework.cache.annotation.CheckCache;
import com.dhu.framework.cache.annotation.EvictCache;
import com.dhu.uc.dao.RoleDao;
import com.dhu.uc.model.AdminAuth;
import com.dhu.uc.model.Role;
import com.dhu.uc.model.RoleAuth;

@Service
public class RoleService {
	@Autowired
	RoleDao roleDao;

	public Role getRoleByRoleCode(String roleCode){
		return roleDao.getRoleByRoleCode(roleCode);
	}
	/**
	 * 
	 * 插入用户
	 */
	public void insertRole(Role role) {
		roleDao.insertRole(role);
	}

	public Integer countRoleByParameter(Integer type,String keyword) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("type", type);
		pMap.put("keyword", keyword);
		return roleDao.countRoleByParameter(pMap);
	}

	public List<Role> listRoleByParameter(Integer type, String keyword, Integer pageSize,
			Integer offset) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("keyword", keyword);
		pMap.put("type", type);
		pMap.put("pageSize", pageSize);
		pMap.put("offset", offset);
		return roleDao.listRoleByParameter(pMap);
	}

	public void update(Role role) {
		roleDao.updateRole(role);
	}

	public void deleteRole(String roleCode) {
		roleDao.deleteRoleByCode(roleCode);
	}

	
	public List<Role> listRoleByParameter(String keyword, Integer pageSize, Integer offset) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("keyword", keyword);
		pMap.put("pageSize",pageSize);
		pMap.put("offset",offset);
		return roleDao.listRoleByParameter(pMap);
	}

	public Integer countRoleByParameter(String keyword) {
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("keyword", keyword);
		return roleDao.countRoleByParameter(pMap);
	}

	public void updateRole(Role role) {
		// TODO Auto-generated method stub
		roleDao.updateRole(role);
		
	}
	/**
	 * 查询该角色已授权的权限列表
	 * @param roleCode
	 * @return
	 */
	@CheckCache(timeToLive=60*60*1)
	public List<RoleAuth>  listRoleAuths(String roleCode){
		return roleDao.listRoleAuths(roleCode);
	}
	
	/**
	 * 查询该角色未授权的权限列表
	 * @param roleCode
	 * @return
	 */
	@CheckCache(timeToLive=60*60*1)
	public List<AdminAuth> listUnAuthRoles(String roleCode){
		return roleDao.listUnAuthByRoleCode(roleCode);
	}
	/**
	 * 删除角色和权限关系
	 * @param authId
	 * @param roleCode
	 */
	@EvictCache(key="'RoleService.listUnAuthRoles_'+#roleCode,'RoleService.listRoleAuths_'+#roleCode")
	public void deleteRoleAuth(Long authId,String roleCode){
		Map<String, Object> pMap = new HashMap<>();
		pMap.put("authId", authId);
		pMap.put("roleCode",roleCode);
		roleDao.deleteRoleAuth(pMap);
	}
	/**
	 * 插入管理员和角色关系，并清除分配角色和未分配角色缓存
	 * @param userName
	 * @param roleCode
	 */
	@EvictCache(key= "'RoleService.listUnAuthRoles_'+#roleAuth.getRoleCode(),'RoleService.listRoleAuths_'+#roleAuth.getRoleCode()")
	public void addRoleAuth(RoleAuth roleAuth){
		roleDao.addRoleAuth(roleAuth);
	}
}
