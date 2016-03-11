package com.agama.device.dao;

import java.util.List;
import java.util.Map;

import com.agama.common.dao.IBaseDao;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.domain.CollectionDevice;

public interface ICollectionDeviceDao extends IBaseDao<CollectionDevice, Integer> {
	/**
	 * @Description:更具状态获取采集设备集合
	 * @param status
	 * @return
	 * @Since :2016年1月20日 下午1:40:20
	 */
	public List<CollectionDevice> getListByStatus(StatusEnum status);

	public void updateCurrentStateById(int collectDeviceId, StateEnum state);
	/**
	 * @Description:根据网点id获取已经领取的采集设备
	 * @param organizationId
	 * @return
	 * @Since :2016年3月4日 下午3:57:32
	 */
	public List<CollectionDevice> getCollectionListByOrgId(
			Integer organizationId);
/**
 * @Description:根据领用人id获取领用人所属网点的采集设备集合
 * @param obtainUserId
 * @return
 * @Since :2016年3月4日 下午4:54:53
 */
	public List<CollectionDevice> getCollectionListByObtainUserId(
			Integer obtainUserId);
	
	
	public Long getInventoryCountBySecondDeviceType(Integer orgId,
			SecondDeviceType secondType);
	
	public List<CollectionDevice> getListByQueryMap(Map<String, Object> map);

}
