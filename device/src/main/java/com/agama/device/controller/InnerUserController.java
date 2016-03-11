package com.agama.device.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.web.BaseController;
import com.agama.device.service.IUserAdminOrgService;

/**
 * @Description:对内部人员运维设置，设置其对应负责的区域；
 * @Author:杨远高
 * @Since :2016年3月2日 上午11:46:06
 */
@Controller("innerUserController")
@RequestMapping("device/innerUser")
public class InnerUserController extends BaseController {

	@Autowired
	private IUserAdminOrgService userAdminOrgService;
	
	/**
	 * @Description:默认跳转到的首页；
	 * @return
	 * @Since :2016年3月2日 上午11:56:38
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String defaultPage(){
		return "maintainUser/innerUserList";
	}

	/**
	 * @Description:根据内部用户id获取用户所负责的机构ids；
	 * @param userId
	 * @return
	 * @Since :2016年3月2日 下午2:25:44
	 */
	@ResponseBody
	@RequestMapping(value = "{userId}/OrgIdList",method = RequestMethod.GET)
	public List<Integer> getOrgIdList(@PathVariable Integer userId){
		return userAdminOrgService.getOrgIdList(userId);
	}
	
	/**
	 * @Description:更新内部用户负责管理的机构；
	 * @param userId
	 * @param newOrgIds
	 * @return
	 * @Since :2016年3月2日 下午3:17:51
	 */
	@RequestMapping(value = "{userId}/updateOrg",method = RequestMethod.POST)
	@ResponseBody
	public String updateOrg(@PathVariable Integer userId,@RequestBody List<Integer> newOrgIds){
		userAdminOrgService.updateUserOrg(userId, userAdminOrgService.getOrgIdList(userId), newOrgIds);
		return "success";
	}
	
}
