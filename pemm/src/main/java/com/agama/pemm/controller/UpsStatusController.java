package com.agama.pemm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.common.web.BaseController;
import com.agama.authority.system.service.IAreaInfoService;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IDeviceOperationService;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IGitInfoService;
import com.agama.pemm.service.IUpsStatusService;

@Controller
@RequestMapping("upsStatus")
public class UpsStatusController extends BaseController {
	@Autowired
	private IUpsStatusService upsStatusService;
	@Autowired
	private IDeviceService deviceService;
	
	@Autowired
	private IAreaInfoService areaInfoService;
	@Autowired
	private IGitInfoService gitInfoService;

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
		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(request);
		Integer gitInfoId=Integer.parseInt(request.getParameter("gitInfoId"));
		Integer index=Integer.parseInt(request.getParameter("index"));
		page = upsStatusService.searchByGitInfoIdAndDeviceTypeAndIndex(page,gitInfoId,DeviceType.UPS,index);
		return getEasyUIData(page);

	}
	@RequiresPermissions("sys:upsStatus:gitInfoView")
	@RequestMapping(value="gitInfoView")
	public String gitInfoView(Integer areaInfoId,Model model){
		String areaInfoIdStr=areaInfoService.getAreaInfoIdStrById(areaInfoId);
		List<GitInfo> gitInfoList=gitInfoService.getListByAreaInfoIdStr(areaInfoIdStr);
		List<DeviceStateRecord> deviceStateRecordList=deviceService.getDeviceStateRecordByAreaInfoId(areaInfoIdStr);
		model.addAttribute("gitInfoList", gitInfoList);
		model.addAttribute("deviceStateRecordList", deviceStateRecordList);
		return "upsStatus/gitInfoView";
	}
	@RequiresPermissions("sys:upsStatus:upsStatusView")
	@RequestMapping(value="upsStatusView")
	public String upsStatusView(@RequestParam(required=false) Integer gitInfoId,Model model){
		List<UpsStatus> upsStatusList=upsStatusService.findLatestDataByGitInfoId(gitInfoId);
		model.addAttribute("upsStatusList", upsStatusList);
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
}
