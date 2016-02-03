package com.agama.das.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.das.model.entity.SystemConfig;
import com.agama.das.quartz.CustomTrigger;
import com.agama.das.service.SystemConfigService;
import com.agama.tool.utils.PropertiesUtils;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

/**
 * @Description:
 * @Author:佘朝军
 * @Since :2015年11月13日 下午4:06:17
 */
@Log4j
@Controller
@RequestMapping(value = "/config")
public class SystemConfigController {

	@Autowired
	private SystemConfigService scService;
	@Autowired
	private CustomTrigger customTrigger;

	@RequestMapping(value = "/properties/independence/value/{value}", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, String> saveIndependence(@PathVariable(value = "value") String value) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			PropertiesUtils.saveOrUpdateProperties("independence", value, "/local.properties");
			map.put("message", "配置修改成功！");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			map.put("message", "配置修改发生异常！");
		}
		return map;
	}

	@RequestMapping(value = "/properties/quartzCron/key/{key}/value/{value}", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, String> saveQuartzCron(@PathVariable(value = "value") int value, @PathVariable(value = "key") String key) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String minutes = String.valueOf(value * 60 * 1000);
			PropertiesUtils.saveOrUpdateProperties(key, minutes, "/local.properties");
			customTrigger.modifyTriggersByKey(key, value);
			map.put("message", "success");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			map.put("message", "配置修改发生异常！");
		}
		return map;
	}

	@RequestMapping(value = "/properties/keys/{keys}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getProperties(@PathVariable(value = "keys") List<String> keys) {
		Map<String, String> map = new HashMap<String, String>();
		String path = "/local.properties";
		for (String key : keys) {
			String value = PropertiesUtils.getPropertiesValue(key, path);
			map.put(key, value);
		}
		return map;
	}

	@RequestMapping(value = "/properties/quartzCron/key/{key}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Integer> getQuartzCronProperties(@PathVariable(value = "key") String key) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String path = "/local.properties";
		String value = PropertiesUtils.getPropertiesValue(key, path);
		int minute = Integer.parseInt(value) / (1000 * 60);
		map.put(key, minute);
		return map;
	}

	@RequestMapping(value = "/systemConfig/systemCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveSystemCode(@RequestParam(value = "systemCode") String systemCode) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			SystemConfig systemConfig = scService.getFirst();
			if (systemConfig != null) {
				systemConfig.setSystemCode(systemCode);
				scService.update(systemConfig);
			} else {
				systemConfig = new SystemConfig();
				systemConfig.setSystemCode(systemCode);
				scService.insert(systemConfig);
			}
			map.put("message", "保存成功！");
		} catch (Exception e) {
			map.put("message", "数据采集器编码保存异常！");
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/systemConfig/centerAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveCenterAddress(@RequestParam(value = "centerAddress") String centerAddress,
			@RequestParam(value = "independence") boolean independence) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (independence) {
				SystemConfig systemConfig = scService.getFirst();
				if (systemConfig != null) {
					systemConfig.setCenterAddress(centerAddress);
					scService.update(systemConfig);
				} else {
					systemConfig = new SystemConfig();
					systemConfig.setCenterAddress(centerAddress);
					scService.insert(systemConfig);
				}
				map.put("message", "保存成功！");
			} else {
				HttpRequest req = HttpRequest.get(centerAddress + "/test");
				int code = req.code();
				String body = req.body();
				if (code == 200 && "0".equals(body)) {
					SystemConfig systemConfig = scService.getFirst();
					if (systemConfig != null) {
						systemConfig.setCenterAddress(centerAddress);
						scService.update(systemConfig);
					} else {
						systemConfig = new SystemConfig();
						systemConfig.setCenterAddress(centerAddress);
						scService.insert(systemConfig);
					}
					map.put("message", "保存成功！");
				} else {
					map.put("message", "连接失败！请检查网络连接，或中心地址是否正确！");
				}
			}
		} catch (HttpRequestException e) {
			map.put("message", "无法识别的访问地址！");
			e.printStackTrace();
		} catch (Exception e) {
			map.put("message", "管理中心地址保存异常！");
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/systemConfig", method = RequestMethod.GET)
	@ResponseBody
	public SystemConfig getSystemConfig() {
		SystemConfig sc = null;
		try {
			sc = scService.getFirst();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return sc;
	}

}
