package com.agama.pemm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.service.IAreaInfoService;
import com.agama.authority.service.IOrganizationService;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.web.BaseController;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.domain.SwitchInputStatus;
import com.agama.pemm.domain.ThStatus;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IGitInfoService;
import com.agama.pemm.service.ISwitchInputStatusService;
import com.agama.pemm.service.IThStatusService;
import com.agama.pemm.service.IUpsStatusService;

@Controller
@RequestMapping("upsStatus")
public class UpsStatusController extends BaseController {
	@Autowired
	private IUpsStatusService upsStatusService;
	@Autowired
	private IThStatusService thStatusService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IAreaInfoService areaInfoService;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IGitInfoService gitInfoService;
	@Autowired
	private ISwitchInputStatusService switchInputStatusService;
	

	@RequestMapping(method = RequestMethod.GET)
	public String main() {
		return "upsStatus/upsStatus";
	}
	
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(){
		
		return "upsStatus/upsStatusList";
		
	}
	@RequiresPermissions("sys:upsStatus:view")
	@RequestMapping(value="upsStatusList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUpsStatus(HttpServletRequest request) {
		
		Page<UpsStatus> page = getPage(request);
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		Integer gitInfoId=Integer.parseInt(request.getParameter("gitInfoId"));
		Integer index=Integer.parseInt(request.getParameter("index"));
		page = upsStatusService.searchByGitInfoIdAndDeviceTypeAndIndex(page,gitInfoId,DeviceType.UPS,index,startDate,endDate);
		return getEasyUIData(page);

	}
	@RequiresPermissions("sys:upsStatus:gitInfoView")
	@RequestMapping(value="gitInfoView")
	public String gitInfoView(Integer organizationId,Model model){
		String organizationIdStr=organizationService.getOrganizationIdStrById(organizationId);
		List<GitInfo> gitInfoList=gitInfoService.getListByOrganizationIdStr(organizationIdStr);
		List<DeviceStateRecord> deviceStateRecordList=deviceService.getDeviceStateRecordByOrganizationId(organizationIdStr);
		model.addAttribute("gitInfoList", gitInfoList);
		model.addAttribute("deviceStateRecordList", deviceStateRecordList);
		return "upsStatus/gitInfoView";
	}
	@RequiresPermissions("sys:upsStatus:upsStatusView")
	@RequestMapping(value="upsStatusView")
	public String upsStatusView(@RequestParam(required=false) Integer gitInfoId,Model model){
		GitInfo gitInfo=gitInfoService.get(gitInfoId);
		List<UpsStatus> upsStatusList=upsStatusService.findLatestDataByGitInfoId(gitInfoId);
		List<ThStatus> thStatusList=thStatusService.findLatestDataByGitInfoId(gitInfoId);
		List<SwitchInputStatus> waterStatusList=switchInputStatusService.findLatestData(gitInfoId,DeviceInterfaceType.WATER);
		List<SwitchInputStatus> smokeStatusList=switchInputStatusService.findLatestData(gitInfoId,DeviceInterfaceType.SMOKE);
		model.addAttribute("gitInfo", gitInfo);
		model.addAttribute("upsStatusList", upsStatusList);
		model.addAttribute("thStatusList", thStatusList);
		model.addAttribute("waterStatusList", waterStatusList);
		model.addAttribute("smokeStatusList",smokeStatusList);
		return "upsStatus/upsStatusView";
	}

	@RequiresPermissions("sys:upsStatus:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String ids){
		upsStatusService.updateStatusByIds(ids);
		return "success";
	}
	@RequestMapping(value="getUpsStatus",method=RequestMethod.POST)
	@ResponseBody
	public List<UpsStatus> getUpsStatus(Integer gitInfoId){
		List<UpsStatus> upsStatusList=upsStatusService.findLatestDataByGitInfoId(gitInfoId);
		
		return upsStatusList;
	}
	/**
	 * @Description: 获取最新温湿度状态信息控制层方法
	 * @param gitInfoId
	 * @return
	 * @Since :2015年10月19日 下午2:07:56
	 */
	@RequestMapping(value="getLatestData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getLatestData(Integer gitInfoId){
		GitInfo gitInfo=gitInfoService.get(gitInfoId);
		Map<String,Object> map=new HashMap<String, Object>();
		List<ThStatus> thStatusList=thStatusService.findLatestDataByGitInfoId(gitInfoId);
		List<UpsStatus> upsStatusList=upsStatusService.findLatestDataByGitInfoId(gitInfoId);
		List<SwitchInputStatus> waterStatusList=switchInputStatusService.findLatestData(gitInfoId, DeviceInterfaceType.WATER);
		List<SwitchInputStatus> smokeStatusList=switchInputStatusService.findLatestData(gitInfoId, DeviceInterfaceType.SMOKE);
		map.put("thStatusList", thStatusList);
		map.put("upsStatusList", upsStatusList);
		map.put("waterStatusList", waterStatusList);
		map.put("smokeStatusList", smokeStatusList);
		map.put("gitInfo", gitInfo);
		return map;
	}
}
