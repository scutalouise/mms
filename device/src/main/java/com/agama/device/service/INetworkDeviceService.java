package com.agama.device.service;

import java.util.List;
import java.util.Map;

import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.NetworkDevice;

public interface INetworkDeviceService extends IBaseService<NetworkDevice, Integer> {

	public NetworkDevice getNetworkDeviceByIdentifier(String identifier);

	public List<NetworkDevice> getNetworkDeviceByOrgId(int orgId);

	public List<Object> getNameAndIdentifierByOrgId(int orgId);

	public List<NetworkDevice> getAllList();

	/**
	 * @Description:根据设备编号获取设备详情信息
	 * @param identifier
	 *            设备编号
	 * @return List
	 * @Since :2016年1月18日 上午9:33:01
	 */
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier);

	/**
	 * @Description:查询已经领用的网络设备集合
	 * @param page
	 * @param organizationId
	 * @param normal
	 * @param unused
	 * @return
	 * @Since :2016年2月24日 上午9:38:36
	 */
	public Page<NetworkDevice> searchObtainNetWorkDeviceList(Page<NetworkDevice> page, String organizationId, StatusEnum status,
			DeviceUsedStateEnum deviceUsedState,User user);

	/**
	 * @Description:网络设备领用
	 * @param orgId
	 * @param netWorkDeviceIdList
	 * @Since :2016年2月24日 下午3:19:16
	 */
	public void netWorkDeviceObtain(Integer orgId,String type,
			List<Integer> netWorkDeviceIdList,User user);


	/**
	 * @Description:网络设备退回
	 * @param hostDeviceIdList
	 * @Since :2016年2月24日 下午3:20:03
	 */
	public void backNetWorkDevice(List<Integer> netWorkDeviceIdList);

	/**
	 * @Description:网络设备报废
	 * @param netWorkDeviceIdList
	 * @Since :2016年2月25日 上午10:10:38
	 */
	public void scrappedNetWorkDevice(List<Integer> netWorkDeviceIdList);

	/**
	 * @Description:网络设备审核
	 * @param type
	 * @param netWorkDeviceIdList
	 * @Since :2016年3月1日 下午3:33:21
	 */
	public void netWorkDeviceAudit(Integer type, List<Integer> netWorkDeviceIdList);

	public List<NetworkDevice> getListByDeviceUsedStateEnum(DeviceUsedStateEnum deviceUsedStateEnum);
	
	public List<NetworkDevice> getListByObtainUser(Integer userId);
	/**
	 * @Description:根据设备id集合改变设备的状态
	 * @param netWorkDeviceIdList
	 * @param putinstorage
	 * @Since :2016年3月10日 上午10:20:53
	 */
	public void updateDeviceUsedStateByDeviceIdList(
			List<Integer> netWorkDeviceIdList, DeviceUsedStateEnum deviceUsedState);
	
	public List<NetworkDevice> getListByQueryMap(Map<String, Object> map);

}
