package com.agama.device.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.DeviceInventory;
import com.agama.device.domain.DevicePurchase;

public interface IDevicePurchaseService extends IBaseService<DevicePurchase, Integer> {
	
	public Page<DevicePurchase> findPageByHQL(Page<DevicePurchase> page, HttpServletRequest request);
	
	public List<DevicePurchase> getByPurchaseId(Integer id);
	
	/**
	 * @Description:根据采购记录id获取采购的设备已经领取的情况；
	 * @param purchaseId
	 * @return
	 * @Since :2016年3月3日 下午5:28:34
	 */
	public Integer getObtainCountByPurchaseId(Integer purchaseId);
	
	/**
	 * 重新加载新的查询条件；
	 */
	/**
	 * @Description:与采购记录中记录的数量进行比较，
	 * @param purchaseId
	 * @return	已经领取的设备数量小于当前采购记录的数量返回：{result:"success";message:"采购记录记载数量**，已经领取**"}；否则返回：{result:"fails";message:"采购记录记载数量**，已经领取**"};
	 * @Since :2016年3月4日 上午11:37:30
	 */
	public Map<String,String> obtainCountCompare(Integer purchaseId);
	
	/**
	 * @Description 保存采购，生成设备汇总
	 * @param devicePurchase
	 * @param deviceInventory
	 * @return
	 * @Since 2016年3月10日 下午3:07:37
	 */
	public String savePurchaseAndInventory(DevicePurchase devicePurchase, @Valid DeviceInventory deviceInventory);
	
	/**
	 * @Description 修改采购，设备汇总
	 * @param devicePurchase
	 * @param deviceInventory
	 * @return
	 * @Since 2016年3月10日 下午3:11:02
	 */
	public String updatePurchaseAndInventory(DevicePurchase devicePurchase, @Valid DeviceInventory deviceInventory);
	
	/**
	 * @Description:根据id查找定制的DevicePurchase
	 * @param id
	 * @return
	 * @Since :2016年3月11日 下午2:58:37
	 */
	public DevicePurchase getPurchaseByid(Integer id);
	
}
