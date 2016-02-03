package com.agama.device.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.Role;
import com.agama.authority.service.IRoleService;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.OSBitsEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.domain.HostDevice;
import com.agama.device.service.IDevicePurchaseService;
import com.agama.device.service.IHostDeviceService;
import com.agama.tool.utils.date.DateUtils;

/**
 * @Description:主机设备控制器类
 * @Author:杨远高
 * @Since :2016年1月7日 上午10:43:23
 */
@Controller
@RequestMapping("device/hostDevice")
public class HostDeviceController extends BaseController {
	@Autowired
	private IHostDeviceService hostDeviceService;

	@Autowired
	private IDevicePurchaseService devicePurchaseService;
	
	@Autowired
	private IRoleService roleService;

	/**
	 * @Description:主机设备默认页面
	 * @return
	 * @Since :2016年1月4日 下午4:22:37
	 */
	@RequestMapping(method = RequestMethod.GET)
	private String view() {
		return "details/hostDeviceList";
	}

	/**
	 * @Description:主机设备页面数据封装
	 * @param request
	 * @return
	 * @Since :2016年1月7日 上午10:38:32
	 */
	// @RequiresPermissions("device:hostDevice:view")
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<HostDevice> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		// 新增过滤掉，状态为删除状态的记录；
		PropertyFilter propertyFilter = new PropertyFilter("EQE_status", String.valueOf(StatusEnum.NORMAL));
		filters.add(propertyFilter);
		page = hostDeviceService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * @Description:跳转到主机新增的页面；
	 * @param model
	 * @return
	 * @Since :2016年1月8日 下午1:06:15
	 */
	// @RequiresPermissions("device:hostDevice:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("hostDevice", new HostDevice());
		model.addAttribute("oSBits", OSBitsEnum.LOW.getId());
		model.addAttribute("enable", EnabledStateEnum.ENABLED);
		model.addAttribute("action", "create");
		return "details/hostDeviceForm";
	}

	/**
	 * @Description:保存主机设备
	 * @param hostDevice
	 * @param model
	 * @return
	 * @Since :2016年1月8日 下午1:05:56
	 */
	// @RequiresPermissions("device:hostDevice:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid HostDevice hostDevice, Model model) {
		if (null != hostDevice) {
			hostDevice.setStatus(StatusEnum.NORMAL);
			hostDevice.setUpdateTime(new Date());
			synchronized (hostDevice) {
				hostDevice.setIdentifier(FirstDeviceType.HOSTDEVICE.getValue() + DateUtils.formatDate(new Date(), "yyMMddHHmmSSS"));// 唯一识别码的生成
			}
			hostDeviceService.save(hostDevice);
		}
		return "success";
	}

	/**
	 * @Description:使用逻辑删除
	 * @param id
	 * @return
	 * @Since :2016年1月12日 上午11:13:41
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		HostDevice hostDevice = hostDeviceService.get(id);
		hostDevice.setStatus(StatusEnum.DELETED);
		hostDevice.setUpdateTime(new Date());
		hostDeviceService.update(hostDevice);
		return "success";
	}

	/**
	 * @Description:跳转到修改主机页面；
	 * @param id
	 * @param model
	 * @return
	 * @Since :2016年1月8日 下午1:06:30
	 */
	// @RequiresPermissions("device:hostDevice:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		HostDevice hostDevice = hostDeviceService.get(id);
		DevicePurchase devicePurchase = devicePurchaseService.get(hostDevice.getPurchaseId());
		Role role = roleService.get(hostDevice.getRoleId());
		hostDevice.setPurchaseName(devicePurchase.getName());
		hostDevice.setRoleName(role.getName());
		model.addAttribute("hostDevice", hostDevice);
		model.addAttribute("action", "update");
		return "details/hostDeviceForm";
	}

	/**
	 * @Description:修改主机设备
	 * @param hostDevice
	 * @param model
	 * @return
	 * @Since :2016年1月8日 下午1:07:59
	 */
	// @RequiresPermissions("device:hostDevice:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody HostDevice hostDevice, Model model) {
		hostDevice.setUpdateTime(new Date());
		hostDeviceService.update(hostDevice);
		return "success";
	}

	/**
	 * @Description:处理，主机设备设置管理员
	 * @param hostDevice
	 * @param model
	 * @return
	 * @Since :2016年1月12日 下午1:06:18
	 */
	@RequestMapping(value = "setDeviceUser", method = RequestMethod.POST)
	@ResponseBody
	public String setDeviceUser(@Valid @ModelAttribute HostDevice hostDevice, Model model) {
		hostDeviceService.update(hostDevice);// 根据提交过来的管理员id进行设置；
		return "success";
	}

	/**
	 * @Description:处理，主机设备设置运维角色
	 * @param hostDevice
	 * @param model
	 * @return
	 * @Since :2016年1月12日 下午4:21:56
	 */
	@RequestMapping(value = "setDeviceRole", method = RequestMethod.POST)
	@ResponseBody
	public String setDeviceRole(@Valid @ModelAttribute HostDevice hostDevice, Model model) {
		hostDeviceService.update(hostDevice);// 根据提交过来的运维角色id进行设置；
		return "success";
	}

	/**
	 * @Description:处理，主机设备设置用户自定义设备的类型
	 * @param hostDevice
	 * @param model
	 * @return
	 * @Since :2016年1月15日 上午11:23:29
	 */
	@ResponseBody
	@RequestMapping(value = "setUserDeviceType", method = RequestMethod.POST)
	public String setUserDeviceType(@Valid @ModelAttribute HostDevice hostDevice, Model model) {
		hostDeviceService.update(hostDevice);// 根据提交过来的运维角色id进行设置；
		return "success";
	}

	/**
	 * @Description:根据设备Id返回设备的具体的详情；以此选中设备当前选中的设备管理员
	 * @param hostDevice
	 * @param model
	 * @param map
	 * @return
	 * @Since :2016年1月15日 上午11:27:51
	 */
	@ResponseBody
	@RequestMapping(value = "getHostDeviceById", method = RequestMethod.GET)
	public Map<String, Object> getHostDeviceById(@Valid @ModelAttribute HostDevice hostDevice, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("managerId", hostDevice.getManagerId());
		map.put("roleId",hostDevice.getRoleId());
		map.put("userDeviceTypeId", hostDevice.getUserDeviceTypeId());
		return map;
	}
	
	
	/**
	 * @Description:设置告警模板
	 * @param hostDevice
	 * @param model
	 * @return
	 * @Since :2016年1月21日 下午5:05:41
	 */
	@ResponseBody
	@RequestMapping(value = "setAlarmTemplate", method = RequestMethod.POST)
	public String setAlarmTemplate(@Valid @ModelAttribute HostDevice hostDevice, Model model) {
		hostDeviceService.update(hostDevice);// 根据提交过来的告警模板id进行设置；
		return "success";
	}

	/**
	 * @Description:在处理修改主机设备之前，加载一次主机信息；
	 * @param id
	 * @param model
	 * @Since :2016年1月12日 上午9:19:53
	 */
	@ModelAttribute
	public void getHost(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("hostDevice", hostDeviceService.get(id));
		}
	}
}
