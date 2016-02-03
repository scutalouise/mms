package com.agama.device.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.device.domain.DeviceInventory;

public interface IDeviceInventoryDao extends IBaseDao<DeviceInventory, Integer> {
	/**
	 * @Description:根据采购记录id获取设备汇总信息
	 * @param purchaseId
	 * @return
	 * @Since :2016年1月18日 下午5:05:28
	 */
	public DeviceInventory getDeviceInventoryByPurchaseId(int purchaseId);
}
