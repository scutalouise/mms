package com.agama.device.dao;

import java.util.List;
import java.util.Map;

import com.agama.common.dao.IBaseDao;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.device.domain.UnintelligentDevice;

public interface IUnintelligentDeviceDao extends IBaseDao<UnintelligentDevice, Integer> {
	public Long getInventoryCountBySecondDeviceType(Integer orgId,
			SecondDeviceType secondType);
	
	public List<UnintelligentDevice> getListByQueryMap(Map<String, Object> map);
}
