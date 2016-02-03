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
import com.agama.device.domain.UnintelligentDevice;
import com.agama.device.service.IDevicePurchaseService;
import com.agama.device.service.IUnintelligentDeviceService;
import com.agama.tool.utils.date.DateUtils;

/**
 * @Description:非智能设备处理
 * @Author:杨远高
 * @Since :2016年1月19日 下午2:27:22
 */
@Controller
@RequestMapping("device/unintelligentDevice")
public class UnintelligentDeviceController extends BaseController {
	@Autowired
	private IUnintelligentDeviceService unintelligentDeviceService;

	@Autowired
	private IDevicePurchaseService devicePurchaseService;
	
	@Autowired
	private IRoleService roleService;

	/**
	 * 非智能设备默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	private String list() {
		return "details/unintelligentDeviceList";
	}

	/**
	 * 获取非智能设备json
	 */
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<UnintelligentDevice> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		PropertyFilter propertyFilter = new PropertyFilter("EQE_status", String.valueOf(StatusEnum.NORMAL));// 过滤掉，状态为删除状态的记录；
		filters.add(propertyFilter);
		page = unintelligentDeviceService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * @Description:跳转到非智能设备新增页面
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午2:30:11
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String addForm(Model model) {
		model.addAttribute("unintelligentDevice", new UnintelligentDevice());
		model.addAttribute("enable", EnabledStateEnum.ENABLED);
		model.addAttribute("action", "create");
		return "details/unintelligentDeviceForm";
	}

	/**
	 * @Description:执行非智能设备的新增操作
	 * @param unintelligentDevice
	 * @return
	 * @Since :2016年1月19日 下午2:31:14
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid UnintelligentDevice unintelligentDevice) {
		synchronized (unintelligentDevice) {
			unintelligentDevice.setIdentifier(FirstDeviceType.UNINTELLIGENTDEVICE.getValue() + DateUtils.formatDate(new Date(), "yyMMddHHmmSSS"));// 唯一识别码的生成
		}
		unintelligentDevice.setStatus(StatusEnum.NORMAL);
		unintelligentDevice.setUpdateTime(new Date());
		unintelligentDeviceService.save(unintelligentDevice);
		return "success";
	}

	/**
	 * @Description:使用逻辑删除
	 * @param id
	 * @return
	 * @Since :2016年1月19日 下午2:35:32
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable int id) {
		UnintelligentDevice unintelligentDevice = unintelligentDeviceService.get(id);
		unintelligentDevice.setStatus(StatusEnum.DELETED);
		unintelligentDevice.setUpdateTime(new Date());
		unintelligentDeviceService.update(unintelligentDevice);
		return "success";
	}
	
	
	/**
	 * @Description:非智能设备更新的页面
	 * @param id
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午2:41:24
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable Integer id, Model model) {
		UnintelligentDevice unintelligentDevice = unintelligentDeviceService.get(id);
		DevicePurchase devicePurchase = devicePurchaseService.get(unintelligentDevice.getPurchaseId());
		Role role = roleService.get(unintelligentDevice.getRoleId());
		unintelligentDevice.setPurchaseName(devicePurchase.getName());
		unintelligentDevice.setRoleName(role.getName());
		model.addAttribute("unintelligentDevice", unintelligentDevice);
		model.addAttribute("action", "update");
		return "details/unintelligentDeviceForm";
	}

	/**
	 * @Description:执行修改非智能设备
	 * @param unintelligentDevice
	 * @return
	 * @Since :2016年1月19日 下午2:44:11
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody UnintelligentDevice unintelligentDevice) {
		unintelligentDevice.setUpdateTime(new Date());
		unintelligentDeviceService.update(unintelligentDevice);
		return "success";
	}


	/**
	 * @Description:修改非智能设备的管理员
	 * @param unintelligentDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午2:48:16
	 */
	@RequestMapping(value = "setDeviceUser", method = RequestMethod.POST)
	@ResponseBody
	public String setDeviceUser(@Valid @ModelAttribute UnintelligentDevice unintelligentDevice, Model model) {
		unintelligentDeviceService.update(unintelligentDevice);// 根据提交过来的管理员id进行设置；
		return "success";
	}

	/**
	 * @Description:修改非智能设备的运维角色
	 * @param unintelligentDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午2:48:50
	 */
	@RequestMapping(value = "setDeviceRole", method = RequestMethod.POST)
	@ResponseBody
	public String setDeviceRole(@Valid @ModelAttribute UnintelligentDevice unintelligentDevice, Model model) {
		unintelligentDeviceService.update(unintelligentDevice);// 根据提交过来的运维角色id进行设置；
		return "success";
	}

	/**
	 * @Description:非智能设备设置用户自定义设备
	 * @param unintelligentDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午2:49:20
	 */
	@ResponseBody
	@RequestMapping(value = "setUserDeviceType", method = RequestMethod.POST)
	public String setUserDeviceType(@Valid @ModelAttribute UnintelligentDevice unintelligentDevice, Model model) {
		unintelligentDeviceService.update(unintelligentDevice);// 根据提交过来的运维角色id进行设置；
		return "success";
	}

	/**
	 * @Description:在处理修改非智能设备之前，加载一次主机信息；
	 * @param id
	 * @param model
	 * @Since :2016年1月19日 下午2:53:05
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("unintelligentDevice",unintelligentDeviceService.get(id));
		}
	}
}
