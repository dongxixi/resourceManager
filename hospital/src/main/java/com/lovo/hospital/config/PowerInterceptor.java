package com.lovo.hospital.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lovo.hospital.entity.UserEntity;
import com.lovo.hospital.entity.UserRoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Component
public class PowerInterceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(PowerInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		System.out.println("调用之前");
		UserEntity user = (UserEntity)request.getSession().getAttribute("user");
		if("/loginyanzheng".equals(request.getRequestURI())||user!=null){
			return true;
		}
		logger.info("------:跳转到login页面！");
		response.sendRedirect(request.getContextPath()+"/loginyanzheng");
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
