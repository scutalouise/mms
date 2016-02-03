package com.agama.device.controller;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
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
import com.agama.common.enumbean.AlarmDeviceType;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.BrandType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.domain.AlarmCondition;
import com.agama.device.domain.AlarmLevel;
import com.agama.device.domain.AlarmTemplate;
import com.agama.device.service.IAlarmConditionService;
import com.agama.device.service.IAlarmLevelService;
import com.agama.device.service.IAlarmTemplateService;

@Controller
@RequestMapping("system/alarmCondition")
public class AlarmConditionController extends BaseController {
	@Autowired
	private IAlarmConditionService alarmConditionService;
	@Autowired
	private IAlarmLevelService alarmLevelService;
	@Autowired
	private IAlarmTemplateService alarmTemplateService;
	/**
	 * @Description:默认页面
	 * @return
	 * @Since :2015年12月31日 下午1:52:48
	 */
	@RequestMapping(method = RequestMethod.GET)
	private String view() {
		return "alarm/alarmCondition";
	}
	@RequiresPermissions("sys:alarmCondition:view")
	@RequestMapping(value="json",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getData(HttpServletRequest request){
		
		Page<AlarmCondition> page = getPage(request);
		
		Integer alarmTemplateId=Integer.parseInt(request.getParameter("alarmTemplateId"));
		StatusEnum status=StatusEnum.valueOf(request.getParameter("status"));
		page = alarmConditionService.searchByAlarmTemplateIdAndStatus(page, alarmTemplateId,status);
		return getEasyUIData(page);
	}
	@RequiresPermissions("sys:alarmCondition:add")
	@RequestMapping(value="addForm",method=RequestMethod.GET)
	public String addForm(Model model){
		model.addAttribute("alarmCondition", new AlarmCondition());
		model.addAttribute("alarmDeviceTypes", AlarmDeviceType.values());
		model.addAttribute("brandTypes",BrandType.values());
		model.addAttribute("action", "add");
		return "alarm/conditionForm";
	}
	@RequiresPermissions("sys:alarmCondition:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String save(AlarmCondition alarmCondition){
		AlarmLevel alarmLevel = alarmLevelService.get(alarmCondition
				.getAlarmLevel().getId());
		AlarmTemplate alarmTemplate=alarmTemplateService.get(alarmCondition.getAlarmTemplate().getId());
		alarmCondition.setAlarmLevel(alarmLevel);
		alarmCondition.setAlarmTemplate(alarmTemplate);
		alarmConditionService.save(alarmCondition);
		return "success";
	}
	
	@RequiresPermissions("sys:alarmCondition:update")
	@RequestMapping(value = "updateForm/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		AlarmCondition alarmCondition = alarmConditionService.get(id);
		model.addAttribute("alarmCondition", alarmCondition);
		model.addAttribute("alarmDeviceTypes", AlarmDeviceType.values());
		model.addAttribute("brandTypes",BrandType.values());
		model.addAttribute("action", "update");
		return "alarm/conditionForm";
	}

	@RequiresPermissions("sys:alarmCondition:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid AlarmCondition alarmCondition) {
		AlarmLevel alarmLevel = alarmLevelService.get(alarmCondition
				.getAlarmLevel().getId());
		AlarmTemplate alarmTemplate=alarmTemplateService.get(alarmCondition.getAlarmTemplate().getId());
		alarmCondition.setAlarmLevel(alarmLevel);
		alarmCondition.setAlarmTemplate(alarmTemplate);
		alarmConditionService.update(alarmCondition);
		return "success";
	}
	@RequiresPermissions("sys:alarmCondition:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String ids) {
		alarmConditionService.updateStatusByIds(ids);
		return "success";

	}
	
	@RequestMapping(value="getAlarmOptionTypeListByAlarmDeviceType/{alarmDeviceType}",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getAlarmOptionTypeListByAlarmDeviceType(@PathVariable("alarmDeviceType") AlarmDeviceType alarmDeviceType){
		List<Map<String,Object>> list=AlarmOptionType.getAlarmOptionTypeListByAlarmDeviceType(alarmDeviceType);
		
		return list;
	}
	@RequestMapping(value="getAlarmOptionTypeListByDeviceTypeAndBrand/{alarmDeviceType}/{brand}",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getAlarmOptionTypeListByDeviceTypeAndBrand(@PathVariable("alarmDeviceType") AlarmDeviceType alarmDeviceType,@PathVariable("brand") String brand){
		String name=alarmDeviceType.toString()+"_"+brand;
		return AlarmOptionType.getAlarmOptionTypeListByName(name);
		
	}
}
