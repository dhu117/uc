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

import com.dhu.common.ApiJsonResult;
import com.dhu.common.Constants;
import com.dhu.common.base.ApiBaseController;
import com.dhu.common.util.StringUtil;
import com.dhu.portal.model.PageBean;
import com.dhu.uc.model.User;
import com.dhu.uc.service.UserService;

@Controller
@RequestMapping(value = "/api/user")
public class ApiUserController extends ApiBaseController {

	@Autowired
	UserService userService;

	@ResponseBody
	@RequestMapping(value = "/login.json")
	public ApiJsonResult login(HttpServletResponse response, @RequestParam String phone,
			@RequestParam String password) {
		ApiJsonResult result = ok();
		User user = userService.findUser(null, phone);
		if (user == null || !StringUtil.equals(DigestUtils.md5Hex(password), user.getPassword())) {
			result = fail(ApiJsonResult.ERRNO_NOT_FIND, "用户登录失败");
		}
		result.put("user", user);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteUserbyId.json", method = RequestMethod.POST)
	public ApiJsonResult deleteUserById(HttpServletResponse response,@RequestParam Long ucid) {
		ApiJsonResult result = ok();
		try {
			userService.deleteUserById(ucid);
		} catch (Exception e) {
			result = fail(ApiJsonResult.ERRNO_EXCEPTION, "注册失败");
			return result;
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/register.json", method = RequestMethod.POST)
	public ApiJsonResult register(HttpServletResponse response, @RequestBody User user) {
		ApiJsonResult result = ok();
		if (StringUtil.isBlank(user.getPhone()) || StringUtil.isBlank(user.getPassword())) {
			return fail(ApiJsonResult.ERRNO_ILLEALDATA, "注册失败");
		}
		try {
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			userService.insertAndGetId(user);
		} catch (Exception e) {
			result = fail(ApiJsonResult.ERRNO_EXCEPTION, "注册失败");
			return result;
		}
		result.put("user", user);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public ApiJsonResult update(HttpServletResponse response, @RequestBody User user) {
		ApiJsonResult result = ok();
		if (null==user.getUcid()) {
			return fail(ApiJsonResult.ERRNO_ILLEALDATA, "修改失败");
		}
		try {
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			userService.update(user);
		} catch (Exception e) {
			e.printStackTrace();
			result = fail(ApiJsonResult.ERRNO_EXCEPTION, "修改失败");
			return result;
		}
		result.put("user", user);
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
	@RequestMapping(value = "/getUsers.json")
	public ApiJsonResult getUsers(HttpServletResponse response, Integer type, String keyword, Integer gender,
			Integer page, Integer rows) {
		ApiJsonResult result = ok();
		Integer totalCount = userService.countUserByParameter(type, gender, keyword);
		PageBean<User> pageBean = new PageBean<>(totalCount, rows, page);
		if(totalCount!=null&&totalCount>0){
		List<User> userList = userService.listUserByParameter(type, gender, keyword, pageBean.getPageSize(),
				pageBean.getOffset());
		pageBean.setPageList(userList);
		}
		result.put("total", totalCount);
		result.put("rows",pageBean.getPageList());
		return result;
	}
}
