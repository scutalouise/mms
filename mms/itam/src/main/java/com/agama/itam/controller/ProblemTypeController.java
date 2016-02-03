package com.agama.itam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.enumbean.StatusEnum;
import com.agama.itam.domain.ProblemType;
import com.agama.itam.service.IProblemTypeService;

/**
 * @Description:问题类型控制层
 * @Author:佘朝军
 * @Since :2016年1月20日 上午9:46:00
 */
@Controller
@RequestMapping("maintenance/problemType")
public class ProblemTypeController {

	@Autowired
	private IProblemTypeService ptService;

	@RequestMapping(method = RequestMethod.GET)
	public String turnPage(Model model) {
		return "maintenance/problemType";
	}

	// @RequiresPermissions("maintenance:problemType:view")
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	private List<ProblemType> view() {
		List<ProblemType> ptList = new ArrayList<ProblemType>();
		try {
			ptList = ptService.getAllList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ptList;
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

	/**
	 * @Description:在处理修改方法之前，根据id加载数据库记录信息
	 * @param id
	 * @param model
	 * @Since :2016年1月21日 上午11:36:38
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("problemType", ptService.get(id));
		}
	}

}
