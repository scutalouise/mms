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
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.domain.AlarmLevel;
import com.agama.device.service.IAlarmLevelService;

@Controller
@RequestMapping("system/alarmLevel")
public class AlarmLevelController extends BaseController{
	@Autowired
	private IAlarmLevelService alarmLevelService;
	/**
	 * @Description:默认页面
	 * @return
	 * @Since :2015年12月30日 下午3:14:08
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String view(){
		return "alarm/alarmLevel";
	}
	/**
	 * @Description:显示数据
	 * @param request
	 * @return
	 * @Since :2015年12月30日 下午3:13:49
	 */
	@RequiresPermissions("sys:alarmLevel:view")
	@RequestMapping(value="json",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getData(HttpServletRequest request){
		Page<AlarmLevel> page=getPage(request);
		List<PropertyFilter> filters=PropertyFilter.buildFromHttpRequest(request);
		page=alarmLevelService.search(page, filters);
		return getEasyUIData(page);
	}
	/**
	 * @Description:显示报警等级添加表单
	 * @param model
	 * @return
	 * @Since :2015年12月30日 下午3:12:22
	 */
	@RequiresPermissions("sys:alarmLevel:add")
	@RequestMapping(value="addForm",method=RequestMethod.GET)
	public String addForm(Model model){
		model.addAttribute("alarmLevel",new AlarmLevel());
		model.addAttribute("action", "add");
		return "alarm/alarmLevelForm";
	}
	/**
	 * @Description:保存数据
	 * @param alarmLevel
	 * @return
	 * @Since :2015年12月30日 下午3:16:49
	 */
	@RequiresPermissions("sys:alarmLevel:add")
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String save(AlarmLevel alarmLevel){
		alarmLevel.setStatus(StatusEnum.NORMAL);
		alarmLevelService.save(alarmLevel);
		return "success";
	}
	/**
	 * @Description:删除
	 * @param ids
	 * @return
	 * @Since :2015年12月30日 下午3:24:25
	 */
	@RequiresPermissions("sys:alarmLevel:delete")
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(String ids){
		alarmLevelService.updateStatusByIds(ids);
		return "success";
	}
	/**
	 * 
	 * @Description:进入修改告警等级表单
	 * @param id
	 * @param model
	 * @return
	 * @Since :2015年12月30日 下午3:30:25
	 */
	@RequiresPermissions("sys:alarmLevel:update")
	@RequestMapping(value="updateForm/{id}",method=RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id,Model model){
		AlarmLevel alarmLevel=alarmLevelService.get(id);
		model.addAttribute("alarmLevel",alarmLevel);
		model.addAttribute("action", "update");
		return "alarm/alarmLevelForm";
		
	}
	/**
	 * @Description:修改告警等级
	 * @param alarmLevel
	 * @return
	 * @Since :2015年12月30日 下午3:33:35
	 */
	@RequiresPermissions("sys:alarmLevel:update")
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public String update(@Valid AlarmLevel alarmLevel){
		alarmLevelService.update(alarmLevel);
		return "success";
	}
	/**
	 * @Description:获取所有的报警等级方法
	 * @return
	 * @Since :2015年12月30日 下午3:34:07
	 */
	@RequestMapping(value="getData",method=RequestMethod.GET)
	@ResponseBody
	public List<AlarmLevel> getData(){
		List<AlarmLevel> alarmLevels=alarmLevelService.getListByStatus(StatusEnum.NORMAL);
		return alarmLevels;
	}
	
	
	

}
