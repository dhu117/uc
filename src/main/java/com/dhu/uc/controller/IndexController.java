package com.dhu.uc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dhu.common.base.BaseController;
import com.dhu.framework.cache.CacheManager;
import com.dhu.portal.service.LoginService;

import net.rubyeye.xmemcached.MemcachedClient;

@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController {

	@Autowired
	LoginService loginService;
	
	
	@Resource(name ="ljmemcachedClient")
	MemcachedClient ljmemcachedClient;
	
	@RequestMapping(value = "")
	public String index(HttpServletResponse response){
		return "index";
	}
	
	@ResponseBody
	@RequestMapping(value = "clearCache.json")
	public String clearCache(HttpServletResponse response){
		try{
			ljmemcachedClient.flushAll();
			return ok().toJson();
		}catch (Exception e) {
		}
		return fail().toJson();
	}
	
}
