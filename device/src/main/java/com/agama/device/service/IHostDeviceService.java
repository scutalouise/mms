package com.agama.device.service;

import java.util.List;
import java.util.Map;

import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.HostDevice;

public interface IHostDeviceService extends IBaseService<HostDevice, Integer> {

	public void updateCurrentStatus(String identifier, StateEnum currentState);

	public HostDevice getHostDeviceByIdentifier(String identifier);

	public List<HostDevice> getHostDeviceByOrgId(int orgId);

	public List<Object> getNameAndIdentifierByOrgId(int orgId);

	public List<HostDevice> getAllList();

	/**
	 * @Description:根据设备编号获取设备详情信息
	 * @param identifier
	 *            设备编号
	 * @return List
	 * @Since :2016年1月18日 上午9:33:01
	 */
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier);

	/**
	 * @Description:查询已经领用的主机设备列表
	 * @param page
	 * @param organizationId
	 * @param status
	 * @return
	 * @Since :2016年2月23日 下午3:39:53
	 */
	public Page<HostDevice> searchObtainHostDeviceList(Page<HostDevice> page,
			String organizationId, StatusEnum status, DeviceUsedStateEnum deviceUsedState,User user);

	/**
	 * @Description:主机设备领用
	 * @param orgId
	 *            网点Id
	 * @param type
	 * @param hostDeviceIdList
	 *            领用的主机设备id集合
	 * @Since :2016年2月24日 下午1:57:05
	 */

	public void hostDeviceObtain(Integer orgId, String type,
			List<Integer> hostDeviceIdList,User user);

	/**
	 * @Description:主机设备退回
	 * @param hostDeviceIdList
	 * @Since :2016年2月24日 下午2:55:22
	 */
	public void backHostDevice(List<Integer> hostDeviceIdList);

	/**
	 * @Description:主机设备报废
	 * @param hostDeviceIdList
	 * @Since :2016年2月25日 上午9:52:03
	 */
	public void scrappedHostDevice(List<Integer> hostDeviceIdList);

	/**
	 * @Description:主机设备审核
	 * @param type
	 * @param hostDeviceIdList
	 * @Since :2016年3月1日 下午2:39:27
	 */
	public void hostDeviceAudit(Integer type, List<Integer> hostDeviceIdList);

	public List<HostDevice> getListByDeviceUsedStateEnum(DeviceUsedStateEnum deviceUsedStateEnum);
	
	public List<HostDevice> getListByObtainUser(Integer userId);
/**
 * @Description:根据设备的id集合修改设备状态
 * @param hostDeviceIdList
 * @param deviceUsedState
 * @Since :2016年3月10日 上午10:23:57
 */
	public void updateDeviceUsedStateByDeviceIdList(
			List<Integer> hostDeviceIdList, DeviceUsedStateEnum deviceUsedState);
	
	public List<HostDevice> getListByQueryMap(Map<String, Object> map);
}
