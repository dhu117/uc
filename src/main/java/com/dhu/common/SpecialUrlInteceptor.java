package com.dhu.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dhu.common.CityThreadLocal;
import com.dhu.common.util.CommonFunctions;

/**
 * 苏州地铁房隐藏拦截器
 * 
 * @author lizehua
 */
public class SpecialUrlInteceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if ("su".equalsIgnoreCase(CityThreadLocal.get())) {
			String webUrl = CommonFunctions.getConfigByCity("web.host", CityThreadLocal.get());
			response.sendRedirect(webUrl);
			return false;
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
