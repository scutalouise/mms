package com.agama.device.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.domain.PeDevice;



@Repository
public class PeDeviceDaoImpl extends HibernateDaoImpl<PeDevice, Integer>implements IPeDeviceDao {

	@Override
	public List<PeDevice> getListByCollectionDeviceIdAndStatus(Integer id,
			StatusEnum status) {
	
		return find(Restrictions.eq("collectDeviceId", id),Restrictions.eq("status", status));
	}

	@Override
	public PeDevice findByIdentifier(String identifier) {
		// TODO Auto-generated method stub
		return findUnique(Restrictions.eq("identifier", identifier));
	}

	@Override
	public void updateCurrentStateByIdentifier(String identifier,
			StateEnum state) {
		
		StringBuffer hql = new StringBuffer("update PeDevice set currentState='").append(state.name())
				.append("' where identifier='").append(identifier).append("'");
		this.batchExecute(hql.toString());
		
	}

	@Override
	public StateEnum findSeverityStateByCollectId(Integer collectDeviceId) {
		String hql="from PeDevice where status="+StatusEnum.NORMAL.ordinal()+" and collectDeviceId="+collectDeviceId+" order by currentState desc";
		List<PeDevice> peDeviceList=find(hql);
		if(peDeviceList.size()>0){
			return peDeviceList.get(0).getCurrentState();
		}
		return null;
	}

	@Override
	public List<PeDevice> getPeDeviceByIpAndDeviceTypeAndIndex(String ip,
			DeviceType switchinput, Integer index) {
		String hql="from PeDevice where status="+StatusEnum.NORMAL.getId()+" and collectDeviceId in (select id from CollectionDevice where status="+StatusEnum.NORMAL.getId()+" and ip='"+ip+"') and dhDeviceIndex="+index;
		return find(hql);
	}

	@Override
	public List<Map<String, Object>> getCurrentStateAndCount() {
		String hql = "select new map(currentState as currentState,count(currentState) as num) from PeDevice where status=0 group by currentState";
		return this.getSession().createQuery(hql.toString()).list();
	}

	@Override
	public Long getInventoryCountBySecondDeviceType(Integer orgId,
			SecondDeviceType secondType) {
		StringBuffer hql=new StringBuffer("select count(*) from PeDevice where deviceUsedState='").append(DeviceUsedStateEnum.PUTINSTORAGE).append("' and status='").append(StatusEnum.NORMAL.toString()).append("' and purchaseId in (select id from DevicePurchase where orgId=").append(orgId).append(" and deviceInventoryId in (select id from DeviceInventory where secondDeviceType='").append(secondType.toString()).append("'))");
		return (Long)this.getSession().createQuery(hql.toString()).uniqueResult();
	}

	@Override
	public Long getCountAll(Integer orgId, SecondDeviceType secondType) {
		StringBuffer hql=new StringBuffer("select count(*) from PeDevice where deviceUsedState!='").append(DeviceUsedStateEnum.SCRAP).append("' and status='").append(StatusEnum.NORMAL.toString()).append("' and purchaseId in (select id from DevicePurchase where orgId=").append(orgId).append(" and deviceInventoryId in (select id from DeviceInventory where secondDeviceType='").append(secondType.toString()).append("'))");
		return (Long)this.getSession().createQuery(hql.toString()).uniqueResult();

	}
	
	public List<PeDevice> getListByQueryMap(Map<String, Object> map) {
		String hql = "select new Map(b.name as brand,di.firstDeviceType as firstDeviceType, di.secondDeviceType as secondDeviceType, "
				+ " o.orgName as hallName, hd.name as name, hd.enable as enable, hd.remark as description, hd.model as model, hd.managerId as manager, "
				+ " CONCAT(hd.manufactureDate) as manufactureDate, CONCAT(hd.warrantyDate) as warrantyDate, hd.identifier as identifier) "
				+ " from PeDevice hd, Brand b, DevicePurchase dp, Organization o, DeviceInventory di "
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
