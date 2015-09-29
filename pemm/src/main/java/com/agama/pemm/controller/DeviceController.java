package com.agama.pemm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.agama.authority.common.web.BaseController;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.domain.Device;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IDeviceOperationService;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.service.IGitInfoService;
import com.agama.pemm.utils.DeviceListBean;
import com.agama.pemm.utils.SNMPUtil;

/**
 * @Author:ranjunfeng
 * @Since :2015年9月17日 下午5:12:04
 * @Description:动换设备控制层
 */
@Controller
@RequestMapping("device")
@SessionAttributes("device")
public class DeviceController extends BaseController {
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IGitInfoService gitInfoService;
	@Autowired
	private IDeviceOperationService deviceOperationService;

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "device/deviceList";
	}

	@RequiresPermissions("sys:device:view")
	@RequestMapping(value = "json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getData(Integer areaInfoId,
			HttpServletRequest request) {
		Page<Device> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(request);
		page = deviceService.searchListByAreaInfoId(areaInfoId, page, filters);
		return getEasyUIData(page);

	}

	@RequiresPermissions("sys:device:add")
	@RequestMapping(value = "scan", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> scan() {
		int count = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> deviceMap = null;
		List<Object> list = new ArrayList<Object>();
		List<Map<String, Object>> deviceIndexList = new ArrayList<Map<String, Object>>();
		List<GitInfo> gitInfoList = gitInfoService.getListByStatus(0);

		SNMPUtil snmp = new SNMPUtil();

		for (GitInfo gitInfo : gitInfoList) {

			deviceMap = new HashMap<String, Object>();
			try {
				Map<String, String> resultMap = snmp.walkByGetNext(
						gitInfo.getIp(), "1.3.6.1.4.1.34651.3.1");
				String ups1Status = resultMap.get("1.3.6.1.4.1.34651.3.1.21.0");
				String ups2Status = resultMap.get("1.3.6.1.4.1.34651.3.1.22.0");

				if (ups1Status != null && ups1Status.trim() != "-1") {
					Device device = deviceService
							.getListByGitInfoIdAndDeviceTypeAndIndex(
									gitInfo.getId(), DeviceType.UPS, 1);
					if (device == null) {
						count++;
						deviceMap.put("ups1", 1);
					}
				}
				if (ups2Status != null && ups2Status.trim() != "-1") {
					Device device = deviceService
							.getListByGitInfoIdAndDeviceTypeAndIndex(
									gitInfo.getId(), DeviceType.UPS, 2);
					if (device == null) {
						count++;
						deviceMap.put("ups2", 2);
					}
				}
				if (resultMap.size() > 0) {
					list.add(gitInfo.getId());
				}
				deviceIndexList.add(deviceMap);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		map.put("gitInfoIdList", list);
		map.put("deviceIndexList", deviceIndexList);
		map.put("count", count);

		return map;

	}

	@RequiresPermissions("sys:device:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(
			@RequestParam("gitInfoIdList[]") List<Integer> gitInfoIdList,
			DeviceListBean deviceListBean) {
		deviceService.addDeviceForScan(gitInfoIdList,
				deviceListBean.getDeviceIndexList());
		return "success";
	}

	@RequiresPermissions("sys:device:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		Device device = deviceService.get(id);
		model.addAttribute("device", device);

		model.addAttribute("action", "update");
		return "device/deviceForm";

	}

	@RequiresPermissions("sys:device:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid Device device, Model model) {
		device.setStatus(0);
		deviceService.update(device);
		return "success";

	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("sys:device:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(String ids) {
		deviceService.updateStatusByIds(ids);
		return "success";

	}

	@RequiresPermissions(value = { "sys:device:upsClose",
			"sys:device:upsDischarge" }, logical = Logical.OR)
	@RequestMapping(value = "upsOperation", method = RequestMethod.GET)
	@ResponseBody
	public String upsOperation(Integer gitInfoId, String oidStr,
			Integer instruction) {

		deviceOperationService.upsOperation(gitInfoId, oidStr, instruction);

		return "success";

	}

}
