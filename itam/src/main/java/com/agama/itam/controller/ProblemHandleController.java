package com.agama.itam.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.User;
import com.agama.authority.service.IUserRoleService;
import com.agama.aws.utils.MongoBeanUtils;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.ProblemStatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.service.IDeviceService;
import com.agama.itam.domain.Problem;
import com.agama.itam.domain.ProblemType;
import com.agama.itam.mongo.domain.ProblemHandle;
import com.agama.itam.service.IProblemHandleService;
import com.agama.itam.service.IProblemService;
import com.agama.itam.service.IProblemTypeService;

/**
 * @Description:
 * @Author:佘朝军
 * @Since :2016年1月27日 上午9:07:01
 */
@Controller
@RequestMapping("maintenance/handling")
public class ProblemHandleController extends BaseController {

	@Autowired
	private IProblemService problemService;
	@Autowired
	private IUserRoleService urService;
	@Autowired
	private IProblemHandleService phService;
	@Autowired
	private IProblemTypeService ptService;
	@Autowired
	private IDeviceService deviceService;

	@RequestMapping(method = RequestMethod.GET)
	public String turnPage(Model model) {
		return "maintenance/handling";
	}

	@RequestMapping(value = "problem", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> view(HttpServletRequest request, String problemCode, Integer problemTypeId, String enable, String recordTime,
			String recordEndTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page<Problem> page = getPage(request);
			User user = getSessionUser();
			page = problemService.searchListForHandling(problemCode, problemTypeId, user.getId(), enable, recordTime, recordEndTime, page, "handle");
			map = getEasyUIData(page);
		} catch (Exception e) {
			map.put("total", 0);
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/create/problemId/{problemId}", method = RequestMethod.GET)
	public String toCreate(Model model, @PathVariable("problemId") Long id) {
		Problem problem = problemService.get(id);
		ProblemType pt = ptService.get(problem.getProblemTypeId());
		model.addAttribute("problem", new ProblemHandle());
		model.addAttribute("problem", problem);
		model.addAttribute("problemType", pt);
		model.addAttribute("action", "create");
		return "maintenance/handlingForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(ProblemHandle problemHandle, String change, String changeDeviceIdentifier) {
		if (problemHandle != null) {
			problemHandle.setHandleTime(new Date());
			User user = getSessionUser();
			problemHandle.setHandleUserId(user.getId());
			problemHandle.setHandleUserName(user.getName());
			try {
				Problem problem = problemService.get(problemHandle.getProblemId());
				problem.setEnable(problemHandle.getEnable());
				if (problem.getEnable().equals(ProblemStatusEnum.RESOLVED)) {
					problem.setResolveTime(new Date());
				}
				if (problemHandle.getEnable().equals(ProblemStatusEnum.RESOLVED) && "true".equals(change)) {
					if (changeDeviceIdentifier == null) {
						return "换修必须要选择新更换的设备！";
					} else {
						problemService.updateChangeDeviceRepair(problem, user, changeDeviceIdentifier);
					}
				} else {
					problemService.update(problem);
				}
				phService.saveProblemHandle(problemHandle);//mongodb数据保存，没有事务管理
			} catch (Exception e) {
				e.printStackTrace();
				return "提交数据异常！请联系相关技术人员！";
			}
		}
		return "success";
	}

	@RequestMapping(value = "list/problemId/{problemId}", method = RequestMethod.GET)
	public String toListPage(@PathVariable("problemId") Long problemId, Model model) {
		model.addAttribute("problemId", problemId);
		return "maintenance/handleHistory";
	}
	
	@RequestMapping(value = "historyList/problemId/{problemId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> findListByProblemId(@PathVariable("problemId") Long problemId) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			List<ProblemHandle> phList  = phService.findListByProblemId(problemId);
			for (ProblemHandle problemHandle : phList) {
				Map<String,Object> map = MongoBeanUtils.buildViewMapFromBean(problemHandle, ProblemHandle.class);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "obtainUser/devices", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> getDeviceListByUserId() {
		User user = getSessionUser();
		return deviceService.getDeviceListByObtainUser(user.getId());
	}
	
	private User getSessionUser() {
		Session session = SecurityUtils.getSubject().getSession();
		return (User) session.getAttribute("user");
	}

}
