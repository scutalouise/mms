package com.agama.device.dao;

import java.util.List;
import java.util.Map;

import com.agama.common.dao.IBaseDao;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.device.domain.NetworkDevice;

public interface INetworkDeviceDao extends IBaseDao<NetworkDevice, Integer> {

	public void updateCurrentStateByIdentifier(String identifier,
			StateEnum currentState);
	public Long getInventoryCountBySecondDeviceType(Integer orgId,
			SecondDeviceType secondType);
	
	public List<NetworkDevice> getListByQueryMap(Map<String, Object> map);

}
