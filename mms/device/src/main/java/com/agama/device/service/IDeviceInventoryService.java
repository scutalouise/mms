package com.agama.device.service;

import com.agama.common.service.IBaseService;
import com.agama.device.domain.DeviceInventory;

public interface IDeviceInventoryService extends IBaseService<DeviceInventory, Integer> {

	/**
	 * 根据二级设备类型获取设备汇总记录
	 * 
	 * @param deviceInventory
	 * @return
	 */
	public DeviceInventory getEntityByDeviceTypeAndBrand(DeviceInventory deviceInventory);

	/**
	 * 根据品牌Id获取设备汇总记录
	 * 
	 * @param brandId
	 * @return
	 */
	public DeviceInventory getByBrandId(int brandId);
	
	/**
	 * @Description: 根据设备编号获取设备汇总记录
	 * @param identifier 设备唯一编号
	 * @return 设备汇总表
	 * @Since :2016年1月15日 下午1:53:09
	 */
	public DeviceInventory getDeviceInventoryByPurchaseId(int id);
}
