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
import com.agama.device.domain.CollectionDevice;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.service.ICollectionDeviceService;
import com.agama.device.service.IDevicePurchaseService;
import com.agama.tool.utils.date.DateUtils;

/**
 * @Description:采集设备控制器类
 * @Author:杨远高
 * @Since :2016年1月7日 上午10:43:47
 */
@Controller
@RequestMapping("device/collectionDevice")
public class CollectionDeviceController extends BaseController {
	@Autowired
	private ICollectionDeviceService collectionDeviceService;
	
	@Autowired
	private IDevicePurchaseService devicePurchaseService;
	
	@Autowired
	private IRoleService roleService;

	/**
	 * @Description：采集器默认页面
	 * @return
	 * @Since :2016年1月4日 下午4:18:36
	 */
	@RequestMapping(method = RequestMethod.GET)
	private String view() {
		return "details/collectionDeviceList";
	}

	/**
	 * @Description:采集器设备页面数据封装
	 * @param request
	 * @return
	 * @Since :2016年1月7日 上午10:38:18
	 */
//	@RequiresPermissions("sys:collectionDevice:view")
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<CollectionDevice> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		PropertyFilter propertyFilter = new PropertyFilter("EQE_status", String.valueOf(StatusEnum.NORMAL));//过滤掉，状态为删除状态的记录；
		filters.add(propertyFilter);
		page = collectionDeviceService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * @Description:采集器新增页面
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午5:27:09
	 */
//	@RequiresPermissions("sys:collectionDevice:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String addForm(Model model) {
		model.addAttribute("collectionDevice", new CollectionDevice());
		model.addAttribute("enable", EnabledStateEnum.ENABLED);
		model.addAttribute("action", "create");
		return "details/collectionDeviceForm";
	}
	
	/**
	 * @Description:执行新增的操作
	 * @param collectionDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午5:28:13
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid CollectionDevice collectionDevice, Model model) {
		if (null != collectionDevice) {
			collectionDevice.setStatus(StatusEnum.NORMAL);
			collectionDevice.setUpdateTime(new Date());
			synchronized (collectionDevice) {
				collectionDevice.setIdentifier(FirstDeviceType.COLLECTDEVICE.getValue() + DateUtils.formatDate(new Date(), "yyMMddHHmmSSS"));// 唯一识别码的生成
			}
			collectionDeviceService.save(collectionDevice);
		}
		return "success";
	}
	
	/**
	 * @Description:使用逻辑删除
	 * @param id
	 * @return
	 * @Since :2016年1月19日 下午5:29:02
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		CollectionDevice collectionDevice = collectionDeviceService.get(id);
		collectionDevice.setStatus(StatusEnum.DELETED);
		collectionDevice.setUpdateTime(new Date());
		collectionDeviceService.update(collectionDevice);
		return "success";
	}
	
	/**
	 * @Description:新增采集器设备页面
	 * @param id
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午5:29:48
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		CollectionDevice collectionDevice = collectionDeviceService.get(id);
		DevicePurchase devicePurchase = devicePurchaseService.get(collectionDevice.getPurchaseId());
		Role role = roleService.get(collectionDevice.getRoleId());
		collectionDevice.setPurchaseName(devicePurchase.getName());
		collectionDevice.setRoleName(role.getName());
		model.addAttribute("collectionDevice", collectionDevice);
		model.addAttribute("action", "update");
		return "details/collectionDeviceForm";
	}
	
	/**
	 * @Description:执行修改的具体操作
	 * @param collectionDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午5:30:23
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody CollectionDevice collectionDevice, Model model) {
		collectionDevice.setUpdateTime(new Date());
		collectionDeviceService.update(collectionDevice);
		return "success";
	}
	
	/**
	 * @Description:设置采集器管理人员
	 * @param collectionDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午5:32:22
	 */
	@RequestMapping(value = "setDeviceUser", method = RequestMethod.POST)
	@ResponseBody
	public String setDeviceUser(@Valid @ModelAttribute CollectionDevice collectionDevice, Model model) {
		collectionDeviceService.update(collectionDevice);// 根据提交过来的管理员id进行设置；
		return "success";
	}
	
	/**
	 * @Description:设置采集器运维角色
	 * @param collectionDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午5:33:08
	 */
	@RequestMapping(value = "setDeviceRole", method = RequestMethod.POST)
	@ResponseBody
	public String setDeviceRole(@Valid @ModelAttribute CollectionDevice collectionDevice, Model model) {
		collectionDeviceService.update(collectionDevice);// 根据提交过来的运维角色id进行设置；
		return "success";
	}
	
	/**
	 * @Description:设置采集器所属的自定义分类类别
	 * @param collectionDevice
	 * @param model
	 * @return
	 * @Since :2016年1月19日 下午5:34:01
	 */
	@ResponseBody
	@RequestMapping(value = "setUserDeviceType", method = RequestMethod.POST)
	public String setUserDeviceType(@Valid @ModelAttribute CollectionDevice collectionDevice, Model model) {
		collectionDeviceService.update(collectionDevice);// 根据提交过来的运维角色id进行设置；
		return "success";
	}
	
	/**
	 * @Description:所有处理之前根据id,加载实体到内存；
	 * @param id
	 * @param model
	 * @Since :2016年1月19日 下午5:35:18
	 */
	@ModelAttribute
	public void getCollection(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("collectionDevice", collectionDeviceService.get(id));
		}
	}
}
