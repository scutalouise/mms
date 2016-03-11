package com.agama.device.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.ICollectionDeviceDao;
import com.agama.device.domain.CollectionDevice;

@Repository
public class CollectionDeviceDaoImpl extends HibernateDaoImpl<CollectionDevice, Integer>
		implements ICollectionDeviceDao {

	@Override
	public List<CollectionDevice> getListByStatus(StatusEnum status) {
		
		return find(Restrictions.eq("status", status));
	}

	@Override
	public void updateCurrentStateById(int collectDeviceId, StateEnum state) {
		StringBuffer hql = new StringBuffer("update CollectionDevice set currentState='").append(state.name())
				.append("' where id=").append(collectDeviceId);
		this.batchExecute(hql.toString());
		
	}

	@Override
	public List<CollectionDevice> getCollectionListByOrgId(
			Integer organizationId) {
		StringBuffer hql=new StringBuffer("from CollectionDevice where deviceUsedState='").append(DeviceUsedStateEnum.USED).append("' and organizationId=").append(organizationId);
		
		return this.find(hql.toString());
	}

	@Override
	public List<CollectionDevice> getCollectionListByObtainUserId(
			Integer obtainUserId) {
		StringBuffer hql=new StringBuffer("from CollectionDevice where organizationId in (select orgId from UserOrg where userId=").append(obtainUserId).append(")");
		return this.find(hql.toString());
	}

	@Override
	public Long getInventoryCountBySecondDeviceType(Integer orgId,
			SecondDeviceType secondType) {
		StringBuffer hql=new StringBuffer("select count(*) from CollectionDevice where status='").append(StatusEnum.NORMAL.toString()).append("' and purchaseId in (select id from DevicePurchase where orgId=").append(orgId).append(" and deviceInventoryId in (select id from DeviceInventory where secondDeviceType='").append(secondType.toString()).append("'))");
		return (Long)this.getSession().createQuery(hql.toString()).uniqueResult();
	}

	@Override
	public List<CollectionDevice> getListByQueryMap(Map<String, Object> map) {
		String hql = "select new Map(b.name as brand,di.firstDeviceType as firstDeviceType, di.secondDeviceType as secondDeviceType, "
				+ " o.orgName as hallName, hd.name as name, hd.enable as enable, hd.remark as description, hd.model as model, hd.managerId as manager, "
				+ " CONCAT(hd.manufactureDate) as manufactureDate, CONCAT(hd.warrantyDate) as warrantyDate, hd.identifier as identifier) "
				+ " from CollectionDevice hd, Brand b, DevicePurchase dp, Organization o, DeviceInventory di "
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
