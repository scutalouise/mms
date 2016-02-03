package com.agama.device.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.status.Status;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceType;
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
				.append("' where identifier=").append(identifier);
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

}
