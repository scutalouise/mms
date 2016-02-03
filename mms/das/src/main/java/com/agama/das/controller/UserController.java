package com.agama.das.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.das.model.entity.User;
import com.agama.das.service.UserService;
import com.agama.tool.utils.security.Md5Utils;

/**
 * @Description:控制类，用于处理用户管理操作请求
 * @Author:佘朝军
 * @Since :2015年11月13日 上午11:19:49
 */
@Controller
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/oldPassword/{oldPassword}/newPassword/{newPassword}", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, String> resetPassword(HttpServletRequest httpServletRequest, @PathVariable(value = "oldPassword") String oldPassword,
			@PathVariable(value = "newPassword") String newPassword) {
		Map<String, String> map = new HashMap<String, String>();
		if (!newPassword.equals(oldPassword)) {
			HttpSession session = httpServletRequest.getSession();
			String userName = (String) session.getAttribute("userName");
			if (!StringUtils.isBlank(userName)) {
				User user = userService.findOneByUserName(userName);
				if (user != null) {
					if (Md5Utils.getCustomMD5(oldPassword).equals(user.getPassword())) {
						user.setPassword(Md5Utils.getCustomMD5(newPassword));
						userService.update(user);
						map.put("message", "success");
					} else {
						map.put("message", "error password");
					}
				} else {
					map.put("message", "未找到相应的用户！");
				}
			} else {
				map.put("message", "no session");
			}
		} else {
			map.put("message", "新密码不能与原始密码一致！");
		}
		return map;
	}

	@RequestMapping(value = "/loginInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getLoginUserInfo(HttpServletRequest httpServletRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = httpServletRequest.getSession();
		map.put("lastLoginTime", session.getAttribute("lastLoginTime"));
		map.put("totalLogin", session.getAttribute("totalLogin"));
		map.put("loginIp", session.getAttribute("loginIp"));
		map.put("userName", session.getAttribute("userName"));
		return map;
	}

}
