package com.agama.device.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.dao.utils.Page;
import com.agama.common.web.BaseController;
import com.agama.device.domain.DeviceInventory;
import com.agama.device.service.IDeviceInventoryService;

@Controller
@RequestMapping("device/deviceInventory")
public class DeviceInventoryController extends BaseController {

	@Autowired
	private IDeviceInventoryService deviceInventoryService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "deviceInventory/deviceInventoryList";
	}

	/**
	 * 获取设备汇总json
	 */
	@RequiresPermissions("device:deviceInventory:view")
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<DeviceInventory> page = getPage(request);
		page = deviceInventoryService.getPageByHql(page);
		return getEasyUIData(page);
	}
}
