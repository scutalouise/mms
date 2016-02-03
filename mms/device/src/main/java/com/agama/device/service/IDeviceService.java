package com.agama.device.service;

import java.util.List;
import java.util.Map;

import com.agama.common.enumbean.FirstDeviceType;

/**
 * @Description:总体设备操作接口
 * @Author:佘朝军
 * @Since :2016年1月15日 下午1:44:30
 */
public interface IDeviceService {

	public FirstDeviceType getFirstDeviceTypeByIdentifier(String identifier) throws Exception;

	public Object getDeviceByIdentifier(String identifier) throws Exception;
	
	public Map<String, String> getDeviceMapByIdentifier(String identifier) throws Exception;

	/**
	 * @Description:针对于手持机的接口业务，根据设备编号获取设备详细信息
	 * @param identifier
	 *            设备唯一编号
	 * @return Map
	 * @throws Exception
	 * @Since :2016年1月19日 上午10:58:40
	 */
	public Map<String, Object> getDeviceByIdentifierForHandset(String identifier) throws Exception;

	public List<Object> getDeviceListByOrgId(int orgId) throws Exception;
	
	public List<Object> getAllDeviceList() throws Exception;

}
