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
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.domain.PeDevice;
import com.agama.device.service.IDevicePurchaseService;
import com.agama.device.service.IPeDeviceService;
import com.agama.tool.utils.date.DateUtils;

/**
 * @Description:动环设备控制器类
 * @Author:杨远高
 * @Since :2016年1月7日 上午10:43:58
 */
@Controller
@RequestMapping("device/peDevice")
public class PeDeviceController extends BaseController {
	@Autowired
	private IPeDeviceService peDeviceService;
	
	@Autowired
	private IDevicePurchaseService devicePurchaseService;
	
	@Autowired
	private IRoleService roleService;

	/**
	 * @Description:动环设备页面
	 * @return
	 * @Since :2016年1月7日 上午10:30:21
	 */
	@RequestMapping(method = RequestMethod.GET)
	private String view() {
		return "details/peDeviceList";
	}

	/**
	 * @Description:动环设备页面数组封装
	 * @param request
	 * @return
	 * @Since :2016年1月7日 上午10:37:58
	 */
//	@RequiresPermissions("device:peDevice:view")
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<PeDevice> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		// 新增过滤掉，状态为删除状态的记录；
		PropertyFilter propertyFilter = new PropertyFilter("EQE_status", String.valueOf(StatusEnum.NORMAL));
		filters.add(propertyFilter);
		page = peDeviceService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @Description:获取动环类型
	 * @return
	 * @Since :2016年1月21日 下午2:44:33
	 */
	@RequestMapping(value = "dhDeviceType")
	@ResponseBody
	public List<DeviceType>  getDeviceType(){
		return DeviceType.geDeviceType();
	}
	
	/**
	 * @Description:根据动环类型返回对应的那些接口
	 * @param deviceType
	 * @return
	 * @Since :2016年1月21日 下午2:45:36
	 */
	@RequestMapping(value = "deviceInterfaceType")
	@ResponseBody
	public List<DeviceInterfaceType> getDeviceInterfaceType(@RequestParam DeviceType deviceType){
		return DeviceInterfaceType.getInterfaceTypeByDeviceType(deviceType);
	}
	

	/**
	 * @Description:动环设备新增的页面
	 * @param model
	 * @return
	 * @Since :2016年1月21日 下午2:21:14
	 */
//	@RequiresPermissions("device:peDevice:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("peDevice", new PeDevice());
		model.addAttribute("enable", EnabledStateEnum.ENABLED);
		model.addAttribute("action", "create");
		return "details/peDeviceForm";
	}
	
	/**
	 * @Description:执行新增动环设备的操作
	 * @param peDevice
	 * @param model
	 * @return
	 * @Since :2016年1月21日 下午2:22:25
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PeDevice peDevice, Model model) {
		if (null != peDevice) {
			peDevice.setStatus(StatusEnum.NORMAL);
			peDevice.setUpdateTime(new Date());
			synchronized (peDevice) {
				peDevice.setIdentifier(FirstDeviceType.PEDEVICE.getValue() + DateUtils.formatDate(new Date(), "yyMMddHHmmSSS"));// 唯一识别码的生成
			}
			peDeviceService.save(peDevice);
		}
		return "success";
	}
	
	/**
	 * @Description:逻辑删除动环设备
	 * @param id
	 * @return
	 * @Since :2016年1月21日 下午2:28:26
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		PeDevice peDevice = peDeviceService.get(id);
		peDevice.setStatus(StatusEnum.DELETED);
		peDevice.setUpdateTime(new Date());
		peDeviceService.update(peDevice);
		return "success";
	}
	
	/**
	 * @Description:动环更新的页面
	 * @param id
	 * @param model
	 * @return
	 * @Since :2016年1月21日 下午2:32:04
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		PeDevice peDevice = peDeviceService.get(id);
		DevicePurchase devicePurchase = devicePurchaseService.get(peDevice.getPurchaseId());
		Role role = roleService.get(peDevice.getRoleId());
		peDevice.setRoleName(role.getName());
		peDevice.setPurchaseName(devicePurchase.getName());
		model.addAttribute("peDevice", peDevice);
		model.addAttribute("action", "update");
		return "details/peDeviceForm";
	}
	
	/**
	 * @Description:执行更新的具体操作
	 * @param peDevice
	 * @param model
	 * @return
	 * @Since :2016年1月21日 下午2:32:53
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PeDevice peDevice, Model model) {
		peDevice.setUpdateTime(new Date());
		peDeviceService.update(peDevice);
		return "success";
	}
	
	/**
	 * @Description:执行动环设备管理员设置
	 * @param peDevice
	 * @param model
	 * @return
	 * @Since :2016年1月21日 下午2:33:30
	 */
	@RequestMapping(value = "setDeviceUser", method = RequestMethod.POST)
	@ResponseBody
	public String setDeviceUser(@Valid @ModelAttribute PeDevice peDevice, Model model) {
		peDeviceService.update(peDevice);// 根据提交过来的管理员id进行设置；
		return "success";
	}
	
	/**
	 * @Description:设置动环设备，运维角色；
	 * @param peDevice
	 * @param model
	 * @return
	 * @Since :2016年1月21日 下午2:34:10
	 */
	@RequestMapping(value = "setDeviceRole", method = RequestMethod.POST)
	@ResponseBody
	public String setDeviceRole(@Valid @ModelAttribute PeDevice peDevice, Model model) {
		peDeviceService.update(peDevice);// 根据提交过来的运维角色id进行设置；
		return "success";
	}
	
	/**
	 * @Description:设置动环设备，用户自定义类型；
	 * @param peDevice
	 * @param model
	 * @return
	 * @Since :2016年1月21日 下午2:34:52
	 */
	@ResponseBody
	@RequestMapping(value = "setUserDeviceType", method = RequestMethod.POST)
	public String setUserDeviceType(@Valid @ModelAttribute PeDevice peDevice, Model model) {
		peDeviceService.update(peDevice);// 根据提交过来的运维角色id进行设置；
		return "success";
	}
	
	/**
	 * @Description:设置告警模板
	 * @param peDevice
	 * @param model
	 * @return
	 * @Since :2016年1月21日 下午5:05:41
	 */
	@ResponseBody
	@RequestMapping(value = "setAlarmTemplate", method = RequestMethod.POST)
	public String setAlarmTemplate(@Valid @ModelAttribute PeDevice peDevice, Model model) {
		peDeviceService.update(peDevice);// 根据提交过来的告警模板id进行设置；
		return "success";
	}
	
	/**
	 * @Description:在处理动环设备前，将数据库对象加载到内存，以备利用；
	 * @param id
	 * @param model
	 * @Since :2016年1月21日 下午2:35:18
	 */
	@ModelAttribute
	public void getPe(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("peDevice",peDeviceService.get(id));
		}
	}

}
