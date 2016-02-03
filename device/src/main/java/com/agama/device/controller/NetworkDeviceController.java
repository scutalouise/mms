package com.agama.device.controller;

import java.util.Date;
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
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.domain.NetworkDevice;
import com.agama.device.service.IDevicePurchaseService;
import com.agama.device.service.INetworkDeviceService;
import com.agama.tool.utils.date.DateUtils;

/**
 * @Description:网络设备控制器类
 * @Author:杨远高
 * @Since :2016年1月7日 上午10:44:24
 */
@Controller
@RequestMapping("device/networkDevice")
public class NetworkDeviceController extends BaseController {
	@Autowired
	private INetworkDeviceService networkDeviceService;
	
	@Autowired
	private IDevicePurchaseService devicePurchaseService;
	
	@Autowired
	private IRoleService roleService;

	/**
	 * @Description:网络设备默认页面
	 * @return
	 * @Since :2016年1月7日 上午10:35:44
	 */
	@RequestMapping(method = RequestMethod.GET)
	private String view() {
		return "details/networkDeviceList";
	}

	/**
	 * @Description:网络设备页面数据组装
	 * @param request
	 * @return
	 * @Since :2016年1月7日 上午10:36:34
	 */
//	@RequiresPermissions("sys:collectionDevice:view")
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<NetworkDevice> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		PropertyFilter propertyFilter = new PropertyFilter("EQE_status", String.valueOf(StatusEnum.NORMAL));//过滤掉，状态为删除状态的记录；
		filters.add(propertyFilter);
		page = networkDeviceService.search(page, filters);
		return getEasyUIData(page);
	}

	
	/**
	 * @Description:跳转到网络设备新增页面
	 * @param model
	 * @return
	 * @Since :2016年1月19日 上午10:09:27
	 */
//	@RequiresPermissions("device:networkDevice:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("networkDevice", new NetworkDevice());
		model.addAttribute("enable", EnabledStateEnum.ENABLED);
		model.addAttribute("action", "create");
		return "details/networkDeviceForm";
	}
	
	/**
	 * @Description:执行网络设备的保存操作
	 * @param networkDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 上午10:14:49
	 */
	// @RequiresPermissions("device:hostDevice:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid NetworkDevice networkDevice, Model model) {
		if (null != networkDevice) {
			networkDevice.setStatus(StatusEnum.NORMAL);
			networkDevice.setUpdateTime(new Date());
			synchronized (networkDevice) {
				networkDevice.setIdentifier(FirstDeviceType.NETWORKDEVICE.getValue() + DateUtils.formatDate(new Date(), "yyMMddHHmmSSS"));// 唯一识别码的生成
			}
			networkDeviceService.save(networkDevice);
		}
		return "success";
	}
	
	
	/**
	 * @Description:使用逻辑删除数据
	 * @param id
	 * @return
	 * @Since :2016年1月19日 上午10:53:17
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		NetworkDevice networkDevice = networkDeviceService.get(id);
		networkDevice.setStatus(StatusEnum.DELETED);
		networkDevice.setUpdateTime(new Date());
		networkDeviceService.update(networkDevice);
		return "success";
	}
	
	
	/**
	 * @Description:跳转到修改网络设备页面；
	 * @param id
	 * @param model
	 * @return
	 * @Since :2016年1月19日 上午10:55:39
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		NetworkDevice networkDevice = networkDeviceService.get(id);
		DevicePurchase devicePurchase = devicePurchaseService.get(networkDevice.getPurchaseId());
		Role role = roleService.get(networkDevice.getRoleId());
		networkDevice.setPurchaseName(devicePurchase.getName());
		networkDevice.setRoleName(role.getName());
		model.addAttribute("networkDevice", networkDevice);
		model.addAttribute("action", "update");
		return "details/networkDeviceForm";
	}
	
	/**
	 * @Description:执行修改操作
	 * @param networkDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 上午10:56:37
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody NetworkDevice networkDevice, Model model) {
		networkDevice.setUpdateTime(new Date());
		networkDeviceService.update(networkDevice);
		return "success";
	}
	
	/**
	 * @Description:设置网络设备管理员
	 * @param networkDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 上午10:57:00
	 */
	@RequestMapping(value = "setDeviceUser", method = RequestMethod.POST)
	@ResponseBody
	public String setDeviceUser(@Valid @ModelAttribute NetworkDevice networkDevice, Model model) {
		networkDeviceService.update(networkDevice);// 根据提交过来的管理员id进行设置；
		return "success";
	}
	
	/**
	 * @Description:设置网络设备运维角色
	 * @param networkDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 上午10:58:31
	 */
	@RequestMapping(value = "setDeviceRole", method = RequestMethod.POST)
	@ResponseBody
	public String setDeviceRole(@Valid @ModelAttribute NetworkDevice networkDevice, Model model) {
		networkDeviceService.update(networkDevice);// 根据提交过来的运维角色id进行设置；
		return "success";
	}
	
	/**
	 * @Description:设置网络设备用户自定义类型
	 * @param networkDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 上午10:59:23
	 */
	@ResponseBody
	@RequestMapping(value = "setUserDeviceType", method = RequestMethod.POST)
	public String setUserDeviceType(@Valid @ModelAttribute NetworkDevice networkDevice, Model model) {
		networkDeviceService.update(networkDevice);// 根据提交过来的运维角色id进行设置；
		return "success";
	}
	
	/**
	 * @Description:设置告警模板
	 * @param networkDevice
	 * @param model
	 * @return
	 * @Since :2016年1月21日 下午5:05:41
	 */
	@ResponseBody
	@RequestMapping(value = "setAlarmTemplate", method = RequestMethod.POST)
	public String setAlarmTemplate(@Valid @ModelAttribute NetworkDevice networkDevice, Model model) {
		networkDeviceService.update(networkDevice);// 根据提交过来的告警模板id进行设置；
		return "success";
	}
	
	/**
	 * @Description:此Controller的ModelAttribute;所有处理之前根据id,加载实体到内存；
	 * @param id
	 * @param model
	 * @Since :2016年1月19日 上午11:01:20
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("networkDevice", networkDeviceService.get(id));
		}
	}
	
}
