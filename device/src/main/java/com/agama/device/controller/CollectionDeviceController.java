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
import com.agama.device.domain.CollectionDevice;
import com.agama.device.domain.DeviceInventory;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.service.ICollectionDeviceService;
import com.agama.device.service.IDeviceInventoryService;
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
	private IDeviceInventoryService deviceInventoryService;
	
	
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
		String type=request.getParameter("type");
		if(type==null){
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			filters.add(new PropertyFilter("EQ_StatusEnum_status", StatusEnum.NORMAL.toString()));// 新增过滤掉，状态为删除状态的记录；
			page = collectionDeviceService.search(page, filters);
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
			page=collectionDeviceService.searchObtainCollectionDeviceList(page,organizationId,StatusEnum.NORMAL,deviceUsedState,user);

		}
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
			DevicePurchase devicePurchase= devicePurchaseService.get(collectionDevice.getPurchaseId());
			DeviceInventory deviceInventory=deviceInventoryService.get(devicePurchase.getDeviceInventoryId());
			collectionDevice.setDeviceType(devicePurchase.getSecondDeviceType());
			collectionDevice.setOrganizationId(devicePurchase.getOrgId());
			collectionDevice.setOrganizationName(devicePurchase.getOrgName());
			collectionDevice.setDeviceType(deviceInventory.getSecondDeviceType());
			collectionDevice.setDeviceUsedState(DeviceUsedStateEnum.PUTINSTORAGE);
			collectionDevice.setScrappedState(UsingStateEnum.NO);
			collectionDevice.setSecondmentState(UsingStateEnum.NO);
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
	@RequestMapping(value = "update/{id}",method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		CollectionDevice collectionDevice = collectionDeviceService.get(id);
		DevicePurchase devicePurchase = devicePurchaseService.get(collectionDevice.getPurchaseId());
		collectionDevice.setPurchaseName(devicePurchase.getName());
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
	
	@RequestMapping(value="chooseCollectionDeviceList",method=RequestMethod.GET)
	public String chooseCollectionDeviceList(){
		return "obtain/chooseCollectionDeviceList";
	}
	@RequestMapping("obtainCollectionDevice/{way}/{type}")
	@ResponseBody
	public String obtainCollectionDevice(@PathVariable("way") String way,@PathVariable("type") String type,String orgId,@RequestBody List<Integer> collectionDeviceIdList,HttpSession session){
		ObtainWayEnum obtainWay=ObtainWayEnum.valueOf(way.toUpperCase());
		Integer organizationId=null;
		if(orgId!=null){
			organizationId=Integer.parseInt(orgId);
		}
		User user=null;
		if(obtainWay==ObtainWayEnum.PERSONAL){
			user=(User)session.getAttribute("user");
		}
		collectionDeviceService.collectionDeviceObtain(organizationId,type,collectionDeviceIdList,user);
		return "success";
	} 
	@RequestMapping("backCollectionDevice")
	@ResponseBody
	public String backCollectionDevice(@RequestBody List<Integer> collectionDeviceIdList){
		collectionDeviceService.backCollectionDevice(collectionDeviceIdList);
		return "success";
		
	}
	@RequiresPermissions("sys:auditDevice:audit")
	@RequestMapping("collectionDeviceAudit/{type}")
	@ResponseBody
	public String collectionDeviceAudit(@PathVariable("type") Integer type,@RequestBody List<Integer> collectionDeviceIdList){
		collectionDeviceService.collectionDeviceAudit(type,collectionDeviceIdList);
		return "success";
	}
	
	@RequiresPermissions("sys:auditDevice:badparts")
	@RequestMapping("badparts")
	@ResponseBody
	public String badparts(@RequestBody List<Integer> collectionDeviceIdList){
		collectionDeviceService.updateDeviceUsedStateByDeviceIdList(collectionDeviceIdList,DeviceUsedStateEnum.BADPARTS);
		return "success";
	}
	@RequiresPermissions("sys:auditDevice:putInstorage")
	@RequestMapping("putInstorage")
	@ResponseBody
	public String putInstorage(@RequestBody List<Integer> collectionDeviceIdList){
		collectionDeviceService.updateDeviceUsedStateByDeviceIdList(collectionDeviceIdList,DeviceUsedStateEnum.PUTINSTORAGE);
		return "success";
	}
	@RequiresPermissions("sys:auditDevice:scrap")
	@RequestMapping("scrap")
	@ResponseBody
	public String scrap(@RequestBody List<Integer> collectionDeviceIdList){
		collectionDeviceService.updateDeviceUsedStateByDeviceIdList(collectionDeviceIdList,DeviceUsedStateEnum.SCRAP);
		return "success";
	}
	
	@RequiresPermissions("sys:auditDevice:waitExternalMaintenance")
	@RequestMapping("waitExternalMaintenance")
	@ResponseBody
	public String waitExternalMaintenance(@RequestBody List<Integer> collectionDeviceIdList){
		collectionDeviceService.updateDeviceUsedStateByDeviceIdList(collectionDeviceIdList,DeviceUsedStateEnum.WAITEXTERNALMAINTENANCE);
		return "success";
	}
	
	@RequestMapping("scrappedCollectionDevice")
	@ResponseBody
	public String scrappedCollectionDevice(@RequestBody List<Integer> collectionDeviceIdList){
		collectionDeviceService.scrappedCollectionDevice(collectionDeviceIdList);
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
