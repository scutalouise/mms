package com.agama.itam.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.agama.authority.entity.Organization;
import com.agama.authority.service.IOrganizationService;
import com.agama.authority.service.IUserService;
import com.agama.aws.dao.MongoDao;
import com.agama.aws.helper.MongoQueryHelper;
import com.agama.common.dao.utils.Page;
import com.agama.itam.mongo.domain.InspectRecord;
import com.agama.itam.service.IDeviceInspectStatusService;
import com.agama.itam.service.InspectRecordService;
import com.agama.tool.utils.date.DateUtils;

/**
 * @Description:巡检记录业务实现类
 * @Author:佘朝军
 * @Since :2016年1月7日 下午3:35:21
 */
@Service
public class InspectRecordServiceImpl implements InspectRecordService {

	@Autowired
	private MongoDao mongoDao;
	@Autowired
	private IDeviceInspectStatusService diss;
	@Autowired
	private IOrganizationService ios;
	@Autowired
	private IUserService userService;

	@Override
	public List<Map<String, Object>> getLatestFive(Organization org) throws Exception {
		Page<InspectRecord> page = new Page<InspectRecord>();
		page.setPageNo(1);
		page.setPageSize(5);
		return parseLastFive(org);
	}

	public void saveInspectRecord(int total, int checkedTotal, int inexistendTotal, List<String> checkedList, List<String> uncheckedList,
			List<String> inexistendList, int orgId, int userId) throws Exception {
		InspectRecord ir = new InspectRecord();
		ir.setDeviceTotal(total);
		ir.setInexistendTotal(inexistendTotal);
		ir.setInspectedTotal(checkedTotal);
		ir.setOrgId(orgId);
		if (checkedTotal == total) {
			ir.setInspectStatus("合格");
		} else {
			ir.setInspectStatus("不合格");
		}
		ir.setUncheckedTotal(total - checkedTotal);
		ir.setUserId(userId);
		ir.setInspectTime(new Date());
		mongoDao.save(ir);
		ObjectId recordId = ir.getId();
		diss.saveListByStatus(checkedList, "已巡检", recordId);
		diss.saveListByStatus(uncheckedList, "未巡检", recordId);
		diss.saveListByStatus(inexistendList, "非网点", recordId);
	}

	private List<Map<String, Object>> parseLastFive(Organization org) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> eqMap = new HashMap<String, Object>();
		eqMap.put("orgId", org.getId());
		Criteria c = MongoQueryHelper.createCriteria(null, null, eqMap, null, null, null, null, null);
		Map<String, Direction> orderMap = new HashMap<String, Direction>();
		orderMap.put("inspectTime", Direction.DESC);
		Sort sort = MongoQueryHelper.createSort(orderMap);
		List<InspectRecord> irList = mongoDao.queryListByCriteriaAndSortForPage(c, sort, InspectRecord.class, 1, 5);
		for (InspectRecord ir : irList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", ir.getId().toString());
			map.put("time", DateUtils.formatDate(ir.getInspectTime()));
			map.put("status", ir.getInspectStatus());
			map.put("hallName", org.getOrgName());
			map.put("user", userService.get(ir.getUserId()).getName());
			map.put("deviceTotal", ir.getDeviceTotal());
			map.put("uncheckedTotal", ir.getUncheckedTotal());
			map.put("inspectedTotal", ir.getInspectedTotal());
			map.put("inexistendTotal", ir.getInexistendTotal());
			list.add(map);
		}
		return list;
	}

}
