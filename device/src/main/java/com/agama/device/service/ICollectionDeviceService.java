package com.agama.device.service;

import java.util.List;
import java.util.Map;

import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.CollectionDevice;

public interface ICollectionDeviceService extends
		IBaseService<CollectionDevice, Integer> {
	/**
	 * @Description:更具状态获取采集设备集合
	 * @param status
	 *            状态
	 * @return
	 * @Since :2016年1月20日 下午1:47:44
	 */
	public List<CollectionDevice> getListByStatus(StatusEnum status);

	public CollectionDevice getCollectionDeviceByIdentifier(String identifier);

	public List<CollectionDevice> getCollectionDeviceByOrgId(int orgId);

	public List<Object> getNameAndIdentifierByOrgId(int orgId);

	public List<CollectionDevice> getAllList();

	/**
	 * @Description:根据设备编号获取设备详情信息
	 * @param identifier
	 *            设备编号
	 * @return List
	 * @Since :2016年1月18日 上午9:33:01
	 */
	public Map<String, Object> getDetailByIdentifierForHandset(String identifier);

	/**
	 * @Description:查询已经领用的采集设备列表
	 * @param page
	 * @param organizationId
	 * @param normal
	 * @param deviceUsedState
	 * @return
	 * @Since :2016年2月24日 下午5:09:17
	 */

	public Page<CollectionDevice> searchObtainCollectionDeviceList(
			Page<CollectionDevice> page, String organizationId,
			StatusEnum normal, DeviceUsedStateEnum deviceUsedState, User user);


	/**
	 * @Description:采集设备领用
	 * @param orgId
	 *            网点Id
	 * @param collectionDeviceIdList
	 *            领用的采集设备id集合
	 * @Since :2016年2月24日 下午1:57:05
	 */

	public void collectionDeviceObtain(Integer orgId, String type,
			List<Integer> collectionDeviceIdList, User user);

	/**
	 * @Description:采集设备退回
	 * @param collectionDeviceIdList
	 * @Since :2016年2月24日 下午2:55:22
	 */
	public void backCollectionDevice(List<Integer> collectionDeviceIdList);

	/**
	 * @Description:采集设备报废
	 * @param collectionDeviceIdList
	 * @Since :2016年2月25日 上午10:28:57
	 */
	public void scrappedCollectionDevice(List<Integer> collectionDeviceIdList);

	/**
	 * @Description:采集设备审核
	 * @param type
	 * @param collectionDeviceIdList
	 * @Since :2016年3月1日 下午4:34:59
	 */
	public void collectionDeviceAudit(Integer type, List<Integer> collectionDeviceIdList);

	public List<CollectionDevice> getListByDeviceUsedStateEnum(DeviceUsedStateEnum deviceUsedStateEnum);
    /**
     * @Description:根据网点id获取已经领取的采集设备集合
     * @param organizationId
     * @return
     * @Since :2016年3月4日 下午3:53:12
     */
	public List<CollectionDevice> getCollectionListByOrgId(
			Integer organizationId);
	/**
	 * @Description:根据领用人id获取领用人所属网点的采集设备集合
	 * @param obtainUserId
	 * @return
	 * @Since :2016年3月4日 下午4:50:07
	 */
	public List<CollectionDevice> getCollectionListByObtainUserId(
			Integer obtainUserId);
	
	public List<CollectionDevice> getListByObtainUser(Integer userId);
	/**
	 * @Description:根据设备id集合修改设备状态
	 * @param unintelligentDeviceIdList
	 * @param deviceUsedState
	 * @Since :2016年3月10日 上午11:53:16
	 */
	public void updateDeviceUsedStateByDeviceIdList(
			List<Integer> collectionDeviceIdList,
			DeviceUsedStateEnum deviceUsedState);
	
	public List<CollectionDevice> getListByQueryMap(Map<String, Object> map);

}
