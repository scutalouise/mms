package com.agama.device.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.device.domain.UserDeviceType;
import com.agama.device.service.IUserDeviceTypeService;


@Controller
@RequestMapping("device/userDeviceType")
public class UserDeviceTypeController {
	
	@Autowired
	private IUserDeviceTypeService userDeviceTypeService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "userDeviceType/userDeviceTypeList";
	}

	/**
	 * 获取自定义设备类型json
	 */
	@RequiresPermissions("device:userDeviceType:view")
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public List<UserDeviceType> userDeviceTypeList(HttpServletRequest request) {
		List<UserDeviceType> UserDeviceTypes = userDeviceTypeService.getAll();
		return UserDeviceTypes;
	}

	/**
	 * 添加自定义设备类型弹窗
	 */
	@RequiresPermissions("device:userDeviceType:add")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("userDeviceType", new UserDeviceType());
		model.addAttribute("action", "add");
		return "userDeviceType/userDeviceTypeForm";
	}

	/**
	 * 添加自定义设备类型
	 */
	@RequiresPermissions("device:userDeviceType:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid UserDeviceType userDeviceType) {
		System.out.println(userDeviceType.getOtherNote());
		userDeviceTypeService.save(userDeviceType);
		return "success";
	}

	/**
	 * 修改自定义设备类型弹窗
	 */
	@RequiresPermissions("device:userDeviceType:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable Integer id, Model model) {
		model.addAttribute("userDeviceType", userDeviceTypeService.get(id));
		model.addAttribute("action", "update");
		return "userDeviceType/userDeviceTypeForm";
	}

	/**
	 * 修改自定义设备类型
	 */
	@RequiresPermissions("device:userDeviceType:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid UserDeviceType userDeviceType) {
		userDeviceTypeService.update(userDeviceType);
		return "success";
	}

	/**
	 * 删除自定义设备类型
	 */
	@RequiresPermissions("device:userDeviceType:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		userDeviceTypeService.delete(id);
		return "success";
	}
}