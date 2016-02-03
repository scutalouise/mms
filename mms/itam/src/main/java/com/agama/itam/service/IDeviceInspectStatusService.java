package com.agama.itam.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

/**
 * @Description:设备巡检状态详情业务接口
 * @Author:佘朝军
 * @Since :2016年1月11日 下午2:31:29
 */
public interface IDeviceInspectStatusService {
	
	public void saveListByStatus(List<String> list, String status, ObjectId recordId) throws Exception;
	
	public List<Map<String,Object>> getDetailsByRecordAndStatus(ObjectId recordId, int status) throws Exception;

}
