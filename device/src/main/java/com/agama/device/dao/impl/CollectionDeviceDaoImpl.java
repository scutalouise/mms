package com.agama.device.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.domain.StateEnum;
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

}
