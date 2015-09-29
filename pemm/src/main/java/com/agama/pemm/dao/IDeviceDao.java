package com.agama.pemm.dao;

import java.util.List;
import java.util.Map;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.bean.StateEnum;
import com.agama.pemm.domain.Device;

public interface IDeviceDao extends IBaseDao<Device, Integer> {

	public void updateStatusByIds(String ids);
	public List<DeviceStateRecord> getDeviceStateRecordByAreaInfoId(String areaInfoIdStr);
	public void updateCurrentStateById(Integer id, StateEnum stateEnum);
	public List<Map<String,Object>> getCurrentStateAndCount();
	

}
