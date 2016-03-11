package com.agama.device.controller;

import java.util.List;
import java.util.Map;

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

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.MaintainWayEnum;
import com.agama.common.web.BaseController;
import com.agama.device.domain.DeviceInventory;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.service.IDeviceInventoryService;
import com.agama.device.service.IDevicePurchaseService;

@Controller
@RequestMapping("device/devicePurchase")
public class DevicePurchaseController extends BaseController {

	@Autowired
	private IDevicePurchaseService devicePurchaseService;
	@Autowired
	private IDeviceInventoryService deviceInventoryService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "devicePurchase/devicePurchaseList";
	}

	/**
	 * 获取设备采购记录json
	 * 
	 */
	@RequiresPermissions("device:devicePurchase:view")
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<DevicePurchase> page = getPage(request);
		page = devicePurchaseService.findPageByHQL(page, request);
		return getEasyUIData(page);
	}

	/**
	 * 添加采购记录弹窗
	 */
	@RequiresPermissions("device:devicePurchase:add")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("devicePurchase", new DevicePurchase());
		model.addAttribute("action", "add");
		return "devicePurchase/devicePurchaseForm";
	}

	
//	@RequiresPermissions("device:devicePurchase:details")
	@RequestMapping(value = "details/{id}", method = RequestMethod.GET)
	public String details(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("devicePurchase", devicePurchaseService.getPurchaseByid(id));
		return "devicePurchase/devicePurchaseDetails";
	}
	
	/**
	 * 添加采购记录
	 */
	@RequiresPermissions("device:devicePurchase:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid DevicePurchase devicePurchase, @Valid DeviceInventory deviceInventory) {
        return devicePurchaseService.savePurchaseAndInventory(devicePurchase, deviceInventory);
	}

	/**
	 * 修改采购记录弹窗
	 */
	@RequiresPermissions("device:devicePurchase:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("devicePurchase", devicePurchaseService.get(id));
		model.addAttribute("deviceInventory",
				deviceInventoryService.get(devicePurchaseService.get(id).getDeviceInventoryId()));
		model.addAttribute("action", "update");
		return "devicePurchase/devicePurchaseForm";
	}

	/**
	 * 修改采购记录
	 */
	@RequiresPermissions("device:devicePurchase:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid DevicePurchase devicePurchase, @Valid DeviceInventory deviceInventory) {
		return devicePurchaseService.updatePurchaseAndInventory(devicePurchase, deviceInventory);
	}

	/**
	 * @Description:选择设备的购买信息的时候，弹出窗口的跳转
	 * @return
	 * @Since :2016年1月8日 上午10:15:53
	 */
	@RequestMapping(value = "purchaseList", method = RequestMethod.GET)
	public String purchaseList() {
		return "details/devicePurchaseList";
	}

	/**
	 * @Description:设备详情里，弹出采购记录窗口，供选择：指定设备属于哪一个采购批次
	 * @param request
	 * @return
	 * @Since :2016年1月8日 上午10:12:46
	 */
	@RequestMapping(value = "purchase", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDevciePurchase(HttpServletRequest request) {
		Page<DevicePurchase> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = devicePurchaseService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取运维方式集合
	 */
	@RequestMapping(value = "maintainWay", method = RequestMethod.GET)
	@ResponseBody
	public List<MaintainWayEnum> getMaintainWay() {
		return MaintainWayEnum.getMaintainWay();
	}
	
	/**
	 * 通过采购ID获取采购记录
	 */
	@RequestMapping(value = "purchaseDetail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<DevicePurchase> getPurchaseDetail(@PathVariable Integer id) {
		return devicePurchaseService.getByPurchaseId(id);
	}
	
	@RequestMapping(value = "obtainCountCompare/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> isGreaterThanPurchaseQuantity(@PathVariable("id") Integer purchaseId ){
		return  devicePurchaseService.obtainCountCompare(purchaseId);
	}
}
