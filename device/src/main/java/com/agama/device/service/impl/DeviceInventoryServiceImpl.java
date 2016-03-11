package com.agama.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.dao.utils.Page;
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

	@Override
	public Page<DeviceInventory> getPageByHql(Page<DeviceInventory> page) {
		
		StringBuffer hql = new StringBuffer( 
				"select new DeviceInventory("
				+ "d.id as id,d.quantity as quantity,d.scrapQuantity as scrapQuantity, "
				+ "d.freeQuantity as freeQuantity,d.firstDeviceType as firstDeviceType, "
				+ "d.secondDeviceType as secondDeviceType,d.brandId as brandId, "
				+ "b.name as brandName,d.otherNote as otherNote) "
			    + "from DeviceInventory d,Brand b "
			    + "where d.brandId=b.id ");//查询语句
		
       hql.append("order by d.firstDeviceType,d.secondDeviceType ");
       
       return deviceInventoryDao.findPage(page, hql.toString());
	}
}
