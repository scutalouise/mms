package com.agama.itam.service;

import java.util.List;
import java.util.Map;

import com.agama.authority.entity.Organization;

/**
 * @Description:巡检记录业务接口
 * @Author:佘朝军
 * @Since :2016年1月7日 下午3:26:35
 */
public interface InspectRecordService {

	public List<Map<String, Object>> getLatestFive(Organization org) throws Exception;

	public void saveInspectRecord(int total, int checkedTotal, int inexistendTotal, List<String> checkedList, List<String> uncheckedList,
			List<String> inexistendList, int orgId, int userId) throws Exception;

}
