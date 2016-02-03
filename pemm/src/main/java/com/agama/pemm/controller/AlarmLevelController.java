package com.agama.pemm.controller;

import java.util.HashMap;
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
import com.agama.common.web.BaseController;
import com.agama.pemm.domain.AlarmLevel;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IAlarmLevelService;

/**
 * @Author:ranjunfeng
 * @Since :2015年9月17日 下午5:51:35
 * @Description:告警等级控制层
 */
@Controller
@RequestMapping("system/alarmLevel")
public class AlarmLevelController extends BaseController{
	@Autowired
	private IAlarmLevelService alarmLevelService;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "alarmLevel/alarmLevelList";
	}
	@RequiresPermissions("sys:alarmLevel:view")
	@RequestMapping(value="json",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getData(Integer areaInfoId,HttpServletRequest request){
		Page<AlarmLevel> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		
		page = alarmLevelService.search(page, filters);
		return getEasyUIData(page);
	}
	@RequiresPermissions("sys:alarmLevel:add")
	@RequestMapping(value="addForm",method=RequestMethod.GET)
	public String addForm(Model model){
		model.addAttribute("alarmLevel",new AlarmLevel());
		model.addAttribute("action", "add");
		
	
		return "alarmLevel/alarmLevelForm";
	}
	@RequiresPermissions("sys:alarmLevel:add")
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String  save(AlarmLevel alarmLevel){
		alarmLevel.setStatus(0);
		alarmLevelService.save(alarmLevel);
		return "success";
		
	}
	@RequiresPermissions("sys:alarmLevel:delete")
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(String ids){
		alarmLevelService.updateStatusByIds(ids);
		return "success";
		
	}
	@RequiresPermissions("sys:alarmLevel:update")
	@RequestMapping(value="updateForm/{id}",method=RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id,Model model){
		AlarmLevel alarmLevel=alarmLevelService.get(id);
		model.addAttribute("alarmLevel",alarmLevel);
		model.addAttribute("action", "update");
		return "alarmLevel/alarmLevelForm";
	}
	@RequiresPermissions("sys:alarmLevel:update")
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public String update(@Valid AlarmLevel alarmLevel){
		alarmLevelService.update(alarmLevel);
		return "success";
	}
	/**
	 * @return
	 * @Since :2015年9月22日 下午3:43:33
	 * @Desciption:获取所有的报警等级方法
	 */
	@RequestMapping(value="getData",method=RequestMethod.GET)
	@ResponseBody
	public List<AlarmLevel> getData(){
		List<AlarmLevel> alarmLevels=alarmLevelService.getListByStatus(0);
		return alarmLevels;
	}

}
