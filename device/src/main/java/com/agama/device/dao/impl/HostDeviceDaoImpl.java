package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.domain.StateEnum;
import com.agama.device.dao.IHostDeviceDao;
import com.agama.device.domain.HostDevice;

@Repository
public class HostDeviceDaoImpl extends HibernateDaoImpl<HostDevice, Integer>implements IHostDeviceDao {

	@Override
	public void updateCurrentStateByIdentifier(String identifier,
			StateEnum stateEnum) {
		StringBuffer hql=new StringBuffer("update HostDevice set currentState='").append(stateEnum.toString()).append("'").append(" where identifier='").append(identifier).append("'");
		this.batchExecute(hql.toString());
		
	}

}
