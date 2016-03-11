package com.agama.device.dao;

import java.util.List;
import java.util.Map;

import com.agama.common.dao.IBaseDao;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.SecondDeviceType;
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
	
	/**
	 * @Description:根据二级设备类型获取网点下面的库存数量
	 * @param orgId
	 * @param secondType
	 * @return
	 * @Since :2016年3月8日 下午12:57:30
	 */
	public Long getInventoryCountBySecondDeviceType(Integer orgId,SecondDeviceType secondType);
	
	public List<HostDevice> getListByQueryMap(Map<String, Object> map);


}
