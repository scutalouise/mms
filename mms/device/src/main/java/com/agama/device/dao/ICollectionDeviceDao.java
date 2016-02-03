package com.agama.device.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.domain.StateEnum;
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

}
