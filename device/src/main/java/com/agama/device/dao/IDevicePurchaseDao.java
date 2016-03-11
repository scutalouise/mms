package com.agama.device.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.device.domain.DevicePurchase;

public interface IDevicePurchaseDao extends IBaseDao<DevicePurchase, Integer> {

	/**
	 * @Description:根据采购记录id获取采购的设备已经领取的数量；
	 * @param purchaseId
	 * @return
	 * @Since :2016年3月3日 下午5:34:31
	 */
	public Integer getObtainCountByPurchaseId(Integer purchaseId);
}
