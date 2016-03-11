package com.agama.itam.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.agama.authority.entity.Organization;
import com.agama.authority.entity.User;
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
	private IUserService userService;
	@Autowired
	private IOrganizationService organizationService;

	@Override
	public List<Map<String, Object>> getLatestFive(Organization org) throws Exception {
		Page<InspectRecord> page = new Page<InspectRecord>();
		page.setPageNo(1);
		page.setPageSize(5);
		return parseLastFive(org);
	}

	public void saveInspectRecord(int total, int checkedTotal, int inexistendTotal, List<String> checkedList, List<String> uncheckedList,
			List<String> inexistendList, Organization org, User user) throws Exception {
		InspectRecord ir = new InspectRecord();
		ir.setDeviceTotal(total);
		ir.setInexistendTotal(inexistendTotal);
		ir.setInspectedTotal(checkedTotal);
		ir.setOrgId(org.getId());
		ir.setOrgName(org.getOrgName());
		if (checkedTotal == total) {
			ir.setInspectStatus("合格");
		} else {
			ir.setInspectStatus("不合格");
		}
		ir.setUncheckedTotal(total - checkedTotal);
		ir.setUserId(user.getId());
		ir.setUserName(user.getName());
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
	
	public Map<String, Object> queryListForPage(Criteria criteria, Sort sort, int pageNo, int pageSize) {
		Map<String,Object> map=new HashMap<String, Object>();
		List<InspectRecord> list = mongoDao.queryListByCriteriaAndSortForPage(criteria, sort, InspectRecord.class, pageNo, pageSize);
		long total = mongoDao.queryCountByCriteria(criteria, InspectRecord.class);
		map.put("rows", list);
		map.put("total", total);
		return map;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<InspectRecord> getList(Page<InspectRecord> inspectRecordPage,HttpServletRequest request) {
		//巡检网点查询
		Map<String, Collection> inMap = new HashMap<String, Collection>();
		List<Integer> list = new ArrayList<Integer>();
		String ids="";
		String	orgId = request.getParameter("orgId");
		if (orgId != null && orgId !="") {
			ids = organizationService.getOrganizationIdStrById(Integer.parseInt(orgId));
			String[] arrayStr = ids.split(",");
			Integer[] arrayInt= new Integer[arrayStr.length];
			for(int i=0;i<arrayStr.length;i++){  
				arrayInt[i]=Integer.parseInt(arrayStr[i]);
			}
			list = Arrays.asList(arrayInt);
			inMap.put("orgId", list);
		}
		
		//巡检人查询
		Map<String, String> regexMap = new HashMap<String, String>();
		String name=request.getParameter("name");
		if (name != null && name !="") {
		    regexMap.put("name", name);
		}
		
		//巡检状态查询
		Map<String, Object> eqMap = new HashMap<String, Object>();
		String status=request.getParameter("status");
		if (status != null && status !="") {
			eqMap.put("inspectStatus", status.equals("qualified") ? "合格" : "不合格");
		}
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		//巡检日期查询 gte
		Map<String, Object> gteMap = new HashMap<String, Object>();
		String startDate=request.getParameter("startDate");
		Date inspectTimeStart = null;
		if (startDate != null && startDate !="") {
			try {
				inspectTimeStart = sdf.parse(startDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			gteMap.put("inspectTime", inspectTimeStart);
		}
		
		//巡检日期查询 lte
		Map<String, Object> lteMap = new HashMap<String, Object>();
		String endDate=request.getParameter("endDate");
		Date inspectTimeEnd = null;
		if (endDate != null && endDate !="") {
			try {
				inspectTimeEnd = sdf.parse(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			lteMap.put("inspectTime", inspectTimeEnd);
		}
		
		Criteria c = MongoQueryHelper.createCriteria(null, null, eqMap, gteMap, lteMap, regexMap, inMap, null);
		Map<String, Direction> orderMap = new HashMap<String, Direction>();
		orderMap.put("inspectTime", Direction.DESC);
		Sort sort = MongoQueryHelper.createSort(orderMap);
		return mongoDao.queryListByCriteriaAndSortForPage(c, sort, InspectRecord.class, inspectRecordPage.getPageNo(), inspectRecordPage.getPageSize());
	}

}
