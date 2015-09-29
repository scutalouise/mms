package com.agama.pemm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.common.web.BaseController;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.domain.AlarmCondition;
import com.agama.pemm.domain.AlarmLevel;  
import com.agama.pemm.service.IAlarmConditionService;
import com.agama.pemm.service.IAlarmLevelService;

@Controller
@RequestMapping("system/alarmCondition")
public class AlarmConditionController extends BaseController {
	@Autowired
	private IAlarmConditionService alarmConditionService;
	@Autowired
	private IAlarmLevelService alarmLevelService;

	@RequestMapping(method = RequestMethod.GET)
	private String view() {
		return "alarmTemplate/alarmCondition";
	}

	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<AlarmCondition> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(request);
		page = alarmConditionService.search(page, filters);
		return getEasyUIData(page);
	}

	@RequestMapping(value = "addForm", method = RequestMethod.GET)
	public String addForm(Model model) {
		model.addAttribute("alarmCondition", new AlarmCondition());
		model.addAttribute("deviceTypes", DeviceType.values());
		model.addAttribute("action", "add");
		return "alarmTemplate/conditionForm";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String save(AlarmCondition alarmCondition) {
		AlarmLevel alarmLevel = alarmLevelService.get(alarmCondition
				.getAlarmLevel().getId());
		alarmCondition.setAlarmLevel(alarmLevel);
		alarmConditionService.save(alarmCondition);
		return "success";
	}

	@RequestMapping(value = "updateForm/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		AlarmCondition alarmCondition = alarmConditionService.get(id);
		model.addAttribute("alarmCondition", alarmCondition);
		model.addAttribute("deviceTypes", DeviceType.values());
		model.addAttribute("action", "update");
		return "alarmTemplate/conditionForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid AlarmCondition alarmCondition) {
		AlarmLevel alarmLevel = alarmLevelService.get(alarmCondition
				.getAlarmLevel().getId());
		alarmCondition.setAlarmLevel(alarmLevel);
		alarmConditionService.update(alarmCondition);
		return "success";
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String ids) {
		alarmConditionService.updateStatusByIds(ids);
		return "success";

	}
}
