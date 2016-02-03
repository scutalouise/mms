package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.domain.StateEnum;
import com.agama.device.dao.INetworkDeviceDao;
import com.agama.device.domain.NetworkDevice;

@Repository
public class NetworkDeviceDaoImpl extends HibernateDaoImpl<NetworkDevice, Integer>implements INetworkDeviceDao {

	@Override
	public void updateCurrentStateByIdentifier(String identifier,
			StateEnum currentState) {
		StringBuffer hql=new StringBuffer("update NetworkDevice set currentState='").append(currentState.toString()).append("'").append(" where identifier='").append(identifier).append("'");
		this.batchExecute(hql.toString());
		
	}

}
