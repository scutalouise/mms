package com.agama.pemm.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.agama.authority.utils.FormAuthenticationCaptchaFilter;



@Controller
@RequestMapping(value="d")
public class MonitorLoginController {
	@Autowired
	private FormAuthenticationCaptchaFilter formAuthenticationCaptchaFilter;
	/**
	 * 默认页面
	 * @return
	 */
	@RequestMapping(value="login",method = RequestMethod.GET)
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()||subject.isRemembered()){
			return "redirect:/device/deviceView";
		} 
		formAuthenticationCaptchaFilter.setLoginUrl("/d/login");
		formAuthenticationCaptchaFilter.setSuccessUrl("/device/deviceView");
		return "monitor/login";
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
		return "monitor/login";
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
		formAuthenticationCaptchaFilter.setLoginUrl("/d/login");
		formAuthenticationCaptchaFilter.setSuccessUrl("/device/deviceView");
		return "monitor/login";
	}


}
