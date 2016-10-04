package com.dhu.uc.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dhu.common.ApiJsonResult;
import com.dhu.common.base.ApiBaseController;
import com.dhu.common.util.StringUtil;
import com.dhu.portal.model.PageBean;
import com.dhu.uc.model.Admin;
import com.dhu.uc.model.AdminRole;
import com.dhu.uc.model.Role;
import com.dhu.uc.service.AdminService;

@Controller
@RequestMapping(value = "/api/admin")
public class ApiAdminController extends ApiBaseController {

	@Autowired
	AdminService adminService;

	@ResponseBody
	@RequestMapping(value = "/login.json")
	public ApiJsonResult login(HttpServletResponse response, @RequestParam String userName,
			@RequestParam String password) {
		ApiJsonResult result = ok();
		Admin admin = adminService.getAdminByUserName(userName);
		if (admin == null || !StringUtil.equals(DigestUtils.md5Hex(password), admin.getPassword())) {
			result = fail(ApiJsonResult.ERRNO_NOT_FIND, "用户登录失败");
		}
		result.put("admin", admin);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public ApiJsonResult register(HttpServletResponse response, @RequestBody Admin user) {
		ApiJsonResult result = ok();
		if (StringUtil.isBlank(user.getUserName()) || StringUtil.isBlank(user.getPassword())) {
			return fail(ApiJsonResult.ERRNO_ILLEALDATA, "注册失败");
		}
		try {
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			adminService.insertAndGetId(user);
		} catch (Exception e) {
			e.printStackTrace();
			result = fail(ApiJsonResult.ERRNO_EXCEPTION, "添加失败");
			return result;
		}
		result.put("user", user);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public ApiJsonResult update(HttpServletResponse response, @RequestBody Admin admin) {
		ApiJsonResult result = ok();
		if (StringUtil.isBlank(admin.getUserName()) || StringUtil.isBlank(admin.getPassword())) {
			return fail(ApiJsonResult.ERRNO_ILLEALDATA, "修改失败");
		}
		try {
			admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
			adminService.updateAdmin(admin);
		} catch (Exception e) {
			e.printStackTrace();
			result = fail(ApiJsonResult.ERRNO_EXCEPTION, "修改失败");
			return result;
		}
		result.put("admin", admin);
		return result;
	}
	

	
	@ResponseBody
	@RequestMapping(value = "/deleteAdmin.json", method = RequestMethod.POST)
	public ApiJsonResult deleteAdmin(HttpServletResponse response,@RequestParam String userName) {
		ApiJsonResult result = ok();
		try {
			adminService.deleteAdmin(userName);
		} catch (Exception e) {
			result = fail(ApiJsonResult.ERRNO_EXCEPTION, "删除失败");
			return result;
		}
		return result;
	}
	
	/**
	 * 获取用户列表
	 * @param response
	 * @param type
	 * @param keyword
	 * @param gender
	 * @param page
	 * @param rows 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAdminList.json")
	public ApiJsonResult getUsers(HttpServletResponse response, Integer type, String keyword, Integer gender,
			Integer page, Integer rows) {
		ApiJsonResult result = ok();
		Integer totalCount = adminService.countAdminByParameter(keyword);
		PageBean<Admin> pageBean = new PageBean<>(totalCount, rows, page);
		if(totalCount!=null&&totalCount>0){
		List<Admin> adminList = adminService.listAdminByParameter(keyword, pageBean.getPageSize(),
				pageBean.getOffset());
		pageBean.setPageList(adminList);
		}
		result.put("total", totalCount);
		result.put("rows",pageBean.getPageList());
		return result;
	}
	
	/**
	 * 获取管理员角色列表
	 */
	@ResponseBody
	@RequestMapping(value = "/getAdminRoleList.json")
	public ApiJsonResult getAdminRoleList(HttpServletResponse response, Integer type, @RequestParam String userName,
			Integer page, Integer rows) {
		ApiJsonResult result = ok();
		List<AdminRole> adminList = adminService.listAdminRoles(userName);
		result.put("total", adminList==null?0:adminList.size());
		result.put("rows",adminList);
		return result;
	}
	
	/**
	 * 获取管理员角色列表
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnAuthRoleList.json")
	public JSONArray listUnAuthRoles(HttpServletResponse response, @RequestParam String userName,
			Integer page, Integer rows) {
		List<Role> roleList = adminService.listUnAuthRoles(userName);
		JSONArray jsonArray=new JSONArray();
		for(Role role:roleList){
			JSONObject object= new JSONObject();
			object.put("id", role.getRoleCode());
			object.put("text", role.getRoleName());
		jsonArray.add(object);
			
		}
		return jsonArray;
	}
	
	/**
	 *添加用户
	 */
	@ResponseBody
	@RequestMapping(value = "/addAdminRole.json",method = RequestMethod.POST)
	public ApiJsonResult addAdminRole(HttpServletResponse response, @RequestBody AdminRole adminRole) {
		ApiJsonResult result = ok();
		try{
			adminService.addAdminRole(adminRole);
		}catch(Exception e){
			e.printStackTrace();
			return fail(ApiJsonResult.ERRNO_EXCEPTION,"添加失败");
		}
		return result;
	}
	
	/**
	 *添加用户
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAdminRole.json",method = RequestMethod.POST)
	public ApiJsonResult deleteAdminRole(HttpServletResponse response, @RequestParam String userName,@RequestParam String roleCode) {
		ApiJsonResult result = ok();
		try{
			adminService.deleteAdminRole(userName, roleCode);
		}catch(Exception e){
			e.printStackTrace();
			return fail(ApiJsonResult.ERRNO_EXCEPTION,"删除失败");
		}
		return result;
	}
}
