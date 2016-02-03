package com.agama.das.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.aws.utils.MongoBeanUtils;
import com.agama.das.model.entity.Asset;
import com.agama.das.service.AssetService;

/**
 * @Description:控制类，处理资产设备请求的各种方法
 * @Author:佘朝军
 * @Since :2015年11月25日 下午2:00:53
 */
@Log4j
@Controller
public class AssetController {

	@Autowired
	private AssetService assetService;

	@RequestMapping(value = "/asset", method = RequestMethod.POST)
	public String insertAsset(Asset asset) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("community", "public");
			map.put("contextEngineId", "9527");
			asset.setMetaData(map);
			assetService.insert(asset);
			map.put("message", "success");
		} catch (Exception e) {
			log.error("数据保存异常！", e);
			map.put("message", "failure");
			e.printStackTrace();
		}
		return "redirect:index";
	}

	@RequestMapping(value = "/asset/id/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deleteAsset(@PathVariable(value = "id") String id) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			assetService.removeOneByObjectId(new ObjectId(id));
			map.put("message", "success");
		} catch (Exception e) {
			log.error("数据删除异常！", e);
			map.put("message", "failure");
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/asset", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, String> updateAsset(Asset asset) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			assetService.update(asset);
			map.put("message", "success");
		} catch (Exception e) {
			log.error("更新数据异常！", e);
			map.put("message", "failure");
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/asset/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById(@PathVariable(value = "id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Asset asset = assetService.getOneByObjectId(new ObjectId(id));
			map = MongoBeanUtils.buildViewMapFromBean(asset, Asset.class);
		} catch (Exception e) {
			log.error("查询数据异常！", e);
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "/asset/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> findList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<Asset> assets = assetService.getAll();
			for (Asset asset : assets) {
				list.add(MongoBeanUtils.buildViewMapFromBean(asset, Asset.class));
			}
		} catch (Exception e) {
			log.error("查询数据列表异常！", e);
			e.printStackTrace();
		}
		return list;
	}

}
