package com.agama.device.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.authority.service.IOrganizationService;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IDevicePurchaseDao;
import com.agama.device.domain.DeviceInventory;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.service.IDeviceInventoryService;
import com.agama.device.service.IDevicePurchaseService;

@Service
public class DevicePurchaseServiceImpl extends BaseServiceImpl<DevicePurchase, Integer>implements IDevicePurchaseService {
	@Autowired
	private IDevicePurchaseDao devicePurchaseDao;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IDeviceInventoryService deviceInventoryService;
	
	@Override
	public Page<DevicePurchase> findPageByHQL(Page<DevicePurchase> page, HttpServletRequest request) {
		
		String ids="";
		String	name = request.getParameter("name");
		String	orgId = request.getParameter("orgId");
		String	GT_purchaseDate = request.getParameter("GTD_purchaseDate");
		String	LT_purchaseDate = request.getParameter("LTD_purchaseDate");
		String purchaseOrderNum = request.getParameter("purchaseOrderNum");
		if (orgId != null && orgId !="") {
			ids = organizationService.getOrganizationIdStrById(Integer.parseInt(orgId));
		}
		
		StringBuffer hql = new StringBuffer( " select new DevicePurchase(d.id as id,d.name as name,d.quantity as quantity, "
						+ "d.purchaseDate as purchaseDate,d.warrantyDate as warrantyDate, "
						+ "d.isPurchase as isPurchase,o.orgName as orgName,d.otherNote as otherNote, "
						+ "d.updateTime as updateTime,b.name as brandName,di.firstDeviceType as firstDeviceType, "
						+ "di.secondDeviceType as secondDeviceType,d.maintainWay as maintainWay,d.purchaseOrderNum as purchaseOrderNum )"
		+ "from DevicePurchase d,DeviceInventory di,Brand b,Organization o "
		+ "where di.id=d.deviceInventoryId and b.id=di.brandId  and d.orgId=o.id ");//查询语句
		
		if (name != null && name != "") {
			hql.append("and d.name like '%" + name.trim() + "%' ");
		}
		if (ids != null && ids != "") {
			hql.append("and d.orgId in(" + ids + ") ");
		}
		if (GT_purchaseDate != null && GT_purchaseDate != "") {
			hql.append("and d.purchaseDate>=str_to_date('" + GT_purchaseDate + "','%Y-%m-%d') ");
		}
		if (LT_purchaseDate != null && LT_purchaseDate != "") {
			hql.append("and d.purchaseDate<=str_to_date('" + LT_purchaseDate + "','%Y-%m-%d') ");
		}
		if(null != purchaseOrderNum && purchaseOrderNum != ""){
			hql.append(" and d.purchaseOrderNum ='" + purchaseOrderNum + "') ");
		}
		
		hql.append("order by d.orgId,d.purchaseDate");
		return devicePurchaseDao.findPage(page, hql.toString());
	}

	@Override
	public Page<DevicePurchase> search(Page<DevicePurchase> page, List<PropertyFilter> filters) {
		StringBuffer hql = new StringBuffer( " select new DevicePurchase(d.id as id,d.name as name,d.quantity as quantity, "
													+ "d.purchaseDate as purchaseDate,d.warrantyDate as warrantyDate, "
													+ "d.isPurchase as isPurchase,o.orgName as orgName,d.otherNote as otherNote, "
													+ "d.updateTime as updateTime,b.name as brandName,di.firstDeviceType as firstDeviceType, "
													+ "di.secondDeviceType as secondDeviceType,d.maintainWay as maintainWay ,d.purchaseOrderNum as purchaseOrderNum)"
												+ "from DevicePurchase d,DeviceInventory di,Brand b,Organization o "
												+ "where di.id=d.deviceInventoryId and b.id=di.brandId  and d.orgId=o.id ");//查询语句
		hql.append("order by d.orgId,d.purchaseDate");
		return devicePurchaseDao.findPage(page, hql.toString(), filters);
	}
	
	
	@Override
	public List<DevicePurchase> getByPurchaseId(Integer id) {
		return devicePurchaseDao.findBy("id", id);
	}

	@Override
	public Integer getObtainCountByPurchaseId(Integer purchaseId) {
		return devicePurchaseDao.getObtainCountByPurchaseId(purchaseId);
	}

	@Override
	public Map<String,String> obtainCountCompare(Integer purchaseId) {
		Map<String,String> map = new HashMap<String,String>();
		DevicePurchase dp = devicePurchaseDao.find(purchaseId);
		int count = devicePurchaseDao.getObtainCountByPurchaseId(purchaseId);
		if(count<dp.getQuantity()){
			map.put("result", "success");
		}else{
			map.put("result", "fails");
		}
		map.put("message", "采购记录记载数量"+ dp.getQuantity() +",已经领取"+count);
		return  map;
	}

	@Override
	public String savePurchaseAndInventory(DevicePurchase devicePurchase, DeviceInventory deviceInventory) {
		DeviceInventory inventory = deviceInventoryService.getEntityByDeviceTypeAndBrand(deviceInventory);
		if (inventory == null) {
			deviceInventory.setQuantity(devicePurchase.getQuantity());
			deviceInventory.setFreeQuantity(devicePurchase.getQuantity());
			deviceInventory.setScrapQuantity(0);
			deviceInventoryService.save(deviceInventory);
			DeviceInventory device = deviceInventoryService.getEntityByDeviceTypeAndBrand(deviceInventory);
			devicePurchase.setUpdateTime(new Date());
			devicePurchase.setDeviceInventoryId(device.getId());
			devicePurchaseDao.save(devicePurchase);
			return "success";
		} else {
			inventory.setQuantity(inventory.getQuantity() + devicePurchase.getQuantity());
			inventory.setFreeQuantity(inventory.getFreeQuantity() + devicePurchase.getQuantity());
			deviceInventoryService.update(inventory);
			devicePurchase.setUpdateTime(new Date());
			devicePurchase.setDeviceInventoryId(inventory.getId());
			devicePurchaseDao.save(devicePurchase);
			return "success";
		}
	}

	@Override
	public String updatePurchaseAndInventory(DevicePurchase devicePurchase, DeviceInventory deviceInventory) {
		DevicePurchase purchase = devicePurchaseDao.find(devicePurchase.getId());
		DeviceInventory inventory = deviceInventoryService.getEntityByDeviceTypeAndBrand(deviceInventory);
		if (inventory == null) {
			deviceInventory.setQuantity(devicePurchase.getQuantity());
			deviceInventory.setFreeQuantity(devicePurchase.getQuantity());
			deviceInventory.setScrapQuantity(0);
			deviceInventoryService.save(deviceInventory);
			DeviceInventory in = deviceInventoryService.get(purchase.getDeviceInventoryId());
			in.setQuantity(in.getQuantity() - purchase.getQuantity());
			in.setFreeQuantity(in.getFreeQuantity() - purchase.getQuantity());
			deviceInventoryService.update(in);
			DeviceInventory device = deviceInventoryService.getEntityByDeviceTypeAndBrand(deviceInventory);
			devicePurchase.setUpdateTime(new Date());
			devicePurchase.setDeviceInventoryId(device.getId());
			devicePurchaseDao.merge(devicePurchase);
			devicePurchaseDao.update(purchase);
			return "success";
		} else if (purchase.getDeviceInventoryId() == inventory.getId()) {
			inventory.setQuantity(inventory.getQuantity() - purchase.getQuantity() + devicePurchase.getQuantity());
			inventory.setFreeQuantity(
					inventory.getFreeQuantity() - purchase.getQuantity() + devicePurchase.getQuantity());
			deviceInventoryService.update(inventory);
			devicePurchase.setUpdateTime(new Date());
			devicePurchase.setDeviceInventoryId(purchase.getDeviceInventoryId());
			devicePurchaseDao.merge(devicePurchase);
			devicePurchaseDao.update(purchase);
			return "success";
		} else {
			inventory.setQuantity(inventory.getQuantity() + devicePurchase.getQuantity());
			inventory.setFreeQuantity(inventory.getFreeQuantity() + devicePurchase.getQuantity());
			deviceInventoryService.update(inventory);
			DeviceInventory in = deviceInventoryService.get(purchase.getDeviceInventoryId());
			in.setQuantity(in.getQuantity() - purchase.getQuantity());
			in.setFreeQuantity(in.getFreeQuantity() - purchase.getQuantity());
			deviceInventoryService.update(in);
			devicePurchase.setUpdateTime(new Date());
			devicePurchase.setDeviceInventoryId(inventory.getId());
			devicePurchaseDao.merge(devicePurchase);
			devicePurchaseDao.update(purchase);
			return "success";
	   }
   }

	@Override
	public DevicePurchase getPurchaseByid(Integer id) {
		StringBuffer hql = new StringBuffer( " select new DevicePurchase(d.id as id,d.name as name,d.quantity as quantity, "
									+ "d.purchaseDate as purchaseDate,d.warrantyDate as warrantyDate, "
									+ "d.isPurchase as isPurchase,o.orgName as orgName,d.otherNote as otherNote, "
									+ "d.updateTime as updateTime,b.name as brandName,di.firstDeviceType as firstDeviceType, "
									+ "di.secondDeviceType as secondDeviceType,d.maintainWay as maintainWay,d.purchaseOrderNum as purchaseOrderNum )"
					+ "from DevicePurchase d,DeviceInventory di,Brand b,Organization o "
					+ "where di.id=d.deviceInventoryId and b.id=di.brandId  and d.orgId=o.id ");//查询语句
		hql.append(" and d.id=?0");
		return devicePurchaseDao.findUnique(hql.toString(), id);
	}

}
