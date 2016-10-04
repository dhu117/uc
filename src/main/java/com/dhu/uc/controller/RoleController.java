package com.dhu.uc.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dhu.uc.service.RoleService;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	RoleService roleService;
	
	@RequestMapping(value = "")
	public String index(HttpServletResponse response) {
		return "role/index";
	}

	@RequestMapping(value = "/auth/{roleCode}")
	public String manageRole(HttpServletResponse response,Model model,@PathVariable("roleCode")String roleCode) {
		model.addAttribute("role", roleService.getRoleByRoleCode(roleCode));
		return "role/auth";
	}

}
