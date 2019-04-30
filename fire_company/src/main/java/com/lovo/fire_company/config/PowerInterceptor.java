package com.lovo.fire_company.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//在拦截器里进行验证，如果在session里面能找到，登录信息就放行
@Component
public class PowerInterceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(PowerInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String roleName = (String)request.getSession().getAttribute("roleName");
		String requestURI = request.getRequestURI();
		if("消防值班员".equals(roleName)&&requestURI.contains("even")){
			return true;
		}else if ("消防管理员".equals(roleName)){
			return true;
		}
		logger.info("------:跳转到login页面！");
		response.sendRedirect(request.getContextPath()+"/loginMessage");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		System.out.println("渲染之前");
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		System.out.println("渲染之后");
		
	}

}
