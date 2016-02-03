package com.agama.device.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.common.domain.StateEnum;
import com.agama.device.domain.NetworkDevice;

public interface INetworkDeviceDao extends IBaseDao<NetworkDevice, Integer> {

	public void updateCurrentStateByIdentifier(String identifier,
			StateEnum currentState);

}
