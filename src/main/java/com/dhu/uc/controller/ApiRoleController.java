package com.dhu.uc.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dhu.common.ApiJsonResult;
import com.dhu.common.base.ApiBaseController;
import com.dhu.common.util.StringUtil;
import com.dhu.portal.model.PageBean;
import com.dhu.uc.model.AdminAuth;
import com.dhu.uc.model.AdminRole;
import com.dhu.uc.model.Role;
import com.dhu.uc.model.RoleAuth;
import com.dhu.uc.service.RoleService;

@Controller
@RequestMapping(value = "/api/role")
public class ApiRoleController extends ApiBaseController {

	@Autowired
	RoleService roleService;

	@ResponseBody
	@RequestMapping(value = "/deleteRole.json", method = RequestMethod.POST)
	public ApiJsonResult deleteRoleById(HttpServletResponse response, @RequestParam String roleCode) {
		ApiJsonResult result = ok();
		try {
			roleService.deleteRole(roleCode);
		} catch (Exception e) {
			result = fail(ApiJsonResult.ERRNO_EXCEPTION, "注册失败");
			return result;
		}
		return result;
	}


	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public ApiJsonResult update(HttpServletResponse response, @RequestBody Role role) {
		ApiJsonResult result = ok();
		if (null == role.getRoleCode()) {
			return fail(ApiJsonResult.ERRNO_ILLEALDATA, "修改失败");
		}
		try {
			roleService.update(role);
		} catch (Exception e) {
			e.printStackTrace();
			result = fail(ApiJsonResult.ERRNO_EXCEPTION, "修改失败");
			return result;
		}
		result.put("role", role);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public ApiJsonResult add(HttpServletResponse response, @RequestBody Role role) {
		ApiJsonResult result = ok();
		if (null == role.getRoleCode()) {
			return fail(ApiJsonResult.ERRNO_ILLEALDATA, "添加失败");
		}
		try {
			roleService.insertRole(role);
		} catch (Exception e) {
			e.printStackTrace();
			result = fail(ApiJsonResult.ERRNO_EXCEPTION, "添加失败");
			return result;
		}
		result.put("role", role);
		return result;
	}

	/**
	 * 获取用户列表
	 * 
	 * @param response
	 * @param type
	 * @param keyword
	 * @param gender
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRoleList.json")
	public ApiJsonResult getRoles(HttpServletResponse response, Integer type, String keyword,
			Integer page, Integer rows) {
		ApiJsonResult result = ok();
		Integer totalCount = roleService.countRoleByParameter(type, keyword);
		PageBean<Role> pageBean = new PageBean<>(totalCount, rows, page);
		if (totalCount != null && totalCount > 0) {
			List<Role> roleList = roleService.listRoleByParameter(type, keyword, pageBean.getPageSize(),
					pageBean.getOffset());
			pageBean.setPageList(roleList);
		}
		result.put("total", totalCount);
		result.put("rows", pageBean.getPageList());
		return result;
	}
	
	

	/**
	 * 获取角色权限列表
	 */
	@ResponseBody
	@RequestMapping(value = "/getRoleAuthList.json")
	public ApiJsonResult getAdminRoleList(HttpServletResponse response, Integer type, @RequestParam String roleCode,
			Integer page, Integer rows) {
		ApiJsonResult result = ok();
		List<RoleAuth> adminList = roleService.listRoleAuths(roleCode);
		result.put("total", adminList==null?0:adminList.size());
		result.put("rows",adminList);
		return result;
	}
	
	/**
	 * 获取管理员角色列表
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnAuthRoleList.json")
	public JSONArray listUnAuthRoles(HttpServletResponse response, @RequestParam String roleCode,
			Integer page, Integer rows) {
		List<AdminAuth> roleList = roleService.listUnAuthRoles(roleCode);
		JSONArray jsonArray=new JSONArray();
		for(AdminAuth role:roleList){
			JSONObject object= new JSONObject();
			object.put("id", role.getId());
			object.put("text", role.getAuthName());
		jsonArray.add(object);
			
		}
		return jsonArray;
	}
	
	/**
	 *添加用户
	 */
	@ResponseBody
	@RequestMapping(value = "/addRoleAuth.json",method = RequestMethod.POST)
	public ApiJsonResult addAdminRole(HttpServletResponse response, @RequestBody RoleAuth roleAuth) {
		ApiJsonResult result = ok();
		if(roleAuth.getAuthId()==null||StringUtil.isEmpty(roleAuth.getRoleCode())){
			return fail(ApiJsonResult.ERRNO_ILLEALDATA);
		}
		try{
			roleService.addRoleAuth(roleAuth);
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
	@RequestMapping(value = "/deleteRoleAuth.json",method = RequestMethod.POST)
	public ApiJsonResult deleteAdminRole(HttpServletResponse response, @RequestParam Long authId,@RequestParam String roleCode) {
		ApiJsonResult result = ok();
		try{
			roleService.deleteRoleAuth(authId, roleCode);
		}catch(Exception e){
			e.printStackTrace();
			return fail(ApiJsonResult.ERRNO_EXCEPTION,"删除失败");
		}
		return result;
	}
}
