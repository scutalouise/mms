package com.agama.itam.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.web.BaseController;
import com.agama.device.service.IDeviceService;
import com.agama.itam.domain.OutsideRepair;
import com.agama.itam.service.IOutsideRepairService;
import com.agama.tool.utils.date.DateUtils;

/**
 * @Description:外修记录控制层
 * @Author:佘朝军
 * @Since :2016年3月2日 下午1:30:16
 */
@Controller
@RequestMapping("maintenance/outsideRepair")
public class OutsideRepairController extends BaseController {

	@Autowired
	private IOutsideRepairService orService;
	@Autowired
	private IDeviceService deviceService;

	@RequestMapping(method = RequestMethod.GET)
	public String turnPage(Model model) {
		return "maintenance/outRepair";
	}

	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> view(HttpServletRequest request, String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page<OutsideRepair> page = getPage(request);
			page.setOrderBy("repairTime");
			page.setOrder("desc");
			page = orService.getListForPage(page, startTime, endTime);
			map = getEasyUIData(page);
		} catch (Exception e) {
			map.put("total", 0);
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "create/{ids}", method = RequestMethod.GET)
	public String toCreate(@PathVariable("ids") String ids, Model model) {
		model.addAttribute("outsideRepair", new OutsideRepair());
		model.addAttribute("ids", ids);
		model.addAttribute("action", "create");
		return "maintenance/outRepairAddForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(OutsideRepair outsideRepair, String ids) {
		try {
			if (outsideRepair != null) {
				User user = getSessionUser();
				outsideRepair.setRepairOpertor(user.getId());
				String[] idArray = ids.split(",");
				orService.saveByIdentifiers(outsideRepair, Arrays.asList(idArray));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "数据保存异常！";
		}
		return "success";
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "id") Integer id) {
		OutsideRepair or = orService.get(id);
		orService.deleteOutsideRepair(or);
		return "success";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable(value = "id") Integer id, Model model) {
		OutsideRepair o = orService.get(id);
		model.addAttribute("outsideRepair", o);
		model.addAttribute("action", "update");
		return "maintenance/outRepairForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute OutsideRepair outsideRepair) {
		User user = getSessionUser();
		outsideRepair.setReturnReceiver(user.getId());
		outsideRepair.setReturnTime(DateUtils.formatDateTime(new Date()));
		try {
			orService.updateRepairRecord(outsideRepair);
		} catch (Exception e) {
			e.printStackTrace();
			return "数据修改异常！";
		}
		return "success";
	}

	@RequestMapping(value = "device", method = RequestMethod.GET)
	public String turnDevicePage(Model model) {
		return "maintenance/outRepairDevice";
	}

	/**
	 * @Description:获取所有待外修的设备列表
	 * @return
	 * @Since :2016年3月2日 下午2:35:40
	 */
	@RequestMapping(value = "device/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDeviceList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Object> page = getPage(request);
		try {
			page = deviceService.getDeviceListByDeviceUsedStateEnum(page, DeviceUsedStateEnum.WAITEXTERNALMAINTENANCE);
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
	public void getOutsideRepair(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("outsideRepair", orService.get(id));
		}
	}

	private User getSessionUser() {
		Session session = SecurityUtils.getSubject().getSession();
		return (User) session.getAttribute("user");
	}

}
