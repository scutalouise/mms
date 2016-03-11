package com.agama.itam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.User;
import com.agama.authority.utils.ServletUtils;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.web.BaseController;
import com.agama.device.service.IDeviceService;

/**
 * @Description:
 * @Author:佘朝军
 * @Since :2016年3月10日 下午3:22:04
 */
@Controller
@RequestMapping("maintenance/badDevice")
public class BadDeviceController extends BaseController {

	@Autowired
	private IDeviceService deviceService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String toBadDevicePage() {
		return "maintenance/deviceBadparts";
	}
	
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> badDeviceList(HttpServletRequest request) {
		Page<Object> page = getPage(request);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> queryMap = ServletUtils.getParametersStartingWith(request, "filter_");
			User user = getSessionUser();
			queryMap.put("obtainUserId", user.getId());
			page = deviceService.getPageListByQueryMap(page, queryMap);
			map = getEasyUIData(page);
		} catch (Exception e) {
			map.put("total", 0);
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestBody List<String> ids) {
		deviceService.updateDeviceUsedStateByIdentifierList(ids, DeviceUsedStateEnum.BADPARTS);
		return "success";
	}
	
	private User getSessionUser() {
		Session session = SecurityUtils.getSubject().getSession();
		return (User) session.getAttribute("user");
	}

}
