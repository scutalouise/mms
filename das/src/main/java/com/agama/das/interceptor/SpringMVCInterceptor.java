package com.agama.das.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description:自定义拦截器，用于拦截无session访问的请求
 * @Author:佘朝军
 * @Since :2015年11月25日 上午10:30:24
 */
@Log4j
public class SpringMVCInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info("请求处理完成！退出spring mvc自定义拦截器。。。");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();
		log.info("开始进入spring mvc自定义拦截器。。。");
		log.info("请求路径：‘" + path + "’");
		if (!path.startsWith(contextPath + "/static") && !path.startsWith(contextPath + "/login")) {
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("userName");
			if (StringUtils.isBlank(userName)) {
				log.info("请求被拦截！系统找不到会话，将返回登陆界面！！！");
				response.getWriter().print("<script type='text/javascript'>window.top.location.href='" + contextPath + "/login'</script>");
				return false;
			}
		}
		return true;
	}

}
