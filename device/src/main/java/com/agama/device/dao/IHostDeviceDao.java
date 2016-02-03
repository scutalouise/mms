package com.agama.device.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.common.domain.StateEnum;
import com.agama.device.domain.HostDevice;

public interface IHostDeviceDao extends IBaseDao<HostDevice, Integer> {
	/**
	 * @Description:根据唯一标识修改主机设备状态
	 * @param identifier
	 * @param valueOf
	 * @Since :2016年1月12日 下午3:01:47
	 */
	public void updateCurrentStateByIdentifier(String identifier,
			StateEnum currentState);

}
