package com.agama.device.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.device.dao.IDeviceInventoryDao;
import com.agama.device.domain.DeviceInventory;


@Repository
public class DeviceInventoryDaoImpl extends HibernateDaoImpl<DeviceInventory, Integer>implements IDeviceInventoryDao {

	@Override
	public DeviceInventory getDeviceInventoryByPurchaseId(int purchaseId) {
		String hql = "select di from DeviceInventory di, DevicePurchase dp where dp.deviceInventoryId = di.id and dp.id = " + purchaseId;
		List<DeviceInventory> list = this.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	

}
