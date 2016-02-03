package com.agama.pemm.controller;



import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.agama.authority.utils.FormAuthenticationCaptchaFilter;
import com.agama.authority.utils.Global;

@Controller
@RequestMapping(value = "m")
public class MonitorPlatformLoginController {
	@Autowired
	private FormAuthenticationCaptchaFilter formAuthenticationCaptchaFilter;
	@RequestMapping(value="login",method = RequestMethod.GET)
	public String login(ServletRequest request,ServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()||subject.isRemembered()){
			return "redirect:/monitorPlatform/index";
		} 
		formAuthenticationCaptchaFilter.setLoginUrl("/m/login");
		formAuthenticationCaptchaFilter.setSuccessUrl("/monitorPlatform/index");
		return "monitorPlatform/login";
	}
	/**
	 * 登录失败
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping(value="login",method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "monitorPlatform/login";
	}
	
	/**
	 * 登出
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping(value="logout")
	public String logout(Model model) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		formAuthenticationCaptchaFilter.setLoginUrl("/m/login");
		formAuthenticationCaptchaFilter.setSuccessUrl("/monitorPlatform/index");
		return "monitorPlatform/login";
	}
	
}