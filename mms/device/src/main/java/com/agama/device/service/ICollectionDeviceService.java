package com.agama.device.service;

import java.util.List;

import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.CollectionDevice;

public interface ICollectionDeviceService extends IBaseService<CollectionDevice, Integer> {
	/**
	 * @Description:更具状态获取采集设备集合
	 * @param status 状态
	 * @return
	 * @Since :2016年1月20日 下午1:47:44
	 */
	public List<CollectionDevice> getListByStatus(StatusEnum status);
}
