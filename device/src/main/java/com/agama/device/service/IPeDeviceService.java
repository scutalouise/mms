package com.agama.device.service;

import java.util.List;
import java.util.Map;

import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.PeDevice;

/**
 * @Description:动环设备业务逻辑接口
 * @Author:ranjunfeng
 * @Since :2016年1月25日 上午9:28:15
 */
public interface IPeDeviceService extends IBaseService<PeDevice, Integer> {
	/**
	 * @Description:根据采集设备的IP、设备类型、接口号获取动环设备对象
	 * @param ip
	 *            采集设备IP
	 * @param switchinput
	 *            设备类型
	 * @param index
	 *            接口号
	 * @return
	 * @Since :2016年1月25日 上午9:34:00
	 */
	public List<PeDevice> getPeDeviceByIpAndDeviceTypeAndIndex(String ip, DeviceType switchinput, Integer index);

	public PeDevice getPeDeviceByIdentifier(String identifier);

	/**
	 * 获取设备当前状态和数量
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getCurrentStateAndCount();

	public List<PeDevice> getPeDeviceByOrgId(int orgId);

	public List<Object> getNameAndIdentifierByOrgId(int orgId);

	public List<PeDevice> getAllList();

	/**
	 * @Description:根据设备编号获取设备详情信息
	 * @param identifier
	 *            设备编号
	 * @return List
	 * @Since :2016年1月18日 上午9:33:01
	 */
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier);

	/**
	 * @Description:查询已经领用的动环设备分页集合
	 * @param page
	 * @param organizationId
	 * @param status
	 * @param deviceUsedState
	 * @return
	 * @Since :2016年2月24日 上午9:44:13
	 */
	public Page<PeDevice> searchObtainPeDeviceList(Page<PeDevice> page,
			String organizationId, StatusEnum status, DeviceUsedStateEnum deviceUsedState,User user);


	/**
	 * @Description:领取动环设备
	 * @param orgId
	 * @param peDeviceIdList
	 * @Since :2016年2月24日 下午5:36:07
	 */
	public void peDeviceObtain(Integer orgId, String type,
			List<Integer> peDeviceIdList,User user);

	/**
	 * @Description:退回动环设备
	 * @param peDeviceIdList
	 * @Since :2016年2月24日 下午5:36:21
	 */
	public void backPeDevice(List<Integer> peDeviceIdList);

	/**
	 * @Description:动环设备报废
	 * @param peDeviceIdList
	 * @Since :2016年2月25日 上午10:36:04
	 */
	public void scrappedPeDevice(List<Integer> peDeviceIdList);

	/**
	 * @Description:动环设备审核
	 * @param type
	 * @param peDeviceIdList
	 * @Since :2016年3月1日 下午5:35:24
	 */
	public void peDeviceAudit(Integer type, List<Integer> peDeviceIdList);

	public List<PeDevice> getListByDeviceUsedStateEnum(DeviceUsedStateEnum deviceUsedStateEnum);
	
	public List<PeDevice> getListByObtainUser(Integer userId);
	public Long getInventoryCountBySecondDeviceType(Integer orgId,
			SecondDeviceType secondType);
	
	public List<PeDevice> getListByQueryMap(Map<String, Object> map);

	/**
	 * @Description:根据设备id集合修改设备使用状态
	 * @param peDeviceIdList
	 * @param badparts
	 * @Since :2016年3月10日 下午1:06:03
	 */
	public void updateDeviceUsedStateByDeviceIdList(
			List<Integer> peDeviceIdList, DeviceUsedStateEnum deviceUsedState);

}
