package com.agama.device.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.ObtainWayEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.UsingStateEnum;
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
		String type=request.getParameter("type");
		//非设备领用模块
		if(type==null){
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			filters.add(new PropertyFilter("EQ_StatusEnum_status",StatusEnum.NORMAL.toString()));// 新增过滤掉，状态为删除状态的记录；
			page = networkDeviceService.search(page, filters);
		}else{
			String organizationId=request.getParameter("orgId");
			DeviceUsedStateEnum deviceUsedState=DeviceUsedStateEnum.USED;
			String deviceUsedStateStr=request.getParameter("deviceUsedState");
			ObtainWayEnum obtainWay=ObtainWayEnum.ORGANIZATION;
			String way=request.getParameter("way");
			if(way!=null){
				obtainWay=ObtainWayEnum.valueOf(way.toUpperCase());//领用方式 
			}
			User user=null;
			if(obtainWay==ObtainWayEnum.PERSONAL){
				user=(User)request.getSession().getAttribute("user");
			}
			
			if(deviceUsedStateStr!=null){
				deviceUsedState=DeviceUsedStateEnum.valueOf(deviceUsedStateStr); //入库
			}
			//type为审核中的状态
			if(type.equals("audit")){
				deviceUsedState=DeviceUsedStateEnum.AUDIT;
			}else if(type.equals("waitRepair")){
				deviceUsedState=DeviceUsedStateEnum.WAITREPAIR;
			}else if(type.equals("badParts")){
				deviceUsedState=DeviceUsedStateEnum.BADPARTS;
			}
			page=networkDeviceService.searchObtainNetWorkDeviceList(page,organizationId,StatusEnum.NORMAL,deviceUsedState,user);
		}
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
			networkDevice.setOrganizationId(devicePurchaseService.get(networkDevice.getPurchaseId()).getOrgId());
			networkDevice.setOrganizationName(devicePurchaseService.get(networkDevice.getPurchaseId()).getOrgName());
			networkDevice.setDeviceUsedState(DeviceUsedStateEnum.PUTINSTORAGE);
			networkDevice.setScrappedState(UsingStateEnum.NO);
			networkDevice.setSecondmentState(UsingStateEnum.NO);
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
		networkDevice.setPurchaseName(devicePurchase.getName());
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
	@RequestMapping(value="chooseNetWorkDeviceList",method=RequestMethod.GET)
	public String chooseNetWorkDeviceList(){
		return "obtain/chooseNetWorkDeviceList";
	}
	@RequestMapping("obtainNetWorkDevice/{way}/{type}")
	@ResponseBody
	public String obtainNetWorkDevice(@PathVariable("way") String way,@PathVariable("type") String type,String orgId,@RequestBody List<Integer> netWorkDeviceIdList,HttpSession session){
		ObtainWayEnum obtainWay=ObtainWayEnum.valueOf(way.toUpperCase());
		Integer organizationId=null;
		if(orgId!=null){
			organizationId=Integer.parseInt(orgId);
		}
		User user=null;
		if(obtainWay==ObtainWayEnum.PERSONAL){
			user=(User)session.getAttribute("user");
		}
		networkDeviceService.netWorkDeviceObtain(organizationId,type,netWorkDeviceIdList,user);
		return "success";
	} 
	@RequestMapping("backNetWorkDevice")
	@ResponseBody
	public String backNetWorkDevice(@RequestBody List<Integer> netWorkDeviceIdList){
		networkDeviceService.backNetWorkDevice(netWorkDeviceIdList);
		return "success";
		
	}
	@RequiresPermissions("sys:auditDevice:audit")
	@RequestMapping("netWorkDeviceAudit/{type}")
	@ResponseBody
	public String netWorkDeviceAudit(@PathVariable("type") Integer type,@RequestBody List<Integer> netWorkDeviceIdList){
		networkDeviceService.netWorkDeviceAudit(type,netWorkDeviceIdList);
		return "success";
	}
	@RequiresPermissions("sys:auditDevice:putInstorage")
	@RequestMapping("putInstorage")
	@ResponseBody
	public String putInstorage(@RequestBody List<Integer> netWorkDeviceIdList){
		networkDeviceService.updateDeviceUsedStateByDeviceIdList(netWorkDeviceIdList,DeviceUsedStateEnum.PUTINSTORAGE);
		return "success";
	}
	
	@RequiresPermissions("sys:auditDevice:badparts")
	@RequestMapping("badparts")
	@ResponseBody
	public String badparts(@RequestBody List<Integer> netWorkDeviceIdList){
		networkDeviceService.updateDeviceUsedStateByDeviceIdList(netWorkDeviceIdList,DeviceUsedStateEnum.BADPARTS);
		return "success";
	}
	
	@RequiresPermissions("sys:auditDevice:scrap")
	@RequestMapping("scrap")
	@ResponseBody
	public String scrap(@RequestBody List<Integer> netWorkDeviceIdList){
		networkDeviceService.updateDeviceUsedStateByDeviceIdList(netWorkDeviceIdList,DeviceUsedStateEnum.SCRAP);
		return "success";
	}
	@RequiresPermissions("sys:auditDevice:waitExternalMaintenance")
	@RequestMapping("waitExternalMaintenance")
	@ResponseBody
	public String waitExternalMaintenance(@RequestBody List<Integer> netWorkDeviceIdList){
		networkDeviceService.updateDeviceUsedStateByDeviceIdList(netWorkDeviceIdList,DeviceUsedStateEnum.EXTERNALMAINTENANCE);
		return "success";
	}
	
	@RequestMapping("scrappedNetWorkDevice")
	@ResponseBody
	public String scrappedNetWorkDevice(@RequestBody List<Integer> netWorkDeviceIdList){
		networkDeviceService.scrappedNetWorkDevice(netWorkDeviceIdList); 
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
