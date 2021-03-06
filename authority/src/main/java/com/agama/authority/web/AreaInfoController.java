package com.agama.authority.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.AreaInfo;
import com.agama.authority.service.IAreaInfoService;
import com.agama.authority.utils.UserUtil;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.TreeBean;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;

/**
 * 区域信息类型controller
 * @author ty
 * @date 2015年1月22日
 */
@Controller
@RequestMapping("system/area")
public class AreaInfoController extends BaseController{

	@Autowired
	private IAreaInfoService areaInfoService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/areaInfoList";
	}
	
	/**
	 * 获取区域信息类型json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public List<AreaInfo> areaInfoList(HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		filters.add(new PropertyFilter("EQ_StatusEnum_status",StatusEnum.NORMAL.toString()));//过滤掉，状态为删除状态并且不可用的记录；
		filters.add(new PropertyFilter("EQ_EnabledStateEnum_enable",EnabledStateEnum.ENABLED.toString()));//过滤掉，状态为禁用的记录；
		return areaInfoService.search(filters);
	}
	
	/**
	 * 添加区域信息跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("areaInfo", new AreaInfo());
		model.addAttribute("action", "create");
		return "system/areaInfoForm";
	}

	/**
	 * 添加区域信息
	 * 
	 * @param goodsType
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid AreaInfo areaInfo, Model model) {
		areaInfoService.save(areaInfo);
		return "success";
	}

	/**
	 * 修改区域信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("areaInfo", areaInfoService.get(id));
		model.addAttribute("action", "update");
		return "system/areaInfoForm";
	}

	/**
	 * 修改区域信息
	 * 
	 * @param goodsType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody AreaInfo areaInfo,Model model) {
		areaInfoService.update(areaInfo);
		return "success";
	}

	/**
	 * 删除区域信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		areaInfoService.delete(id, UserUtil.getCurrentUser().getId());
		return "success";
	}
	
	
	@RequestMapping(value="tree")
	@ResponseBody
	public List<TreeBean> tree(Integer pid){
		List<TreeBean> treeBeans=areaInfoService.getTreeByPid(pid);
		return treeBeans;
	}
	
}
