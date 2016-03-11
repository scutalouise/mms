package com.agama.itam.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.AlarmRuleType;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.itam.domain.ProblemType;
import com.agama.itam.service.IProblemTypeService;

/**
 * @Description:问题类型控制层
 * @Author:佘朝军
 * @Since :2016年1月20日 上午9:46:00
 */
@Controller
@RequestMapping("maintenance/problemType")
public class ProblemTypeController extends BaseController {

	@Autowired
	private IProblemTypeService ptService;

	@RequestMapping(method = RequestMethod.GET)
	public String turnPage(Model model) {
		return "maintenance/problemType";
	}

	// @RequiresPermissions("maintenance:problemType:view")
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> view(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page<ProblemType> page = getPage(request);
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			page = ptService.search(page, filters);
			map = getEasyUIData(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	private List<ProblemType> getList(HttpServletRequest request) {
		List<ProblemType> list = new ArrayList<ProblemType>();
		try {
			list = ptService.getAllList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// @RequiresPermissions("maintenance:problemType:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String toCreate(Model model) {
		model.addAttribute("problemType", new ProblemType());
		model.addAttribute("action", "create");
		return "maintenance/problemTypeForm";
	}

	// @RequiresPermissions("maintenance:problemType:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(ProblemType problemType) {
		if (problemType != null) {
			problemType.setInitial(false);
			problemType.setAlarmGenerate(false);
			problemType.setAlarmRuleType(AlarmRuleType.CPU);
			problemType.setStatus(StatusEnum.NORMAL);
			ptService.save(problemType);
		}
		return "success";
	}

	// @RequiresPermissions("maintenance:problemType:delete")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "id") int id) {
		ProblemType pt = ptService.get(id);
		pt.setStatus(StatusEnum.DELETED);
		ptService.update(pt);
		return "success";
	}

	// @RequiresPermissions("maintenance:problemType:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable(value = "id") int id, Model model) {
		ProblemType pt = ptService.get(id);
		model.addAttribute("problemType", pt);
		model.addAttribute("action", "update");
		return "maintenance/problemTypeForm";
	}

	// @RequiresPermissions("maintenance:problemType:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute ProblemType problemType) {
		ptService.update(problemType);
		return "success";
	}

	@RequestMapping(value = "identifier/{identifier}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getProblemTypeByIdentifier(@PathVariable(value = "identifier") String identifier) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = ptService.getListByIdentifierForHandset(identifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "deviceType/{deviceType}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProblemType> getProblemTypeBydeviceType(@PathVariable(value = "deviceType") String deviceType) {
		List<ProblemType> list = new ArrayList<ProblemType>();
		try {
			if (StringUtils.isNotBlank(deviceType)) {
				list = ptService.getListByDeviceType(FirstDeviceType.valueOf(deviceType));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "deviceType", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getDeviceType() {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list.add(new HashMap<String, String>(){{
			put("value", "");
			put("name", "——全部——");
		}});
		for (FirstDeviceType fdt : FirstDeviceType.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", fdt.toString());
			map.put("name", fdt.getName());
			list.add(map);
		}
		return list;
	}

	/**
	 * @Description:在处理修改方法之前，根据id加载数据库记录信息
	 * @param id
	 * @param model
	 * @Since :2016年1月21日 上午11:36:38
	 */
	@ModelAttribute
	public void getProblemType(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("problemType", ptService.get(id));
		}
	}

}
