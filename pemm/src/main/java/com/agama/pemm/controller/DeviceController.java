package com.agama.pemm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;









import com.agama.authority.service.IOrganizationService;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.web.BaseController;
import com.agama.common.enumbean.AirConditioningRunState;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.pemm.dao.IAcStatusDao;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.protocol.snmp.AcOidInfo;
import com.agama.pemm.protocol.snmp.SwitchInputOidInfo;
import com.agama.pemm.protocol.snmp.ThOidInfo;
import com.agama.pemm.protocol.snmp.UpsOidInfo;
import com.agama.pemm.service.IAcStatusService;
import com.agama.pemm.service.IDeviceOperationService;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IGitInfoService;
import com.agama.pemm.utils.CheckServerConnectUtils;
import com.agama.pemm.utils.DeviceListBean;
import com.agama.pemm.utils.SNMPUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Author:ranjunfeng
 * @Since :2015年9月17日 下午5:12:04
 * @Description:动换设备控制层
 */
@Controller
@RequestMapping("device")
@SessionAttributes("device")
public class DeviceController extends BaseController {
	private Logger log=LoggerFactory.getLogger(DeviceController.class);
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IGitInfoService gitInfoService;
	@Autowired
	private IDeviceOperationService deviceOperationService;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IAcStatusService acStatusService;

	@RequestMapping(value = "deviceView", method = RequestMethod.GET)
	public String deviceView() {
		return "deviceView/index";
	}

	@RequestMapping(value = "deviceHome", method = RequestMethod.GET)
	public String home() {
		return "deviceSystem/deviceHome";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "device/deviceList";
	}

	@RequiresPermissions("sys:device:view")
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(Integer organizationId,String deviceType,
			HttpServletRequest request) {
		Page<Device> page = getPage(request);
		
		String organizationIdStr = organizationService
				.getOrganizationIdStrById(organizationId);
	
		page = deviceService.searchListByOrganizationIdStr(organizationIdStr,deviceType,
				page);
		return getEasyUIData(page);

	}

	@RequiresPermissions("sys:device:add")
	@RequestMapping(value = "scan", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> scan(Integer organizationId) {
		int count = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		//保存扫描到有新增设备的集合
		List<Object> list = new ArrayList<Object>();
		//保存设备的接口
		List<Map<String, Object>> deviceIndexList = new ArrayList<Map<String, Object>>();
		//List<GitInfo> gitInfoList = gitInfoService.getListByStatus(0);
		String organizationIdStr=organizationService.getOrganizationIdStrById(organizationId);
		List<GitInfo> gitInfoList=gitInfoService.getListByOrganizationIdStr(organizationIdStr);
		for (GitInfo gitInfo : gitInfoList) {
			Map<String, Object> deviceMap = new HashMap<String, Object>();
			try {
				SNMPUtil snmp = new SNMPUtil();
				//检查IP是否可达
				boolean isConnect = CheckServerConnectUtils
						.pingByServer(gitInfo.getIp());
				if (isConnect) {
					//获取所有信息放到map中
					Map<String, String> resultMap = snmp.walkByGetNext(
							gitInfo.getIp(), "1.3.6.1.4.1.34651.2");
					//获取到ups1的名字
					String upsName_1 = resultMap.get(UpsOidInfo.upsName
							.getOid() + "." + 1);
					//获取到ups2的名字
					String upsName_2 = resultMap.get(UpsOidInfo.upsName
							.getOid() + "." + 2);
					//获取到温湿度1的名字
					String thName_1 = resultMap.get(ThOidInfo.thName.getOid()
							+ "." + 1);
					//获取到温湿度2的名字
					String thName_2 = resultMap.get(ThOidInfo.thName.getOid()
							+ "." + 2);
					
					for (int i = 1; i <= 4; i++) {
						String thName=resultMap.get(ThOidInfo.thName.getOid()
								+ "." + i);
						if (thName != null) {
							Device device = deviceService
									.getListByGitInfoIdAndDeviceTypeAndIndex(
											gitInfo.getId(), DeviceType.TH, 1);
							if (device == null) {
								count++;
								deviceMap.put("TH-"+i, i);
							}
						}
					}
					if (upsName_1 != null) {
						//是否已经存在对应的设备
						Device device = deviceService
								.getListByGitInfoIdAndDeviceTypeAndIndex(
										gitInfo.getId(), DeviceType.UPS, 1);
						//如果不存在设备数量+1,并放入到deviceMap
						if (device == null) {
							count++;
							deviceMap.put("ups1", 1);
						}
					}
					if (upsName_2 != null) {
						Device device = deviceService
								.getListByGitInfoIdAndDeviceTypeAndIndex(
										gitInfo.getId(), DeviceType.UPS, 2);
						if (device == null) {
							count++;
							deviceMap.put("ups2", 2);
						}
					}
					/*if (thName_1 != null) {
						Device device = deviceService
								.getListByGitInfoIdAndDeviceTypeAndIndex(
										gitInfo.getId(), DeviceType.TH, 1);
						if (device == null) {
							count++;
							deviceMap.put("TH-1", 1);
						}
					}
					if (thName_2 != null) {
						Device device = deviceService
								.getListByGitInfoIdAndDeviceTypeAndIndex(
										gitInfo.getId(), DeviceType.TH, 2);
						if (device == null) {
							count++;
							deviceMap.put("TH-2", 2);
						}
					}*/
					//迭代开关输入量
					for (int i = 1; i <= 8; i++) {
						String switchInputName = resultMap
								.get(SwitchInputOidInfo.NAME.getOid() + "." + i);
						
						if (switchInputName != null) {
							Device device = deviceService
									.getListByGitInfoIdAndDeviceTypeAndIndex(
											gitInfo.getId(),
											DeviceType.SWITCHINPUT, i);
							if (device == null) {
								
								count++;
								deviceMap.put("IN-" + i, i);
							}
						}
					}
					for (int i = 1; i <= 4; i++) {
						String airConditioningName=resultMap.get(AcOidInfo.NAME.getOid()
								+ "." + i);
						if (airConditioningName != null) {
							Device device = deviceService
									.getListByGitInfoIdAndDeviceTypeAndIndex(
											gitInfo.getId(), DeviceType.AC, 1);
							if (device == null) {
								count++;
								deviceMap.put("AC-"+i, i);
							}
						}
					}
					if(deviceMap.size()>0){
						deviceIndexList.add(deviceMap);
					}
					if (deviceMap.size() > 0) {
						list.add(gitInfo.getId());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
 
		}
		map.put("gitInfoIdList", list);
		map.put("deviceIndexList", deviceIndexList);
		map.put("count", count);
		return map;

	}

	@RequiresPermissions("sys:device:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@RequestParam("gitInfoIdList[]") List<Integer> gitInfoIdList,
			DeviceListBean deviceListBean) {
		deviceService.addDeviceForScan(gitInfoIdList,
				deviceListBean.getDeviceIndexList());
		return "success";
	}

	@RequiresPermissions("sys:device:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		Device device = deviceService.get(id);
		model.addAttribute("deviceInterfaceTypes", DeviceInterfaceType
				.getInterfaceTypeByDeviceType(device.getDeviceType()));
		model.addAttribute("device", device);

		model.addAttribute("action", "update");
		return "device/deviceForm";

	}

	@RequiresPermissions("sys:device:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid Device device, Model model) {
		device.setStatus(0);
		deviceService.update(device);
		return "success";

	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("sys:device:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String ids) {
		deviceService.updateStatusByIds(ids);
		
		return "success";

	}

	@RequiresPermissions(value = { "sys:device:upsClose",
			"sys:device:upsDischarge" }, logical = Logical.OR)
	@RequestMapping(value = "upsOperation", method = RequestMethod.GET)
	@ResponseBody
	public String upsOperation(Integer gitInfoId, String oidStr,
			Integer instruction) {

		deviceOperationService.upsOperation(gitInfoId, oidStr, instruction);

		return "success";

	}
	@RequestMapping(value="upsDeviceJson")
	@ResponseBody
	public Map<String, Object> getUpsDeviceJson(String scheduleName,String scheduleGroup,
			Integer type,HttpServletRequest request) {
		Page<Device> page = getPage(request);
	
		

		page = deviceService.getDischargeDevice(page,scheduleName,scheduleGroup,null,type);
		return getEasyUIData(page);

	}
	@RequestMapping(value="upsClose",method=RequestMethod.POST)
	@ResponseBody
	public boolean upsClose(Integer deviceId){
		deviceService.upsClose(deviceId);
		return true;
	}
	
	@RequestMapping(value="closeOrOpenofAc",method=RequestMethod.POST)
	@ResponseBody
	public Integer closeOrOpenofAc(Integer type,Integer deviceIndex,String ip){
		
		acStatusService.closeOrOpenOfAc(ip, deviceIndex, type);
		if(type==1){
			return AirConditioningRunState.RUN.getId();
		}else{
			return AirConditioningRunState.SHUTDOWN.getId();
		}
	}
	
	

}
