package com.agama.itam.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.agama.aws.dao.MongoDao;
import com.agama.aws.helper.MongoQueryHelper;
import com.agama.device.domain.DeviceInventory;
import com.agama.device.domain.HostDevice;
import com.agama.device.service.IDeviceInventoryService;
import com.agama.device.service.IHostDeviceService;
import com.agama.itam.mongo.domain.DeviceInspectStatus;
import com.agama.itam.service.IDeviceInspectStatusService;

/**
 * @Description:设备巡检状态详情业务实现类
 * @Author:佘朝军
 * @Since :2016年1月11日 下午2:34:31
 */
@Service
public class DeviceInspectStatusServiceImpl implements IDeviceInspectStatusService {

	@Autowired
	private MongoDao mongoDao;
	@Autowired
	private IHostDeviceService hds;
	@Autowired
	private IDeviceInventoryService diService;

	@Override
	public void saveListByStatus(List<String> list, String status, ObjectId recordId) throws Exception {
		for (String identifier : list) {
			if (!StringUtils.isBlank(identifier)) {
				DeviceInspectStatus dis = new DeviceInspectStatus();
				dis.setIdentifier(identifier);
				dis.setInspectDeviceStatus(status);
				dis.setInspectRecordId(recordId);
				mongoDao.save(dis);
			}
		}

	}

	public List<Map<String, Object>> getDetailsByRecordAndStatus(ObjectId recordId, int status) throws Exception {
		@SuppressWarnings("rawtypes")
		Map<String, Collection> inMap = new HashMap<String, Collection>();
		Collection<Object> co = new ArrayList<Object>();
		switch (status) {
		case 0:
			co.add("已巡检");
			co.add("未巡检");
			break;
		case 1:
			co.add("已巡检");
			break;
		case 2:
			co.add("未巡检");
			break;
		case 3:
			co.add("非网点");
			break;
		default:
			break;
		}
		inMap.put("inspectDeviceStatus", co);
		Map<String, Object> eqMap = new HashMap<String, Object>();
		eqMap.put("inspectRecordId", recordId);
		Criteria c = MongoQueryHelper.createCriteria(null, null, eqMap, null, null, null, inMap, null);
		List<DeviceInspectStatus> list = mongoDao.queryListByCriteriaAndSort(c, null, DeviceInspectStatus.class);
		return parseDetailsByList(list);
	}

	private List<Map<String, Object>> parseDetailsByList(List<DeviceInspectStatus> disList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (DeviceInspectStatus dis : disList) {
			Map<String, Object> map = new HashMap<String, Object>();
			HostDevice hd = hds.getHostDeviceByIdentifier(dis.getIdentifier());
			if (hd != null) {
				map.put("name", hd.getName());
				DeviceInventory di = diService.getDeviceInventoryByPurchaseId(hd.getPurchaseId());
				map.put("deviceType", di.getFirstDeviceType().getName() + "-" + di.getSecondDeviceType().getName());
			} else {
				map.put("name", "未知设备");
				map.put("deviceType", "未知类型");
			}
			map.put("inspectedStatus", dis.getInspectDeviceStatus());
			map.put("identifier", dis.getIdentifier());
			list.add(map);	
		}
		return list;
	}

}
