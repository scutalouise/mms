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
import com.agama.common.enumbean.AlarmOptionUnitEnum;
import com.agama.common.enumbean.DeviceAlarmOptionEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.OperationType;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmLevel;
import com.agama.device.domain.AlarmTemplate;
import com.agama.device.domain.DeviceAlarmCondition;
import com.agama.device.domain.DeviceAlarmRule;
import com.agama.device.service.IAlarmLevelService;
import com.agama.device.service.IAlarmTemplateService;
import com.agama.device.service.IDeviceAlarmConditionService;
import com.agama.device.service.IDeviceAlarmRuleService;

@Controller
@RequestMapping("system/deviceAlarmRule")
public class DeviceAlarmRuleController extends BaseController{
	@Autowired
	private IDeviceAlarmRuleService deviceAlarmRuleService;
	@Autowired
	private IAlarmLevelService alarmLevelService;
	@Autowired
	private IDeviceAlarmConditionService deviceAlarmConditionService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String view(){
		return "alarm/deviceAlarmRule";
	}
	@RequestMapping(value="json",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getData(HttpServletRequest request){
		Page<DeviceAlarmRule> page=getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page=deviceAlarmRuleService.search(page,filters);
		return getEasyUIData(page);
		
	}
	@RequestMapping(value="addForm",method=RequestMethod.GET)
	public  String addForm(Model model){
		model.addAttribute("action", "add");
		model.addAttribute("deviceAlarmRule",new DeviceAlarmRule());
		model.addAttribute("deviceAlarmOptions",DeviceAlarmOptionEnum.values());
		model.addAttribute("operationTypes", OperationType.values());
		return "alarm/deviceAlarmRuleForm";
	}
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String save(DeviceAlarmRule deviceAlarmRule){
		
		DeviceAlarmCondition deviceAlarmCondition=deviceAlarmConditionService.get(deviceAlarmRule.getDeviceAlarmCondition().getId());
		deviceAlarmRule.setDeviceAlarmCondition(deviceAlarmCondition);
		deviceAlarmRuleService.save(deviceAlarmRule);
		return "success";
	}
	@RequestMapping(value="updateForm/{id}",method=RequestMethod.GET)
	public  String updateForm(@PathVariable("id") Integer id,Model model){
		DeviceAlarmRule deviceAlarmRule=deviceAlarmRuleService.get(id);
		model.addAttribute("deviceAlarmRule",deviceAlarmRule);
		model.addAttribute("action","update");
		model.addAttribute("deviceAlarmOptions",DeviceAlarmOptionEnum.values());
		model.addAttribute("operationTypes", OperationType.values());
		return "alarm/deviceAlarmRuleForm";
	}
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public String update(@Valid DeviceAlarmRule deviceAlarmRule){
		DeviceAlarmCondition deviceAlarmCondition=deviceAlarmConditionService.get(deviceAlarmRule.getDeviceAlarmCondition().getId());
		deviceAlarmRule.setDeviceAlarmCondition(deviceAlarmCondition);
		deviceAlarmRuleService.update(deviceAlarmRule);
		return "success"; 
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestBody List<Integer> deviceAlarmRuleIdList){
		deviceAlarmRuleService.delete(deviceAlarmRuleIdList);
		return "success";
	}
	@RequestMapping(value="getAlarmOptionUnitByAlarmOption/{deviceAlarmOption}",method=RequestMethod.POST)
	@ResponseBody
	public List<AlarmOptionUnitEnum> getAlarmOptionUnitByAlarmOption(@PathVariable("deviceAlarmOption") DeviceAlarmOptionEnum deviceAlarmOption){
		
		return AlarmOptionUnitEnum.getAlarmOptionUnitEnumByDeviceAlarmOptionEnum(deviceAlarmOption);
	}
	
	

}
