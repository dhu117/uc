package com.dhu.uc.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@RequestMapping(value = "/add")
	public String add(HttpServletResponse response) {
		return "user/add";
	}

	@RequestMapping(value = "")
	public String index(HttpServletResponse response) {
		return "user/index";
	}

}
