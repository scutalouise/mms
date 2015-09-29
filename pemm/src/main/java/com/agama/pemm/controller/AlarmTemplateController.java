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
import com.agama.authority.system.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.pemm.domain.AlarmTemplate;
import com.agama.pemm.service.IAlarmTemplateService;

@Controller
@RequestMapping("system/alarmTemplate")
public class AlarmTemplateController extends BaseController {
	@Autowired
	private IAlarmTemplateService alarmTemplateService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "alarmTemplate/alarmTemplate";
	}

	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<AlarmTemplate> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(request);
		page = alarmTemplateService.search(page, filters);
		return getEasyUIData(page);
	}

	@RequestMapping(value="addForm",method=RequestMethod.GET)
	public String addForm(Model model) {
		model.addAttribute("alarmTemplate", new AlarmTemplate());
		model.addAttribute("action", "add");
		return "alarmTemplate/templateForm";
	}
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String save(AlarmTemplate alarmTemplate){
		alarmTemplateService.save(alarmTemplate);
		return "success";
	}
	@RequestMapping(value="updateForm/{id}",method=RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id,Model model){
		AlarmTemplate alarmTemplate=alarmTemplateService.get(id);
		model.addAttribute("alarmTemplate",alarmTemplate);
		model.addAttribute("action","update");
		return "alarmTemplate/templateForm";
		
	}
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public String update(@Valid AlarmTemplate alarmTemplate){
		alarmTemplateService.update(alarmTemplate);
		return "success";
	}
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(String ids){
		alarmTemplateService.updateStatusByIds(ids);
		return "success";
		
	}
}
