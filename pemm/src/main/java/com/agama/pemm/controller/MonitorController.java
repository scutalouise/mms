package com.agama.pemm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.AreaInfo;
import com.agama.authority.entity.Organization;
import com.agama.authority.service.IAreaInfoService;
import com.agama.authority.service.IOrganizationService;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceType;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IAcStatusService;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IGitInfoService;
import com.agama.pemm.service.ISendConfigService;
import com.agama.pemm.service.IStatisticService;
import com.agama.pemm.service.ISwitchInputStatusService;
import com.agama.pemm.service.IThStatusService;
import com.agama.pemm.service.IUpsStatusService;

@Controller
@RequestMapping("system/monitor")
public class MonitorController {
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IStatisticService statisticService;
	@Autowired
	private IAreaInfoService areaInfoService;
	@Autowired
	private IGitInfoService gitInfoService;
	@Autowired
	private IUpsStatusService upsStatusService;
	@Autowired
	private IThStatusService thStatusService;
	@Autowired
	private ISwitchInputStatusService switchInputStatusService;
	@Autowired
	private ISendConfigService sendConfigService;
	@Autowired
	private IAcStatusService acStatusService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {

		return "monitor/index";
	}

	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) {
		List<Map<String, Object>> currentStateAndNum = deviceService
				.getCurrentStateAndCount();
		for (Map<String, Object> map : currentStateAndNum) {
			model.addAttribute("currentState_" + map.get("currentState"),
					map.get("num"));
		}
		Integer good = statisticService
				.statisticBrancheStateNum(StateEnum.good);
		Integer warning = statisticService
				.statisticBrancheStateNum(StateEnum.warning);
		Integer error = statisticService
				.statisticBrancheStateNum(StateEnum.error);
		model.addAttribute("good", good);
		model.addAttribute("warning", warning);
		model.addAttribute("error", error);
		model.addAttribute("total", good + warning + error);
		return "monitor/main";
	}

	@RequestMapping(value = "branchView", method = RequestMethod.GET)
	public String branchView() {
		
		return "monitor/branchView";
	}
	@RequestMapping(value = "branchList", method = RequestMethod.POST)
	public String branchList(StateEnum stateEnum,String searchValue, Model model) {
		List<AreaInfo> areaInfoList = areaInfoService
				.getListRelevancyOrganization(stateEnum,searchValue);

		model.addAttribute("areaInfoList", areaInfoList);
		return "monitor/branchList";
	}

	@RequestMapping(value = "gitInfoList", method = RequestMethod.POST)
	@ResponseBody
	public List<GitInfo> getGitInfoListByOrganizationIdS(String organizationIds) {
		return gitInfoService.getListByOrganizationIdStr(organizationIds);
	}

	@RequestMapping(value = "deviceView", method = RequestMethod.GET)
	public String deviceView(String gitIds, Model model) {
		List<GitInfo> gitInfoList = gitInfoService.getListByIds(gitIds);
		List<Organization> organizationList = organizationService
				.getOrganizationListByGitIds(gitIds);
		model.addAttribute("gitInfoList", gitInfoList);
		model.addAttribute("organizationList", organizationList);
		model.addAttribute("gitIds", gitIds);
		return "monitor/deviceView";
	}

	@RequestMapping(value = "deviceDetail", method = RequestMethod.GET)
	public String deviceDetail(String gitIds, Model model) {
		String[] gitInfoIds = gitIds.split(",");
		if (gitInfoIds.length > 0) {
			List<GitInfo> gitInfoList = gitInfoService
					.getDeviceStatusByGitInfoIds(gitInfoIds);
			model.addAttribute("gitInfoList", gitInfoList);
		}
		model.addAttribute("gitIds", gitIds);
		return "monitor/deviceDetail";
	}

	@RequestMapping(value = "gitToDeviceDetail", method = RequestMethod.GET)
	public String gitToDeviceDetail(String gitIds, Model model) {
		String[] gitInfoIds = gitIds.split(",");
		if (gitInfoIds.length > 0) {
			List<GitInfo> gitInfoList = gitInfoService
					.getDeviceStatusByGitInfoIds(gitInfoIds);
			model.addAttribute("gitInfoList", gitInfoList);
		}
		model.addAttribute("gitIds", gitIds);
		return "monitor/gitToDeviceDetail";
	}
	@RequestMapping(value = "deviceInformationList", method = RequestMethod.GET)
	public String deviceInformationList(Integer gitInfoId, Model model) {

		List<Device> upsDeviceList = deviceService
				.getListByGitInfoIdAndDeviceType(gitInfoId, DeviceType.UPS);
		List<Device> thDeviceList = deviceService
				.getListByGitInfoIdAndDeviceType(gitInfoId, DeviceType.TH);
		model.addAttribute("upsDeviceList", upsDeviceList);
		model.addAttribute("thDeviceList", thDeviceList);
		model.addAttribute("gitInfoId", gitInfoId);
		return "monitor/deviceInformationList";
	}
	@RequestMapping(value="closeAlarm",method=RequestMethod.POST)
	@ResponseBody
	public boolean closeAlarm(String ip){
		boolean result=sendConfigService.closeAlarm(ip);
		return result;
		
	}
	@RequestMapping(value="closeOrOpenOfAc",method=RequestMethod.POST)
	@ResponseBody
	public boolean closeOrOpenOfAc(String ip,Integer index,Integer command){
		return acStatusService.closeOrOpenOfAc(ip,index,command);
		
	}

}
