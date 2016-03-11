package com.agama.device.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IUnintelligentDeviceDao;
import com.agama.device.domain.UnintelligentDevice;

@Repository
public class UnintelligentDeviceDaoImpl extends HibernateDaoImpl<UnintelligentDevice, Integer>
		implements IUnintelligentDeviceDao {
	@Override
	public Long getInventoryCountBySecondDeviceType(Integer orgId,
			SecondDeviceType secondType) {
		StringBuffer hql=new StringBuffer("select count(*) from UnintelligentDevice where status='").append(StatusEnum.NORMAL.toString()).append("' and purchaseId in (select id from DevicePurchase where orgId=").append(orgId).append(" and deviceInventoryId in (select id from DeviceInventory where secondDeviceType='").append(secondType.toString()).append("'))");
		return (Long)this.getSession().createQuery(hql.toString()).uniqueResult();

	}
	
	public List<UnintelligentDevice> getListByQueryMap(Map<String, Object> map) {
		String hql = "select new Map(b.name as brand,di.firstDeviceType as firstDeviceType, di.secondDeviceType as secondDeviceType, "
				+ " o.orgName as hallName, hd.name as name, hd.enable as enable, hd.remark as description, hd.model as model, hd.managerId as manager, "
				+ " CONCAT(hd.manufactureDate) as manufactureDate, CONCAT(hd.warrantyDate) as warrantyDate, hd.identifier as identifier) "
				+ " from UnintelligentDevice hd, Brand b, DevicePurchase dp, Organization o, DeviceInventory di "
				+ " where hd.organizationId = o.id and hd.purchaseId = dp.id and dp.deviceInventoryId = di.id and di.brandId = b.id "
				+ " and hd.status = '" + StatusEnum.NORMAL + "' ";
		if (map.get("organizationId") != null && StringUtils.isNotBlank(String.valueOf(map.get("organizationId")))) {
			hql += " and hd.organizationId = " + map.get("organizationId");
		}
		if (map.get("firstDeviceType") != null && StringUtils.isNotBlank(String.valueOf(map.get("firstDeviceType")))) {
			hql += " and di.firstDeviceType = '" + map.get("firstDeviceType") + "' ";
		}
		if (map.get("obtainUserId") != null && StringUtils.isNotBlank(String.valueOf(map.get("obtainUserId")))) {
			hql += " and hd.obtainUserId = " + map.get("obtainUserId");
		}
		if (map.get("deviceUsedState") != null && StringUtils.isNotBlank(String.valueOf(map.get("deviceUsedState")))) {
			hql += " and hd.deviceUsedState = '" + map.get("deviceUsedState") + "' ";
		}
		return this.find(hql);
	}
	
}
