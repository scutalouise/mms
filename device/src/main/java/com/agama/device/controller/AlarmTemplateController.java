package com.agama.device.controller;

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
import com.agama.common.enumbean.TemplateTypeEnum;
import com.agama.common.web.BaseController;
import com.agama.device.domain.AlarmTemplate;
import com.agama.device.domain.OrgAlarmTemplate;
import com.agama.device.service.IAlarmTemplateService;
import com.agama.device.service.IOrgAlarmTemplateService;

/**
 * @Description:告警模板控制层
 * @Author:ranjunfeng
 * @Since :2015年12月30日 下午4:33:50
 */
@Controller
@RequestMapping("system/alarmTemplate")
public class AlarmTemplateController extends BaseController {
	@Autowired
	private IAlarmTemplateService alarmTemplateService;
	@Autowired
	private IOrgAlarmTemplateService orgAlarmTemplateService;

	/**
	 * @Description:默认页面
	 * @return
	 * @Since :2015年12月30日 下午4:32:56
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "alarm/alarmTemplate";
	}
	/**
	 * @Description:设备业务告警模板
	 * @return
	 * @Since :2016年2月26日 下午4:24:38
	 */
	@RequestMapping(value="deviceAlarmTemplate",method=RequestMethod.GET)
	public String deviceAlarmTemplate(){
		return "alarm/deviceAlarmTemplate";
		
	}
	@RequiresPermissions("sys:alarmTemplate:view")
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		
		Page<AlarmTemplate> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(request);
		page = alarmTemplateService.search(page, filters);
		return getEasyUIData(page);
	}
	@RequiresPermissions("sys:alarmTemplate:add")
	@RequestMapping(value="addForm/{templateType}",method=RequestMethod.GET)
	public String addForm(@PathVariable("templateType") TemplateTypeEnum templateType,Model model) {
		AlarmTemplate alarmTemplate=new AlarmTemplate();
		alarmTemplate.setTemplateType(templateType);
		model.addAttribute("alarmTemplate", alarmTemplate);
		model.addAttribute("action", "add");
		return "alarm/templateForm";
	}
	@RequiresPermissions("sys:alarmTemplate:add")
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String save(AlarmTemplate alarmTemplate){
		alarmTemplateService.save(alarmTemplate);
		return "success";
	}
	@RequiresPermissions("sys:alarmTemplate:update")
	@RequestMapping(value="updateForm/{id}",method=RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id,Model model){
		AlarmTemplate alarmTemplate=alarmTemplateService.get(id);
		model.addAttribute("alarmTemplate",alarmTemplate);
		model.addAttribute("action","update");
		return "alarm/templateForm";
		
	}
	@RequiresPermissions("sys:alarmTemplate:update")
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public String update(@Valid AlarmTemplate alarmTemplate){
		alarmTemplateService.update(alarmTemplate);
		return "success";
	}
	@RequiresPermissions("sys:alarmTemplate:delete")
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(String ids){
		alarmTemplateService.updateStatusByIds(ids);
		return "success";
		
	}
	/**
	 * @Description:获取所有启用的告警模板
	 * @return
	 * @Since :2015年9月29日 下午4:26:50
	 */
	@RequestMapping(value="getData",method=RequestMethod.GET)
	@ResponseBody
	public List<AlarmTemplate> getList(HttpServletRequest request){
		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(request);
		return alarmTemplateService.search(filters);
	}
	@RequestMapping(value="chooseDeviceTemplate",method=RequestMethod.GET)
	public String chooseDeviceTemplate(){
		return "alarm/chooseDeviceTemplate";
	}
	@RequestMapping(value="findOrgAlarmTemplate",method=RequestMethod.GET)
	@ResponseBody
	public OrgAlarmTemplate findTemplateId(Integer orgId){
		OrgAlarmTemplate orgAlarmTemplate=orgAlarmTemplateService.findByOrgId(orgId);
		return orgAlarmTemplate;
	}
	@RequestMapping(value="saveOrgAlarmTemplate",method=RequestMethod.GET)
	@ResponseBody
	public String saveOrgAlarmTemplate(Integer orgId,Integer templateId){
		OrgAlarmTemplate orgAlarmTemplate=new OrgAlarmTemplate();
		orgAlarmTemplate.setOrgId(orgId);
		orgAlarmTemplate.setAlarmTemplateId(templateId);
		orgAlarmTemplateService.saveOrgAlarmTemplateId(orgAlarmTemplate);
		return "success";
		
	}
}
