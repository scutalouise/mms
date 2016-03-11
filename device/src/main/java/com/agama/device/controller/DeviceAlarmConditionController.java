package com.agama.device.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.AlarmDeviceType;
import com.agama.common.enumbean.BrandType;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.web.BaseController;
import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmLevel;
import com.agama.device.domain.AlarmTemplate;
import com.agama.device.domain.DeviceAlarmCondition;
import com.agama.device.domain.DeviceAlarmRule;
import com.agama.device.service.IAlarmLevelService;
import com.agama.device.service.IAlarmTemplateService;
import com.agama.device.service.IDeviceAlarmConditionService;
@Controller
@RequestMapping("system/deviceAlarmCondition")
public class DeviceAlarmConditionController extends BaseController {
	
	
	
	@Autowired
	private IDeviceAlarmConditionService deviceAlarmConditionService;
	@Autowired
	private IAlarmTemplateService alarmTemplateService;
	
	@Autowired
	private IAlarmLevelService alarmLevelService;

	
	@RequestMapping(method = RequestMethod.GET)
	private String view() {
		return "alarm/deviceAlarmCondition";
	}
	
	
	@RequestMapping(value="json",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getData(HttpServletRequest request){
		Page<DeviceAlarmCondition> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page=deviceAlarmConditionService.search(page, filters);
		return getEasyUIData(page);
	}
	@RequestMapping(value="addForm",method=RequestMethod.GET)
	public String addForm(Model model){
		model.addAttribute("deviceAlarmCondition", new DeviceAlarmCondition());
		model.addAttribute("alarmDeviceTypes",FirstDeviceType.values());
		model.addAttribute("alarmDevices",SecondDeviceType.values());

		model.addAttribute("action", "add");
		return "alarm/deviceConditionForm";
	}
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String save(DeviceAlarmCondition deviceAlarmCondition){
		AlarmLevel alarmLevel = alarmLevelService.get(deviceAlarmCondition
				.getAlarmLevel().getId());
		AlarmTemplate alarmTemplate=alarmTemplateService.get(deviceAlarmCondition.getAlarmTemplate().getId());
		deviceAlarmCondition.setAlarmLevel(alarmLevel);
		deviceAlarmCondition.setAlarmTemplate(alarmTemplate);
		deviceAlarmConditionService.save(deviceAlarmCondition);
		return "success";
	}
	
	@RequestMapping(value="updateForm/{id}",method=RequestMethod.GET)
	public  String updateForm(@PathVariable("id") Integer id,Model model){
		DeviceAlarmCondition deviceAlarmCondition=deviceAlarmConditionService.get(id);
		
		model.addAttribute("deviceAlarmCondition", deviceAlarmCondition);
		model.addAttribute("alarmDeviceTypes",FirstDeviceType.values());
		model.addAttribute("alarmDevices",SecondDeviceType.values());

		model.addAttribute("action","update");
		return "alarm/deviceConditionForm";
	}
	
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public String update(@Valid DeviceAlarmCondition deviceAlarmCondition){
		AlarmLevel alarmLevel = alarmLevelService.get(deviceAlarmCondition
				.getAlarmLevel().getId());
		AlarmTemplate alarmTemplate=alarmTemplateService.get(deviceAlarmCondition.getAlarmTemplate().getId());
		deviceAlarmCondition.setAlarmLevel(alarmLevel);
		deviceAlarmCondition.setAlarmTemplate(alarmTemplate);
		deviceAlarmConditionService.update(deviceAlarmCondition);
		return "success";
	}
	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestBody List<Integer> deviceAlarmConditionIdList){
		deviceAlarmConditionService.delete(deviceAlarmConditionIdList);
		return "success";
	}
	
	@RequestMapping(value="getAlarmDeviceListByAlarmDeviceType/{alarmDeviceType}",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getAlarmDeviceListByAlarmDeviceType(@PathVariable("alarmDeviceType") FirstDeviceType alarmDeviceType){
		List<Map<String,Object>> list=SecondDeviceType.getSecondDeviceTypeListByFirstDeviceType(alarmDeviceType);
		return list;
	}

}
