package com.agama.pemm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.AlarmType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.OperationType;
import com.agama.common.web.BaseController;
import com.agama.pemm.domain.AlarmRule;
import com.agama.pemm.service.IAlarmConditionService;
import com.agama.pemm.service.IAlarmRuleService;

@Controller
@RequestMapping("system/alarmRule")
public class AlarmRuleController extends BaseController {
	@Autowired
	private IAlarmRuleService alarmRuleService;
	@Autowired
	private IAlarmConditionService alarmConditionService;
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "alarmTemplate/alarmRule";
	}
	@RequiresPermissions("sys:alarmRule:view")
	@RequestMapping(value="json",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getData(HttpServletRequest request){
		Page<AlarmRule> page=getPage(request);
		List<PropertyFilter> filters=PropertyFilter.buildFromHttpRequest(request);
		page=alarmRuleService.search(page, filters);
		return getEasyUIData(page);
	}
	@RequiresPermissions("sys:alarmRule:add")
	@RequestMapping(value = "addForm", method = RequestMethod.GET)
	public String addForm(Model model,DeviceInterfaceType deviceInterfaceType){
		model.addAttribute("alarmRule",new AlarmRule());
	
		model.addAttribute("alarmTypes",AlarmType.getAlarmTypeByDeviceInterfaceType(deviceInterfaceType));
		model.addAttribute("action", "add");
		model.addAttribute("operationTypes",OperationType.values());
		return "alarmTemplate/ruleForm";
		
	}
	@RequiresPermissions("sys:alarmRule:add")
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String save(AlarmRule alarmRule){
		alarmRuleService.save(alarmRule);
		alarmConditionService.cleanAlarmConditionMap();
		return "success";
	}
	@RequiresPermissions("sys:alarmRule:update")
	@RequestMapping(value="updateForm/{id}/{deviceInterfaceType}",method=RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id,@PathVariable("deviceInterfaceType") DeviceInterfaceType deviceInterfaceType,Model model){
		AlarmRule alarmRule=alarmRuleService.get(id);
		model.addAttribute("alarmRule",alarmRule);
		model.addAttribute("alarmTypes",AlarmType.getAlarmTypeByDeviceInterfaceType(deviceInterfaceType));
		model.addAttribute("operationTypes",OperationType.values());
		model.addAttribute("action","update");
		return "alarmTemplate/ruleForm";
		
	}
	@RequiresPermissions("sys:alarmRule:update")
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public String update(@Valid AlarmRule alarmRule){
		alarmRuleService.update(alarmRule);
		alarmConditionService.cleanAlarmConditionMap();
		return "success";
	}
	@RequiresPermissions("sys:alarmRule:delete")
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(String ids){
		alarmRuleService.updateStatusByIds(ids);
		return "success";
	}
}
