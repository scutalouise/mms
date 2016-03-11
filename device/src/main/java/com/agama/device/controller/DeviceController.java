package com.agama.device.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.enumbean.OSEnum;
import com.agama.common.web.BaseController;

/**
 * @Description:设备管理总的页面展现控制；
 * @Author:scuta
 * @Since :2016年1月4日 下午5:20:23
 */
@Controller
@RequestMapping("device/details")
public class DeviceController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "details/deviceList";
	}

	/**
	 * @Description:设备详情中，返回操作系统枚举
	 * @return
	 * @Since :2016年1月8日 上午11:55:24
	 */
	@RequestMapping(value = "osEnum", method = RequestMethod.GET)
	@ResponseBody
	public List<OSEnum> getOSEnum() {
		return OSEnum.getOSEnum();
	}

	/**
	 * @Description:具体设备设置管理员的列表
	 * @return
	 * @Since :2016年1月12日 上午10:00:51
	 */
	@RequestMapping(value = "users")
	public String getDeviceUser() {
		return "details/userList";
	}

	/**
	 * @Description:具体设备设置运维角色的列表
	 * @return
	 * @Since :2016年1月12日 上午10:00:51
	 */
	@RequestMapping(value = "roles")
	public String getDeviceRole() {
		return "details/roleList";
	}
	
	
	/**
	 * @Description:用户自定义类型列表
	 * @return
	 * @Since :2016年1月12日 下午5:25:35
	 */
	@RequestMapping(value = "userDeviceType")
	public String getUserDefineTypes() {
		return "details/userDeviceTypeList";
	}
	
	/**
	 * @Description:告警模板列表选择
	 * @return
	 * @Since :2016年1月21日 下午5:04:24
	 */
	@RequestMapping(value = "alarmTemplate")
	public String getAlarmTemplate() {
		return "details/alarmTemplateList";
	}
	
	
}
