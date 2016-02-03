package com.agama.device.service;

import java.util.List;
import java.util.Map;

import com.agama.common.domain.StateEnum;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.HostDevice;

public interface IHostDeviceService extends IBaseService<HostDevice, Integer> {

	public void updateCurrentStatus(String identifier, StateEnum currentState);

	public HostDevice getHostDeviceByIdentifier(String identifier);

	public List<HostDevice> getHostDeviceByOrgId(int orgId);

	public List<Object> getNameAndIdentifierByOrgId(int orgId);

	public List<HostDevice> getAllList();

	/**
	 * @Description:根据设备编号获取主机设备详情信息
	 * @param identifier 设备编号
	 * @return List
	 * @Since :2016年1月18日 上午9:33:01
	 */
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier);

}
