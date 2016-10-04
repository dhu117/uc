package com.dhu.uc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dhu.uc.model.AdminAuth;

@Repository
public interface AuthDao {

	public List<AdminAuth> getAuthTree(Map<String, Object> pMap);

	int insertSelective(AdminAuth record);

	AdminAuth getAdminAuthById(Integer id);

	int updateAdminAuth(AdminAuth record);

	int deleteById(Integer id);

}