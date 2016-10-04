package com.dhu.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dhu.common.util.WebUtil;
import com.dhu.portal.model.UserInfo;
import com.dhu.portal.service.LoginService;

@Component
public class LoginInteceptor extends HandlerInterceptorAdapter {
	
	private Logger log  =LoggerFactory.getLogger(getClass());
	private static final String TOKEN_NAME = Constants.PC_TOKEN_NAME;
	private static final String CACHE_NAME = "agent";
	private static final String DOMAIN = ResourceConfig.getString("domain");
	private static final int EXP_TIME = 60 * 60 *4;//memcache数据存活时间，秒
	@Autowired
	LoginService loginService;
	@Autowired
    MemcachedClient ljmemcachedClient;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String serverName = request.getServerName();
		if(serverName.contains("agent.dhu.com")){
			String ref = request.getParameter("ref");
			if(ref !=null){
				response.sendRedirect(ref);
				return false;
			}
		}
		String dhuToken = WebUtil.getCookieByName(request, TOKEN_NAME);
		//token不存在，去创建
		if(StringUtils.isEmpty(dhuToken)){
			String ucId = (String)request.getAttribute("ucid");
			if(StringUtils.isNotEmpty(ucId)){
				UserInfo agent = loginService.createToken(ucId);
				if(agent != null){
					WebUtil.addCookie(response, TOKEN_NAME, agent.getToken(), DOMAIN, 0);
					ljmemcachedClient.set(agent.getToken() + CACHE_NAME, EXP_TIME, agent);
					return true;
				}
			}
		}else{//token存在，去验证
			UserInfo agent = ljmemcachedClient.get(dhuToken + CACHE_NAME);
			if(agent != null){
				return true;
			}
		}
				
		//UserThreadLocal.setUser(user);
		
		return true;
	}
	
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//UserThreadLocal.remove();
	}
}
