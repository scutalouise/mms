package com.agama.itam.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.User;
import com.agama.authority.service.IUserService;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.enumbean.WeekEnum;
import com.agama.common.web.BaseController;
import com.agama.itam.domain.Arrange;
import com.agama.itam.service.IArrangeService;

/**@Description:
 * @Author:佘朝军
 * @Since :2016年2月1日 下午3:53:28
 */
@Controller
@RequestMapping("maintenance/arrange")
public class ArrangeController extends BaseController{
	
	@Autowired
	private IArrangeService arrangeService;
	@Autowired
	private IUserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String turnPage(Model model) {
		return "maintenance/arrange";
	}

	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> view(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page<Arrange> page = getPage(request);
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			page = arrangeService.search(page, filters);
			map = getEasyUIData(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String toCreate(Model model) {
		model.addAttribute("arrange", new Arrange());
		model.addAttribute("action", "create");
		return "maintenance/arrangeForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(Arrange arrange) {
		if (arrange != null) {
			arrange.setStatus(StatusEnum.NORMAL);
			arrange.setUpdateTime(new Date());
			arrangeService.save(arrange);
		}
		return "success";
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "id") Integer id) {
		Arrange arrange = arrangeService.get(id);
		arrange.setUpdateTime(new Date());
		arrange.setStatus(StatusEnum.DELETED);
		arrangeService.update(arrange);
		return "success";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable(value = "id") int id, Model model) {
		Arrange arrange = arrangeService.get(id);
		String[] workTimeArr = arrange.getWorkTime().split("-");
		model.addAttribute("arrange", arrange);
		model.addAttribute("startTime", workTimeArr[0]);
		model.addAttribute("endTime", workTimeArr[1]);
		model.addAttribute("workDay", Arrays.toString(arrange.getWorkDay().split(",")));
		model.addAttribute("action", "update");
		return "maintenance/arrangeForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute Arrange arrange) {
		arrange.setUpdateTime(new Date());
		arrangeService.update(arrange);
		return "success";
	}
	
	@RequestMapping(value = "userList", method = RequestMethod.GET)
	@ResponseBody
	public List<User> userList() {
		List<User> list = userService.getAllList();
		List<User> removeList = new ArrayList<User>();
		for (User user : list) {
			Arrange arrange = arrangeService.findByUserId(user.getId());
			if (arrange != null) {
				removeList.add(user);
			}
		}
		list.removeAll(removeList);
		return list;
	}
	
	@RequestMapping(value = "weekEnum", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> weekEnum() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (WeekEnum day : WeekEnum.values()) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("value", String.valueOf(day.getValue()));
			map.put("name", day.getText());
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
	public void getArrange(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("arrange", arrangeService.get(id));
		}
	}

}
