package com.agama.itam.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.agama.authority.service.IUserRoleService;
import com.agama.authority.service.IUserService;
import com.agama.authority.utils.ServletUtils;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.MaintainWayEnum;
import com.agama.common.enumbean.ProblemStatusEnum;
import com.agama.common.enumbean.ReportWayEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.service.IDeviceService;
import com.agama.device.service.IUserAdminOrgService;
import com.agama.device.service.IUserMaintainOrgService;
import com.agama.itam.domain.Problem;
import com.agama.itam.service.IProblemService;
import com.alibaba.fastjson.JSONArray;

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
	@Autowired
	private IUserRoleService urService;
	@Autowired
	private IUserAdminOrgService uaoService;
	@Autowired
	private IUserMaintainOrgService umoService;

	@RequestMapping(method = RequestMethod.GET)
	public String turnPage(Model model) {
		return "maintenance/problem";
	}

	// @RequiresPermissions("maintenance:problem:view")
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> view(HttpServletRequest request, String problemCode, Integer problemTypeId, String enable, String recordTime,
			String recordEndTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page<Problem> page = getPage(request);
			page = problemService.searchListForHandling(problemCode, problemTypeId, null, enable, recordTime, recordEndTime, page, "problem");
			map = getEasyUIData(page);
		} catch (Exception e) {
			map.put("total", 0);
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "details/{id}", method = RequestMethod.GET)
	public String getDetails(@PathVariable("id") Long id, Model model){
		try {
			Problem p = problemService.getDetailsById(id);
			model.addAttribute("problem", p);
			model.addAttribute("message", "success");
		} catch (Exception e) {
			model.addAttribute("problem", new Problem());
			model.addAttribute("message", "failure");
			e.printStackTrace();
		}
		return "maintenance/problemDetails";
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "receive", method = RequestMethod.POST)
	@ResponseBody
	public String createAlarmProblem(HttpServletRequest request) {
		try {
			String data=request.getParameter("data");
			JSONArray jsonArray = JSONArray.parseArray(data);
			Map<String, String>[] mapArray = (Map<String, String>[]) jsonArray.toArray();
			List<Map<String, String>> list = Arrays.asList(mapArray);
			problemService.saveAlarmProblem(list);
		} catch (Exception e) {
			e.printStackTrace();
			return "数据保存失败！系统发生异常！";
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
		model.addAttribute("problem", pt);
		model.addAttribute("action", "update");
		return "maintenance/problemForm";
	}

	// @RequiresPermissions("maintenance:problem:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute Problem problem) {
		User user = getSessionUser();
		try {
			problemService.updateProblem(problem, user);
		} catch (Exception e) {
			e.printStackTrace();
			return "failure";
		}
		return "success";
	}

	@RequestMapping(value = "assign/{id}", method = RequestMethod.GET)
	public String toAssign(@PathVariable(value = "id") Long id, Model model) {
		Problem pt = problemService.get(id);
		model.addAttribute("problem", pt);
		model.addAttribute("action", "update");
		return "maintenance/problemAssign";
	}
	
	/**
	 * @Description:分配问题后发送短信
	 * @param id 问题记录id
	 * @return
	 * @Since :2016年2月18日 下午5:16:31
	 */
	@RequestMapping(value = "sendMessage/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String sendMessage(@PathVariable(value = "id") Long id) {
		Problem pt = problemService.get(id);
		User user = userService.get(pt.getResolveUserId());
		if (!StringUtils.isBlank(user.getPhone())) {
			problemService.sendPhoneMessage(pt, user);
		}		
		return "success";
	}
	
	@RequestMapping(value = "shutdown/{id}", method = RequestMethod.GET)
	public String toShutdown(@PathVariable(value = "id") Long id, Model model) {
		model.addAttribute("id", id);
		return "maintenance/shutResolve";
	}
	
	@RequestMapping(value = "shutdown", method = RequestMethod.POST)
	@ResponseBody
	public String shutdown(@ModelAttribute Problem problem, Integer classificationId) {
		User user = getSessionUser();
		try {
			if (problem.isEnableKnowledge() && classificationId == null) {
				return "没用选择知识库分类！";
			} else {
				problemService.shutdown(problem, user, classificationId);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "关闭问题发生异常！";
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
			map.put("value", " ");
			map.put("name", "—全部—");
			list.add(map);
		}
		for (ProblemStatusEnum pse : ProblemStatusEnum.values()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("problemStatus", pse.toString());
			map.put("value", pse.getValue());
			map.put("name", pse.getText());
			if (handle) {
				if (pse != ProblemStatusEnum.NEW && pse != ProblemStatusEnum.CLOSED 
						&& pse != ProblemStatusEnum.CALLBACK && pse != ProblemStatusEnum.RESOLVED) {
					list.add(map);
				}
			} else {
				list.add(map);
			}
		}
		return list;
	}

	@RequestMapping(value = "resolveUser/identifier/{identifier}", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getResolveUser(@PathVariable("identifier") String identifier){
		List<User> list = new ArrayList<User>();
		try {
			Map<String,String> deviceMap = deviceService.getDeviceMapByIdentifier(identifier);
			MaintainWayEnum maintenWay = MaintainWayEnum.valueOf(deviceMap.get("maintainWay")); 
			 if (maintenWay.equals(MaintainWayEnum.INNER)) { 
				 String orgIdStr = deviceMap.get("organizationId");
				 if (StringUtils.isNotBlank(orgIdStr)) {
					 list = uaoService.getUserListByOrgId(Integer.parseInt(orgIdStr));		
				}
			 } else {
				 String maintainOrgIdStr = deviceMap.get("maintainOrgId");
				 if (StringUtils.isNotBlank(maintainOrgIdStr)) {
					 list = umoService.getUserListByOrgId(Integer.parseInt(maintainOrgIdStr));
				}
			 }
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
	public Map<String, Object> deviceList(HttpServletRequest request) {
		Page<Object> page = getPage(request);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> queryMap = ServletUtils.getParametersStartingWith(request, "filter_");
			page = deviceService.getPageListByQueryMap(page, queryMap);
			map = getEasyUIData(page);
		} catch (Exception e) {
			map.put("total", 0);
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @Description:在处理修改方法之前，根据id加载数据库记录信息
	 * @param id
	 * @param model
	 * @Since :2016年1月21日 上午11:36:38
	 */
	@ModelAttribute
	public void getProblem(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("problem", problemService.get(id));
		}
	}

	private User getSessionUser() {
		Session session = SecurityUtils.getSubject().getSession();
		return (User) session.getAttribute("user");
	}

}
