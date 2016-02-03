package com.agama.itam.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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
import com.agama.common.enumbean.ProblemStatusEnum;
import com.agama.common.enumbean.ReportWayEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.service.IDeviceService;
import com.agama.itam.domain.Problem;
import com.agama.itam.service.IProblemService;
import com.agama.tool.utils.date.DateUtils;

/**
 * @Description:
 * @Author:佘朝军
 * @Since :2016年1月22日 下午1:50:32
 */
@Controller
@RequestMapping("maintenance/problem")
public class ProblemController extends BaseController {

	@Autowired
	private IProblemService problemService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDeviceService deviceService;

	@RequestMapping(method = RequestMethod.GET)
	public String turnPage(Model model) {
		return "maintenance/problem";
	}

	// @RequiresPermissions("maintenance:problem:view")
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> view(HttpServletRequest request, String recordEndTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page<Problem> page = getPage(request);
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			if (!StringUtils.isBlank(recordEndTime)) {
				Date endDate = DateUtils.parseDate(recordEndTime, "yyyy-MM-dd");
				endDate = DateUtils.calculateDate(endDate, 1);
				PropertyFilter pf = new PropertyFilter("LTD_recordTime", DateUtils.formatDate(endDate, "yyyy-MM-dd"));
				filters.add(pf);
			}
			page = problemService.searchForPage(page, filters);
			map = getEasyUIData(page);
		} catch (Exception e) {
			map.put("total", 0);
			e.printStackTrace();
		}
		return map;
	}

	// @RequiresPermissions("maintenance:problem:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String toCreate(Model model) {
		model.addAttribute("problem", new Problem());
		model.addAttribute("action", "create");
		return "maintenance/problemAddForm";
	}

	// @RequiresPermissions("maintenance:problem:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(Problem problem) {
		if (problem != null) {
			problem.setEnable(ProblemStatusEnum.NEW);
			problem.setStatus(StatusEnum.NORMAL);
			problem.setRecordTime(new Date());
			User user = getSessionUser();
			problem.setRecordUserId(user.getId());
			try {
				problemService.saveProblem(problem, user);
			} catch (Exception e) {
				e.printStackTrace();
				return "failure";
			}
		}
		return "success";
	}

	// @RequiresPermissions("maintenance:problem:delete")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "id") Long id) {
		Problem pt = problemService.get(id);
		try {
			problemService.deleteProblem(pt, getSessionUser());
		} catch (Exception e) {
			e.printStackTrace();
			return "failure";
		}
		return "success";
	}

	// @RequiresPermissions("maintenance:problem:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable(value = "id") Long id, Model model) {
		Problem pt = problemService.get(id);
		User user = userService.get(pt.getRecordUserId());
		pt.setRecordUserName(user.getName());
		model.addAttribute("problem", pt);
		model.addAttribute("action", "update");
		return "maintenance/problemForm";
	}

	// @RequiresPermissions("maintenance:problem:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute Problem problem) {
		User user = getSessionUser();
		if (problem.getEnable().equals(ProblemStatusEnum.RESOLVED)) {
			problem.setResolveUserId(user.getId());
		}
		try {
			problemService.updateProblem(problem, user);
		} catch (Exception e) {
			e.printStackTrace();
			return "failure";
		}
		return "success";
	}

	@RequestMapping(value = "enable/handle/{handle}/search/{flag}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getProblemEnable(@PathVariable("handle") boolean handle, @PathVariable("flag") boolean flag) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (flag) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("problemStatus", "");
			map.put("value", "");
			map.put("name", "—全部—");
			list.add(map);
		}
		for (ProblemStatusEnum pse : ProblemStatusEnum.values()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("problemStatus", pse.toString());
			map.put("value", pse.getValue());
			map.put("name", pse.getText());
			if (handle) {
				if (pse != ProblemStatusEnum.NEW && pse != ProblemStatusEnum.CLOSED) {
					list.add(map);
				}
			} else {
				list.add(map);
			}
		}
		return list;
	}

	@RequestMapping(value = "reportway", method = RequestMethod.GET)
	@ResponseBody
	public List<ReportWayEnum> getReportWay() {
		List<ReportWayEnum> list = new ArrayList<ReportWayEnum>();
		for (ReportWayEnum pwe : ReportWayEnum.values()) {
			list.add(pwe);
		}
		return list;
	}

	@RequestMapping(value = "devices", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> deviceList() {
		List<Object> list = new ArrayList<Object>();
		try {
			list = deviceService.getAllDeviceList();
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
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("problem", problemService.get(id));
		}
	}

	private User getSessionUser() {
		Session session = SecurityUtils.getSubject().getSession();
		return (User) session.getAttribute("user");
	}

}
