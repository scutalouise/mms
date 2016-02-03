package com.agama.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IDeviceInventoryDao;
import com.agama.device.domain.DeviceInventory;
import com.agama.device.service.IDeviceInventoryService;


@Service
@Transactional
public class DeviceInventoryServiceImpl extends BaseServiceImpl<DeviceInventory, Integer>
		implements IDeviceInventoryService {

	@Autowired
	private IDeviceInventoryDao deviceInventoryDao;

	@Override
	public DeviceInventory getEntityByDeviceTypeAndBrand(DeviceInventory deviceInventory) {
		String hql = "from DeviceInventory where firstDeviceType=?0 and secondDeviceType=?1 and brandId=?2";
		return deviceInventoryDao.findUnique(hql, deviceInventory.getFirstDeviceType(),
				deviceInventory.getSecondDeviceType(), deviceInventory.getBrandId());
	}

	@Override
	public DeviceInventory getByBrandId(int brandId) {
		return deviceInventoryDao.findUniqueBy("brandId", brandId);
	}

	@Override
	public DeviceInventory getDeviceInventoryByPurchaseId(int purchaseId) {
		return deviceInventoryDao.getDeviceInventoryByPurchaseId(purchaseId);
	}
}
