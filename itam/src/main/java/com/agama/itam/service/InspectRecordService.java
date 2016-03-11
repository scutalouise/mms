package com.agama.itam.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;

import com.agama.authority.entity.Organization;
import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.itam.mongo.domain.InspectRecord;

/**
 * @Description:巡检记录业务接口
 * @Author:佘朝军
 * @Since :2016年1月7日 下午3:26:35
 */
public interface InspectRecordService {

	public List<Map<String, Object>> getLatestFive(Organization org) throws Exception;

	public void saveInspectRecord(int total, int checkedTotal, int inexistendTotal, List<String> checkedList, List<String> uncheckedList,
			List<String> inexistendList, Organization org, User user) throws Exception;
	
	public List<InspectRecord> getList(Page<InspectRecord> inspectRecordPage,HttpServletRequest request);
	
	public Map<String, Object> queryListForPage(Criteria criteria, Sort sort, int pageNo, int pageSize);

}
