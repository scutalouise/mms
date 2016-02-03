package com.agama.das.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agama.aws.utils.MongoBeanUtils;
import com.agama.das.model.entity.InspectedResult;
import com.agama.das.model.entity.SystemConfig;
import com.agama.das.service.AssetService;
import com.agama.das.service.InspectedResultService;
import com.agama.das.service.SystemConfigService;
import com.alibaba.fastjson.JSONArray;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * @Description:用于提供数据交互操作方法的类
 * @Author:佘朝军
 * @Since :2015年11月26日 下午1:36:26
 */
@Log4j
@Component
public class DataInteractHelper {

	@Autowired
	private SystemConfigService systemConfigService;
	@Autowired
	private AssetService assetService;
	@Autowired
	private InspectedResultService irs;

	/**
	 * @Description:根据不同的系统形态进行不同任务操作功能的支配
	 * @param independence
	 *            系统独立性
	 * @Since :2015年11月26日 下午1:55:29
	 */
	public void controlTask(boolean independence) {
		if (!independence) {
			SystemConfig sc = systemConfigService.getFirst();
			executeToRemote(sc);
		} else {
			executeAtLocal();
		}
	}

	/**
	 * @Description:与远程管理中心进行数据交互
	 * @param systemConfig
	 *            数据采集器系统配置
	 * @Since :2015年11月26日 下午1:56:25
	 */
	public void executeToRemote(SystemConfig systemConfig) {
		boolean hasGetData = getAssetsFromRemote(systemConfig);
		if (hasGetData) {
			pushData(systemConfig);
		}
	}

	/**
	 * @Description:在本地进行数据处理
	 * 
	 * @Since :2015年11月26日 下午1:57:07
	 */
	public void executeAtLocal() {
		// TODO
	}

	/**
	 * @Description:从远程中心抓取资产设备数据
	 * @param systemConfig
	 *            采集器系统配置
	 * @return boolean 数据是否抓取成功
	 * @Since :2015年11月27日 下午5:37:41
	 */
	private boolean getAssetsFromRemote(SystemConfig systemConfig) {
		String url = systemConfig.getCenterAddress() + "/deviceInfo/dasId/" + systemConfig.getSystemCode() + "/lastDataGetTime/"
				+ systemConfig.getLastDataGetTime();
		HttpRequest req = HttpRequest.get(url);
		if (req.ok()) {
			String body = req.body();
			systemConfig.setConnectCenter(req.ok());
			systemConfig.setLastDataGetTime(new Date().getTime());
			systemConfigService.update(systemConfig);
			if (body != null && !"".equals(body) && !"[]".equals(body)) {
				saveData(body);
			}
		}
		return req.ok();
	}

	/**
	 * @Description:将抓取到的数据进行保存，并删除所有原有数据
	 * @param result
	 *            抓取到的数据字符串
	 * @Since :2015年11月27日 下午5:38:37
	 */
	@SuppressWarnings("unchecked")
	private void saveData(String result) {
		try {
			JSONArray jsonArray = JSONArray.parseArray(result);
			Map<String, Object>[] mapArray = (Map<String, Object>[]) jsonArray.toArray();
			List<Map<String, Object>> assets = Arrays.asList(mapArray);
			assetService.removeAll();
			assetService.saveByAssetsList(assets);
		} catch (Exception e) {
			log.error("数据格式转换异常！", e);
			e.printStackTrace();
		}
	}

	/**
	 * @Description:向远端推送本地巡检数据
	 * @param systemConfig
	 *            采集器系统配置
	 * @Since :2015年11月27日 下午5:39:47
	 */
	private void pushData(SystemConfig systemConfig) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<InspectedResult> results = irs.getAll();
		for (InspectedResult result : results) {
			try {
				list.add(MongoBeanUtils.buildViewMapFromBean(result, InspectedResult.class));
			} catch (Exception e) {
				log.error("封装设备id为：" + result.getIdentifier() + "的数据异常！", e);
				results.remove(result);
				e.printStackTrace();
			}
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("data", JSONArray.toJSONString(list));
		HttpRequest req = HttpRequest.post(systemConfig.getCenterAddress() + "/inspectionData").form(map);
		// 如果推送成功，则移除本地已被成功推送出去的数据
		if (req.ok()) {
			irs.removeByList(results);
		}
	}

}
