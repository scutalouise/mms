package com.agama.das.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.das.model.entity.User;
import com.agama.das.service.SystemConfigService;
import com.agama.das.service.UserService;
import com.agama.tool.utils.IpUtils;
import com.agama.tool.utils.date.DateUtils;
import com.agama.tool.utils.security.Md5Utils;

/**
 * @Description:控制类，提供用户登陆的请求操作
 * @Author:佘朝军
 * @Since :2015年11月25日 上午11:22:36
 */
@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private SystemConfigService systemConfigService;

	@RequestMapping(value = "/login/user", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login(HttpServletRequest httpServletRequest, @RequestParam(value = "userName") String userName,
			@RequestParam(value = "password") String password) {
		Map<String, String> map = new HashMap<String, String>();
		User user = userService.findOneByUserNameAndPassword(userName, Md5Utils.getCustomMD5(password));
		if (user != null) {
			saveLoginUserInfo(httpServletRequest, user);
			map.put("message", "success");
		} else {
			map.put("message", "用户名或密码不正确！");
		}
		return map;
	}
	
	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	public String loginOut(HttpServletRequest httpServletRequest){
		HttpSession session = httpServletRequest.getSession();
		session.removeAttribute("userName");
		return "redirect:login";
	}

	/**
	 * @Description:登陆成功，对用户信息和登陆信息进行保存
	 * @param req
	 * @param userName
	 * @Since :2015年12月4日 下午5:38:56
	 */
	private void saveLoginUserInfo(HttpServletRequest req, User user) {
		HttpSession session = req.getSession();
		session.setAttribute("userName", user.getUserName());
		Integer totalLogin = user.getTotalLogin();
		String date = "";
		String ip = IpUtils.getIpAddr(req);
		if (totalLogin != null && totalLogin > 0) {
			totalLogin++;
			date = DateUtils.formatDateTime(user.getLastLoginTime());
		} else {
			totalLogin = 1;
			date = DateUtils.formatDateTime(new Date());
		}
		user.setLastLoginTime(new Date());
		user.setLastLoginIp(ip);
		user.setTotalLogin(totalLogin);
		userService.update(user);
		session.setAttribute("lastLoginTime", date);
		session.setAttribute("totalLogin", totalLogin);
		session.setAttribute("loginIp", ip);
	}

}
