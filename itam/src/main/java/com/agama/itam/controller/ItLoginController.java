package com.agama.itam.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agama.authority.utils.FormAuthenticationCaptchaFilter;
import com.agama.authority.utils.Global;



@Controller
@RequestMapping(value="it")
public class ItLoginController {
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
			
			return "redirect:"+Global.getAdminPath();
		} 
		
		return "it/login";
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
		return "it/login";
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
		return "it/login";
	}


}
