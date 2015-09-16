package com.agama.pemm.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.domain.Device;

public interface IDeviceDao extends IBaseDao<Device, Integer> {

	public void updateStatusByIds(String ids);
	public List<DeviceStateRecord> getDeviceStateRecordByAreaInfoId(String areaInfoIdStr);
	

}
