package com.agama.device.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.web.BaseController;
import com.agama.device.dao.IHostDeviceDao;
import com.agama.device.dao.INetworkDeviceDao;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.dao.IUnintelligentDeviceDao;
import com.agama.device.dao.impl.HostDeviceDaoImpl;
import com.agama.device.domain.CollectionDevice;
import com.agama.device.domain.HostDevice;
import com.agama.device.domain.NetworkDevice;
import com.agama.device.domain.PeDevice;
import com.agama.device.domain.UnintelligentDevice;
import com.agama.device.service.ICollectionDeviceService;
import com.agama.device.service.IHostDeviceService;
import com.agama.device.service.INetworkDeviceService;
import com.agama.device.service.IPeDeviceService;
import com.agama.device.service.IUnintelligentDeviceService;

/**
 * @Description:领用设备控制器
 * @Author:ranjunfeng
 * @Since :2016年2月23日 下午1:50:22
 */
@Controller
@RequestMapping("/device/obtain")
public class ObtainDeviceController extends BaseController {
	@Autowired
	private ICollectionDeviceService collectionDeviceService;
	
	@Autowired
	private IHostDeviceService hostDeviceService;
	@Autowired
	private INetworkDeviceService netWorkDeviceService;
	@Autowired
	private IUnintelligentDeviceService unintelligentDeviceService;
	@Autowired
	private IPeDeviceService peDeviceService;
	/**
	 * @Description:默认视图
	 * @return
	 * @Since :2016年2月23日 下午1:50:38
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String view(){
		return "obtain/obtainDeviceList";
	}
	/**
	 * @Description:领用审批视图
	 * @return
	 * @Since :2016年3月1日 上午11:20:10
	 */
	@RequiresPermissions("sys:obtain:auditView")
	@RequestMapping(value="obtainApprove",method=RequestMethod.GET)
	public String obtainApprove(){
		
		return "obtainAudit/obtainApproveList";
		
	}
	@RequiresPermissions("sys:obtain:waitRepairView")
	@RequestMapping(value="waitRepair")
	public String waitRepair(){
		return "obtainAudit/obtainApproveList";
	}
	@RequiresPermissions("sys:obtain:badPartsView")
	@RequestMapping(value="badParts")
	public String badParts(){
		return "obtainAudit/obtainApproveList";
		
	}
	@RequestMapping(value="personalObtain",method=RequestMethod.GET)
	public String personalObtain(){
		return "personalObtain/personalObtainDeviceList";
	}
	
	/**
	 * @Description:主机设备领用列表视图
	 * @param type 领用类型 personal：个人领用 ，organization：网点领用
	 * @param model
	 * @return
	 * @Since :2016年3月2日 上午9:21:22
	 */
	
	@RequestMapping(value="obtainHostDeviceList/{type}",method=RequestMethod.GET)
	public String obtainHostDeviceList(@PathVariable("type") String type,Model model){
		model.addAttribute("type", type);
		return "obtain/obtainHostDeviceList";
	}
	
	
	/**
	 * @Description:网络设备领用列表视图
	 * @return
	 * @Since :2016年2月24日 上午10:02:05
	 */
	@RequestMapping(value="obtainNetWorkDeviceList/{type}",method=RequestMethod.GET)
	public String obtainNetWorkDeviceList(@PathVariable("type") String type,Model model){
		model.addAttribute("type", type);
		return "obtain/obtainNetWorkDeviceList";
	}
	
	
	/**
	 * @Description:非智能设备领用列表视图
	 * @return
	 * @Since :2016年2月24日 上午10:10:21
	 */
	@RequestMapping(value="obtainUnintelligentDeviceList/{type}",method=RequestMethod.GET)
	public String obtainUnintelligentDeviceList(@PathVariable("type") String type,Model model){
		model.addAttribute("type", type);
		return "obtain/obtainUnintelligentDeviceList";
	}
	/**
	 * @Description:采集设备领用列表视图
	 * @return
	 * @Since :2016年2月24日 上午10:40:44
	 */
	@RequestMapping(value="obtainCollectionDeviceList/{type}",method=RequestMethod.GET)
	public String obtainCollectionDeviceList(@PathVariable("type") String type,Model model){
		model.addAttribute("type", type);
		return "obtain/obtainCollectionDeviceList";
	}
	/**
	 * @Description:动环设备领用列表视图
	 * @return
	 * @Since :2016年2月24日 上午10:03:27
	 */
	@RequestMapping(value="obtainPeDeviceList/{type}",method=RequestMethod.GET)
	public String obtainPeDeviceList(@PathVariable("type") String type,Model model){
		model.addAttribute("type", type);
		return "obtain/obtainPeDeviceList";
	}
	/**
	 * @Description:主机设备审核列表视图
	 * @return
	 * @Since :2016年3月1日 下午4:09:54
	 */
	@RequestMapping(value="auditHostDeviceList",method=RequestMethod.GET)
	public String auditHostDeviceList(){

		return "obtainAudit/auditHostDeviceList";
	}
	/**
	 * @Description:网络设备审核列表视图
	 * @return
	 * @Since :2016年3月1日 下午4:10:24
	 */
	@RequestMapping(value="auditNetWorkDeviceList",method=RequestMethod.GET)
	public String auditNetWorkDeviceList(){
		return "obtainAudit/auditNetWorkDeviceList";
	}
	
	@RequestMapping(value="auditCollectionDeviceList",method=RequestMethod.GET)
	public String auditCollectionDeviceList(){
		return "obtainAudit/auditCollectionDeviceList";
	}
	@RequestMapping(value="auditUnintelligentDeviceList",method=RequestMethod.GET)
	public String auditUnintelligentDeviceList(){
		return "obtainAudit/auditUnintelligentDeviceList";
	}
	@RequestMapping(value="auditPeDeviceList",method=RequestMethod.GET)
	public String auditPeDeviceList(){
		return "obtainAudit/auditPeDeviceList";
	}
	@RequestMapping(value="getCollectionListByOrgId/{organizationId}",method=RequestMethod.GET)
	@ResponseBody
	public List<CollectionDevice> getCollectionListByOrgId(@PathVariable(value="organizationId") Integer organizationId){
		return collectionDeviceService.getCollectionListByOrgId(organizationId);
	}
	@RequestMapping(value="getCollectionListByObtainUserId/{obtainUserId}",method=RequestMethod.GET)
	@ResponseBody
	public List<CollectionDevice> getCollectionListByObtainUserId(@PathVariable(value="obtainUserId") Integer obtainUserId){
		return collectionDeviceService.getCollectionListByObtainUserId(obtainUserId);
	}
	@RequestMapping(value="usedOrganizationForm/{deviceType}",method=RequestMethod.GET)
	public String usedOrganizationForm(@PathVariable("deviceType") FirstDeviceType deviceType,Model model){
		model.addAttribute("deviceType", deviceType);
		return "/obtain/usedOrganizationForm";
	}
	@RequestMapping(value="saveOrganizationId",method=RequestMethod.POST)
	@ResponseBody
	public String saveOrganizationId(Integer id,Integer organizationId,FirstDeviceType deviceType){
		if(deviceType==FirstDeviceType.HOSTDEVICE){
			HostDevice hostDevice=hostDeviceService.get(id);
			hostDevice.setOrganizationId(organizationId);
			hostDevice.setDeviceUsedState(DeviceUsedStateEnum.USED);
			hostDeviceService.update(hostDevice);
		}else if(deviceType==FirstDeviceType.NETWORKDEVICE){
			NetworkDevice networkDevice=netWorkDeviceService.get(id);
			networkDevice.setOrganizationId(organizationId);
			networkDevice.setDeviceUsedState(DeviceUsedStateEnum.USED);
			netWorkDeviceService.update(networkDevice);
		}else if(deviceType==FirstDeviceType.COLLECTDEVICE){
			CollectionDevice collectionDevice=collectionDeviceService.get(id);
			collectionDevice.setOrganizationId(organizationId);
			collectionDevice.setDeviceUsedState(DeviceUsedStateEnum.USED);
			collectionDeviceService.update(collectionDevice);
		}else if(deviceType==FirstDeviceType.UNINTELLIGENTDEVICE){
			UnintelligentDevice unintelligentDevice=unintelligentDeviceService.get(id);
			unintelligentDevice.setOrganizationId(organizationId);
			unintelligentDevice.setDeviceUsedState(DeviceUsedStateEnum.USED);
			unintelligentDeviceService.update(unintelligentDevice);
		}else if(deviceType==FirstDeviceType.PEDEVICE){
			PeDevice peDevice=peDeviceService.get(id);
			peDevice.setOrganizationId(organizationId);
			peDevice.setDeviceUsedState(DeviceUsedStateEnum.USED);
			peDeviceService.update(peDevice);
			
		}
		return "success";
	}
}
