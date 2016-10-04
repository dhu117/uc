package com.dhu.uc.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dhu.common.ApiJsonResult;
import com.dhu.common.base.ApiBaseController;
import com.dhu.uc.service.AuthService;

@Controller
@RequestMapping(value = "/api/auth")
public class ApiAuthController extends ApiBaseController {

	@Autowired
	AuthService authService;

	@ResponseBody
	@RequestMapping(value = "/getAuthTree.json")
	public ApiJsonResult getAuthTree(HttpServletResponse response, @RequestParam String userName) {
		ApiJsonResult result = ok();
		try {
			result.put("data", authService.getAuthTree(userName));
		} catch (Exception e) {
			log.debug("查询失败");
			e.printStackTrace();
			return fail(ApiJsonResult.ERRNO_EXCEPTION);
		}
		return result;
	}
}
