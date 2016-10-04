package com.dhu.uc.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@RequestMapping(value = "")
	public String index(HttpServletResponse response) {
		return "admin/index";
	}
	
	@RequestMapping(value = "/role/{userName}")
	public String manageRole(HttpServletResponse response,Model model,@PathVariable("userName")String userName) {
		model.addAttribute("userName", userName);
		return "admin/role";
	}

}
