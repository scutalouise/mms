package com.agama.itam.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.authority.entity.Organization;
import com.agama.authority.entity.User;
import com.agama.authority.service.IOrganizationService;
import com.agama.authority.service.IUserService;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.ProblemStatusEnum;
import com.agama.common.enumbean.ReportWayEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.domain.HostDevice;
import com.agama.device.service.IDeviceService;
import com.agama.device.service.IHostDeviceService;
import com.agama.itam.domain.Problem;
import com.agama.itam.service.IDeviceInspectStatusService;
import com.agama.itam.service.InspectRecordService;
import com.agama.itam.service.IProblemService;
import com.agama.itam.service.IProblemTypeService;
import com.agama.itam.util.HandsetUtils;
import com.agama.tool.utils.PropertiesUtils;
import com.agama.tool.utils.security.Digests;
import com.agama.tool.utils.security.Encodes;

/**
 * @Description:控制类，提供与手持终端进行数据交互的外部接口
 * @Author:佘朝军
 * @Since :2015年12月18日 上午10:24:28
 */
@Controller
@RequestMapping(value = "handset")
public class HandsetController {

	@Autowired
	private InspectRecordService irs;
	@Autowired
	private IUserService userService;
	@Autowired
	private IOrganizationService orgService;
	@Autowired
	private IHostDeviceService hds;
	@Autowired
	private IDeviceInspectStatusService disService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IProblemTypeService problemTypeService;
	@Autowired
	private IProblemService problemService;

	// http://124.161.16.235:8085/itam/handset/signin?latitude=30.645509&longitude=104.047595&userName=admin&password=123456&appType=0&handsetNo=021601131752687
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> test(HttpServletRequest req, @RequestParam(value = "latitude") Double latitude,
			@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "appType") int appType,
			@RequestParam(value = "handsetNo") String handsetNo) {
		String orgCode = null;
		if (appType == 1) {
			orgCode = handsetNo;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Organization org = null;
		if (appType == 0) {
			HostDevice hd = hds.getHostDeviceByIdentifier(handsetNo);
			if (hd == null) {
				map.put("result", -101);
				map.put("message", "终端编号不存在！");
				return map;
			} else {
				org = orgService.get(hd.getOrganizationId());
			}
		} else {
			org = orgService.getOrganizationByOrgCode(orgCode);
			if (org == null) {
				map.put("result", -101);
				map.put("message", "网点编号不存在！");
				return map;
			}
		}
		// 经纬度的校验
		boolean position = HandsetUtils.coordinateCheck(latitude, longitude, org);
		if (position) {
			map.put("result", 0);
			map.put("message", "测试连接成功！");
		} else {
			map.put("result", -104);
			map.put("message", "操作失败！设备未在规定区域内使用");
		}
		return map;
	}

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> signin(HttpServletRequest req, @RequestParam(value = "latitude") Double latitude,
			@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "appType") int appType,
			@RequestParam(value = "handsetNo") String handsetNo, @RequestParam("userName") String userName, @RequestParam(value = "password") String password) {
		String orgCode = null;
		if (appType == 1) {
			orgCode = handsetNo;
		}
		Map<String, Object> map = loginCheck(latitude, longitude, handsetNo, orgCode, userName, password, appType);
		Integer code = (Integer) map.get("result");
		if (code == 0) {
			HttpSession s = req.getSession();
			s.setAttribute("name", userName);
			map.put("message", "登录成功！");
		}
		return map;
	}

	@RequestMapping(value = "/secondPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> secondPassword(HttpServletRequest req, @RequestParam(value = "latitude") Double latitude,
			@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "appType") int appType,
			@RequestParam(value = "handsetNo") String handsetNo, @RequestParam("userName") String userName, @RequestParam(value = "password") String password,
			@RequestParam(value = "secondPassword") String secondPassword) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer code = 0;
		String pwd = PropertiesUtils.getPropertiesValue("secondPassword", HandsetUtils.PROPERTIES_FILE_PATH);
		if (pwd.equals(secondPassword)) {
			map.put("message", "二级密码验证成功！");
		} else {
			code = -105;
			map.put("message", "二级密码不正确！");
		}
		map.put("result", code);
		return map;
	}

	@RequestMapping(value = "/halllist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getHallList(HttpServletRequest req, @RequestParam(value = "latitude") Double latitude,
			@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "appType") int appType,
			@RequestParam(value = "handsetNo") String handsetNo, @RequestParam("userName") String userName, @RequestParam(value = "password") String password) {
		String orgCode = null;
		if (appType == 1) {
			orgCode = handsetNo;
		}
		HttpSession s = req.getSession();
		String sn = String.valueOf(s.getAttribute("name"));
		Map<String, Object> map = checkToken(latitude, longitude, handsetNo, orgCode, userName, sn, appType);
		Integer code = (Integer) map.get("result");
		if (code == 0) {
			try {
				Organization org = null;
				if (appType == 0) {
					HostDevice hd = hds.getHostDeviceByIdentifier(handsetNo);
					org = orgService.get(hd.getOrganizationId());
				} else {
					org = orgService.getOrganizationByOrgCode(orgCode);
				}
				List<Object> list = deviceService.getDeviceListByOrgId(org.getId());
				map.put("message", "获取网点设备成功！");
				map.put("data", list);
			} catch (Exception e) {
				map.put("result", -500);
				map.put("message", "获取网点设备发生异常！");
				e.printStackTrace();
			}
		}
		return map;
	}

	@RequestMapping(value = "/batchinspection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveInspection(HttpServletRequest req, @RequestParam(value = "latitude") Double latitude,
			@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "appType") int appType,
			@RequestParam(value = "handsetNo") String handsetNo, @RequestParam("userName") String userName, @RequestParam(value = "password") String password,
			@RequestParam(value = "total") int total, @RequestParam(value = "inexistendTotal") int inexistendTotal,
			@RequestParam(value = "checkedTotal") int checkedTotal, @RequestParam(value = "checkedList") String checkedList,
			@RequestParam(value = "uncheckedList") String uncheckedList, @RequestParam(value = "inexistendList") String inexistendList) {
		String orgCode = null;
		if (appType == 1) {
			orgCode = handsetNo;
		}
		HttpSession s = req.getSession();
		String sn = String.valueOf(s.getAttribute("name"));
		Map<String, Object> map = checkToken(latitude, longitude, handsetNo, orgCode, userName, sn, appType);
		Integer code = (Integer) map.get("result");
		if (code == 0) {
			List<String> checked = Arrays.asList(checkedList.split(","));
			List<String> unchecked = Arrays.asList(uncheckedList.split(","));
			List<String> inexistend = Arrays.asList(inexistendList.split(","));
			try {
				Organization org = null;
				if (appType == 0) {
					HostDevice hd = hds.getHostDeviceByIdentifier(handsetNo);
					org = orgService.get(hd.getOrganizationId());
				} else {
					org = orgService.getOrganizationByOrgCode(orgCode);
				}
				User user = userService.getUser(userName);
				irs.saveInspectRecord(total, checkedTotal, inexistendTotal, checked, unchecked, inexistend, org.getId(), user.getId());
				map.put("message", "巡检数据保存成功！");
			} catch (Exception e) {
				map.put("result", -500);
				map.put("message", "巡检数据保存异常！");
				e.printStackTrace();
			}
		}
		return map;
	}

	@RequestMapping(value = "/repairType", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProblemType(HttpServletRequest req, @RequestParam(value = "latitude") Double latitude,
			@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "appType") int appType,
			@RequestParam(value = "handsetNo") String handsetNo, @RequestParam("userName") String userName, @RequestParam(value = "password") String password,
			@RequestParam(value = "identifier") String identifier) {
		String orgCode = null;
		if (appType == 1) {
			orgCode = handsetNo;
		}
		HttpSession s = req.getSession();
		String sn = String.valueOf(s.getAttribute("name"));
		Map<String, Object> map = checkToken(latitude, longitude, handsetNo, orgCode, userName, sn, appType);
		Integer code = (Integer) map.get("result");
		if (code == 0) {
			try {
				Object obj = deviceService.getDeviceByIdentifier(identifier);
				if (obj != null) {
					List<Map<String, Object>> list = problemTypeService.getListByIdentifierForHandset(identifier);
					map.put("message", "获取问题类型成功！");
					map.put("data", list);
				} else {
					map.put("message", "设备编号不存在！");
					map.put("result", -101);
				}
			} catch (Exception e) {
				map.put("message", "获取问题类型异常！");
				map.put("result", -500);
				e.printStackTrace();
			}
		}
		return map;
	}

	@RequestMapping(value = "/repair", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveRepairReport(HttpServletRequest req, @RequestParam(value = "latitude") Double latitude,
			@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "appType") int appType,
			@RequestParam(value = "handsetNo") String handsetNo, @RequestParam("userName") String userName, @RequestParam(value = "password") String password,
			@RequestParam(value = "identifier") String identifier, @RequestParam(value = "problemTypeId") int problemTypeId,
			@RequestParam(value = "description") String description) {
		String orgCode = null;
		if (appType == 1) {
			orgCode = handsetNo;
		}
		HttpSession s = req.getSession();
		String sn = String.valueOf(s.getAttribute("name"));
		Map<String, Object> map = checkToken(latitude, longitude, handsetNo, orgCode, userName, sn, appType);
		Integer code = (Integer) map.get("result");
		if (code == 0) {
			try {
				Object obj = deviceService.getDeviceByIdentifier(identifier);
				if (obj != null) {
					Problem problem = new Problem();
					problem.setDescription(description);
					problem.setEnableKnowledge(false);
					problem.setIdentifier(identifier);
					problem.setProblemTypeId(problemTypeId);
					problem.setRecordTime(new Date());
					User user = userService.getUser(userName);
					problem.setRecordUserId(user.getId());
					problem.setReportUser(user.getName());
					problem.setReportUserContact(user.getPhone());
					problem.setReportWay(ReportWayEnum.HANDSET);
					problem.setEnable(ProblemStatusEnum.NEW);
					problem.setStatus(StatusEnum.NORMAL);
					problemService.saveProblem(problem, user);
					map.put("message", "设备报修成功！");
				} else {
					map.put("message", "设备编号不存在！");
					map.put("result", -101);
				}
			} catch (Exception e) {
				map.put("message", "设备报修异常！");
				map.put("result", -500);
				e.printStackTrace();
			}
		}
		return map;

	}

	@RequestMapping(value = "/searchRecord", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchInspections(HttpServletRequest req, @RequestParam(value = "latitude") Double latitude,
			@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "appType") int appType,
			@RequestParam(value = "handsetNo") String handsetNo, @RequestParam("userName") String userName, @RequestParam(value = "password") String password) {
		String orgCode = null;
		if (appType == 1) {
			orgCode = handsetNo;
		}
		HttpSession s = req.getSession();
		String sn = String.valueOf(s.getAttribute("name"));
		Map<String, Object> map = checkToken(latitude, longitude, handsetNo, orgCode, userName, sn, appType);
		Integer code = (Integer) map.get("result");
		if (code == 0) {
			try {
				Organization org = null;
				if (appType == 0) {
					HostDevice hd = hds.getHostDeviceByIdentifier(handsetNo);
					org = orgService.get(hd.getOrganizationId());
				} else {
					org = orgService.getOrganizationByOrgCode(orgCode);
				}
				List<Map<String, Object>> list = irs.getLatestFive(org);
				map.put("data", list);
				map.put("message", "获取巡检记录成功！");
			} catch (Exception e) {
				map.put("result", -500);
				map.put("message", "查询数据出现异常！");
			}
		}
		return map;
	}

	@RequestMapping(value = "/searchInspectionDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getInspectedStatus(HttpServletRequest req, @RequestParam(value = "latitude") Double latitude,
			@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "appType") int appType,
			@RequestParam(value = "handsetNo") String handsetNo, @RequestParam("userName") String userName, @RequestParam(value = "password") String password,
			@RequestParam(value = "recordId") String recordId, @RequestParam(value = "status") int status) {
		String orgCode = null;
		if (appType == 1) {
			orgCode = handsetNo;
		}
		HttpSession s = req.getSession();
		String sn = String.valueOf(s.getAttribute("name"));
		Map<String, Object> map = checkToken(latitude, longitude, handsetNo, orgCode, userName, sn, appType);
		Integer code = (Integer) map.get("result");
		if (code == 0) {
			try {
				List<Map<String, Object>> list = disService.getDetailsByRecordAndStatus(new ObjectId(recordId), status);
				map.put("data", list);
				map.put("message", "获取巡检详情成功！");
			} catch (Exception e) {
				map.put("result", -500);
				map.put("message", "获取设备巡检详情异常！");
				e.printStackTrace();
			}
		}
		return map;
	}

	@RequestMapping(value = "/device", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDeviceByidentifier(HttpServletRequest req, @RequestParam(value = "latitude") Double latitude,
			@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "appType") int appType,
			@RequestParam(value = "handsetNo") String handsetNo, @RequestParam("userName") String userName, @RequestParam(value = "password") String password,
			@RequestParam(value = "identifier") String identifier) {
		String orgCode = null;
		if (appType == 1) {
			orgCode = handsetNo;
		}
		HttpSession s = req.getSession();
		String sn = String.valueOf(s.getAttribute("name"));
		Map<String, Object> map = checkToken(latitude, longitude, handsetNo, orgCode, userName, sn, appType);
		Integer code = (Integer) map.get("result");
		if (code == 0) {
			try {
				Map<String, Object> objMap = deviceService.getDeviceByIdentifierForHandset(identifier);
				if (objMap != null) {
					Integer managerId = (Integer) objMap.get("manager");
					if (managerId != null) {
						User u = userService.get(managerId);
						objMap.put("manager", u.getName());
					} else {
						objMap.put("manager", "");
					}
					objMap.put("deviceType",
							((FirstDeviceType) objMap.get("firstDeviceType")).getName() + "-" + ((SecondDeviceType) objMap.get("secondDeviceType")).getName());
					objMap.put("enable", ((EnabledStateEnum) objMap.get("enable")).getName());
					objMap.remove("firstDeviceType");
					objMap.remove("secondDeviceType");
					map.put("message", "获取设备详情成功！");
					map.put("data", objMap);
				} else {
					map.put("result", -101);
					map.put("message", "该设备编号不存在");
				}
			} catch (Exception e) {
				map.put("result", -500);
				map.put("message", "获取设备详细信息异常！");
				e.printStackTrace();
			}
		}
		return map;
	}

	private Map<String, Object> checkToken(Double latitude, Double longitude, String handsetNo, String orgCode, String userName, String sessionName, int appType) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 校验编号的存在性
		Organization org = null;
		if (appType == 0) {
			HostDevice hd = hds.getHostDeviceByIdentifier(handsetNo);
			if (hd == null) {
				map.put("result", -101);
				map.put("message", "终端编号不存在！");
				return map;
			} else {
				org = orgService.get(hd.getOrganizationId());
			}

		} else {
			org = orgService.getOrganizationByOrgCode(orgCode);
			if (org == null) {
				map.put("result", -101);
				map.put("message", "网点编号不存在！");
				return map;
			}
		}
		// 经纬度的校验
		boolean position = HandsetUtils.coordinateCheck(latitude, longitude, org);
		if (position) {
			// 账号信息的校验
			if (userName.equals(sessionName)) {
				orgCode = org.getOrgCode();
				map.put("result", 0);
			} else {
				map.put("result", -204);
				map.put("message", "用户未登陆或会话超时！请重新登录！");
			}
		} else {
			map.put("result", -104);
			map.put("message", "操作失败！设备未在规定区域内使用");
		}
		return map;
	}

	private Map<String, Object> loginCheck(Double latitude, Double longitude, String handsetNo, String orgCode, String userName, String password, int appType) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 校验编号的存在性
		Organization org = null;
		if (appType == 0) {
			HostDevice hd = hds.getHostDeviceByIdentifier(handsetNo);
			if (hd == null) {
				map.put("result", -101);
				map.put("message", "终端编号不存在！");
				return map;
			} else {
				org = orgService.get(hd.getOrganizationId());
			}

		} else {
			org = orgService.getOrganizationByOrgCode(orgCode);
			if (org == null) {
				map.put("result", -101);
				map.put("message", "网点编号不存在！");
				return map;
			}
		}
		// 经纬度的校验
		boolean position = HandsetUtils.coordinateCheck(latitude, longitude, org);
		if (position) {
			// 账号信息的校验
			User user = userService.getUser(userName);
			if (user != null
					&& user.getPassword().equals(
							Encodes.encodeHex(Digests.sha1(password.getBytes(), Encodes.decodeHex(user.getSalt()), IUserService.HASH_INTERATIONS)))) {
				map.put("result", 0);
			} else {
				map.put("result", -203);
				map.put("message", "账号或密码错误！");
			}
		} else {
			map.put("result", -104);
			map.put("message", "操作失败！设备未在规定区域内使用");
		}
		return map;
	}

}
