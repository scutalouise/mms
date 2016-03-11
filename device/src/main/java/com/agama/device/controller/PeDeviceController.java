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
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.ObtainWayEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.UsingStateEnum;
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
		String type=request.getParameter("type");
		if(type==null){
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			filters.add(new PropertyFilter("EQ_StatusEnum_status", StatusEnum.NORMAL.toString()));// 新增过滤掉，状态为删除状态的记录；
			page = peDeviceService.search(page, filters);
		}else{
			String organizationId=request.getParameter("orgId");
			DeviceUsedStateEnum deviceUsedState=DeviceUsedStateEnum.USED;
			String deviceUsedStateStr=request.getParameter("deviceUsedState");
			ObtainWayEnum obtainWay=ObtainWayEnum.ORGANIZATION;
			String way=request.getParameter("way");
			if(way!=null){
				obtainWay=ObtainWayEnum.valueOf(way.toUpperCase());//领用方式 
			}
			if(deviceUsedStateStr!=null){
				deviceUsedState=DeviceUsedStateEnum.valueOf(deviceUsedStateStr); //入库
			}
			User user=null;
			if(obtainWay==ObtainWayEnum.PERSONAL){
				user=(User)request.getSession().getAttribute("user");
			}
			//type为审核中的状态
			if(type.equals("audit")){
				deviceUsedState=DeviceUsedStateEnum.AUDIT;
			}else if(type.equals("waitRepair")){
				deviceUsedState=DeviceUsedStateEnum.WAITREPAIR;
			}else if(type.equals("badParts")){
				deviceUsedState=DeviceUsedStateEnum.BADPARTS;
			}
			page=peDeviceService.searchObtainPeDeviceList(page,organizationId,StatusEnum.NORMAL,deviceUsedState,user);
		}
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
			peDevice.setOrganizationId(devicePurchaseService.get(peDevice.getPurchaseId()).getOrgId());
			peDevice.setOrganizationName(devicePurchaseService.get(peDevice.getPurchaseId()).getOrgName());
			peDevice.setDeviceUsedState(DeviceUsedStateEnum.PUTINSTORAGE);
			peDevice.setScrappedState(UsingStateEnum.NO);
			peDevice.setSecondmentState(UsingStateEnum.NO);
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
		peDevice.setPurchaseName(devicePurchase.getName());
		model.addAttribute("peDevice", peDevice);
		model.addAttribute("action", "update");
		return "details/peDeviceForm";
	}
	@RequestMapping(value = "relevanceCollection/{type}/{id}", method = RequestMethod.GET)
	public String relevanceCollectionForm(@PathVariable("type") String type,@PathVariable("id") Integer id, Model model){
		PeDevice peDevice = peDeviceService.get(id);
		model.addAttribute("type",type);
		model.addAttribute("peDevice", peDevice);
		model.addAttribute("action", "update");
		return "obtain/relevanceCollectionForm";
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
	@RequestMapping(value = "saveRelevanceCollection", method = RequestMethod.POST)
	@ResponseBody
	public String relevanceCollectionSave(@Valid PeDevice peDevice, Model model) {
		PeDevice pd=peDeviceService.get(peDevice.getId());
		pd.setCollectDeviceId(peDevice.getCollectDeviceId());
		pd.setDhDeviceType(peDevice.getDhDeviceType());
		pd.setDhDeviceInterfaceType(peDevice.getDhDeviceInterfaceType());
		pd.setDhDeviceIndex(peDevice.getDhDeviceIndex());
		peDeviceService.update(pd);
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
	@RequestMapping(value="choosePeDeviceList",method=RequestMethod.GET)
	public String choosePeDeviceList(){
		return "obtain/choosePeDeviceList";
	}
	@RequestMapping("obtainPeDevice/{way}/{type}")
	@ResponseBody
	public String obtainPeDevice(@PathVariable("way") String way,@PathVariable("type") String type,String orgId,@RequestBody List<Integer> peDeviceIdList,HttpSession session){
		ObtainWayEnum obtainWay=ObtainWayEnum.valueOf(way.toUpperCase());
		Integer organizationId=null;
		if(orgId!=null){
			organizationId=Integer.parseInt(orgId);
		}
		User user=null;
		if(obtainWay==ObtainWayEnum.PERSONAL){
			user=(User)session.getAttribute("user");
		}
		peDeviceService.peDeviceObtain(organizationId,type,peDeviceIdList,user);
		return "success";
	} 
	@RequestMapping("backPeDevice")
	@ResponseBody
	public String backHostDevice(@RequestBody List<Integer> peDeviceIdList){
		peDeviceService.backPeDevice(peDeviceIdList);
		return "success";
		
	}
	@RequestMapping("peDeviceAudit/{type}")
	@ResponseBody
	public String peDeviceAudit(@PathVariable("type") Integer type,@RequestBody List<Integer> peDeviceIdList){
		peDeviceService.peDeviceAudit(type,peDeviceIdList);
		return "success";
	}
	
	
	@RequiresPermissions("sys:auditDevice:badparts")
	@RequestMapping("badparts")
	@ResponseBody
	public String badparts(@RequestBody List<Integer> peDeviceIdList){
		peDeviceService.updateDeviceUsedStateByDeviceIdList(peDeviceIdList,DeviceUsedStateEnum.BADPARTS);
		return "success";
	}
	@RequiresPermissions("sys:auditDevice:putInstorage")
	@RequestMapping("putInstorage")
	@ResponseBody
	public String putInstorage(@RequestBody List<Integer> peDeviceIdList){
		peDeviceService.updateDeviceUsedStateByDeviceIdList(peDeviceIdList,DeviceUsedStateEnum.PUTINSTORAGE);
		return "success";
	}
	
	@RequiresPermissions("sys:auditDevice:scrap")
	@RequestMapping("scrap")
	@ResponseBody
	public String scrap(@RequestBody List<Integer> peDeviceIdList){
		peDeviceService.updateDeviceUsedStateByDeviceIdList(peDeviceIdList,DeviceUsedStateEnum.SCRAP);
		return "success";
	}
	
	@RequiresPermissions("sys:auditDevice:waitExternalMaintenance")
	@RequestMapping("waitExternalMaintenance")
	@ResponseBody
	public String waitExternalMaintenance(@RequestBody List<Integer> peDeviceIdList){
		peDeviceService.updateDeviceUsedStateByDeviceIdList(peDeviceIdList,DeviceUsedStateEnum.WAITEXTERNALMAINTENANCE);
		return "success";
	}
	
	@RequestMapping("scrappedPeDevice")
	@ResponseBody
	public String scrappedPeDevice(@RequestBody List<Integer> peDeviceIdList){
		peDeviceService.scrappedPeDevice(peDeviceIdList);
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
