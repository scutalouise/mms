package com.agama.pemm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.AreaInfo;
import com.agama.authority.entity.Organization;
import com.agama.authority.service.IAreaInfoService;
import com.agama.common.domain.StateEnum;
import com.agama.pemm.domain.AlarmLog;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IAlarmLogService;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IGitInfoService;
import com.agama.pemm.service.IStatisticService;

@Controller
@RequestMapping("monitorPlatform")
public class MonitorPlatformController {
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IStatisticService statisticService;
	@Autowired
	private IAreaInfoService areaInfoService;
	@Autowired
	private IGitInfoService gitInfoService;
	@Autowired
	private IAlarmLogService alarmLogService;

	@RequestMapping("index")
	public String index(Model model) {
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
		return "monitorPlatform/monitor";
	}

	@RequestMapping("list/{stateEnum}")
	public String list(@PathVariable(value = "stateEnum") String stateEnum,
			String searchValue, Model model) {
		model.addAttribute("stateEnum", stateEnum);
		return "monitorPlatform/list";
	}

	@RequestMapping("loadList")
	public String loadList(StateEnum stateEnum, String searchValue, Model model) {
		List<AreaInfo> areaInfoList = areaInfoService
				.getListRelevancyOrganization(stateEnum, searchValue);
		model.addAttribute("areaInfoList", areaInfoList);
		return "monitorPlatform/loadList";
	}

	@RequestMapping(value = "gitInfoList", method = RequestMethod.POST)
	@ResponseBody
	public List<GitInfo> getGitInfoListByOrganizationIdS(String organizationIds) {
		return gitInfoService.getListByOrganizationIdStr(organizationIds);
	}

	@RequestMapping(value = "topAlarmLog")
	@ResponseBody
	public List<AlarmLog> topAlarmLog(Integer top) {
		List<AlarmLog> alarmLogs = alarmLogService.getAlarmLogforTop(top);
		return alarmLogs;
	}

	@RequestMapping(value = "details/{stateEnum}/{gitInfoId}")
	public String details(@PathVariable(value = "stateEnum") String stateEnum,
			@PathVariable(value = "gitInfoId") Integer gitInfoId, Model model) {

		String[] gitInfoIds = gitInfoId.toString().split(",");

		List<GitInfo> gitInfoList = gitInfoService
				.getDeviceStatusByGitInfoIds(gitInfoIds);

		model.addAttribute("gitInfoList", gitInfoList);
		model.addAttribute("gitInfoId", gitInfoId);
		model.addAttribute("stateEnum", stateEnum);
		return "monitorPlatform/details";
	}

	@RequestMapping(value = "alarmLog")
	public String alarmLog() {
		return "monitorPlatform/alarmLog";
	}

	@RequestMapping(value = "alarmLogAll", method = RequestMethod.POST)
	@ResponseBody
	public List<AlarmLog> alarmLogAll() {
		List<AlarmLog> alarmLogs = alarmLogService.getAlarmLogforTop(null);
		return alarmLogs;
	}
}
