package com.agama.device.dao;

import java.util.List;
import java.util.Map;

import com.agama.common.dao.IBaseDao;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.domain.PeDevice;

public interface IPeDeviceDao extends IBaseDao<PeDevice, Integer> {
	/**
	 * @Description:根据采集设备的id和状态获取动环设备集合
	 * @param id
	 * @param normal
	 * @return
	 * @Since :2016年1月20日 下午2:37:06
	 */
	public List<PeDevice> getListByCollectionDeviceIdAndStatus(Integer id,
			StatusEnum status);

	public PeDevice findByIdentifier(String identifier);
	
	public void updateCurrentStateByIdentifier(String identifier,
			StateEnum state);

	/**
	 * @Description:根据采集设备id获取采集设备所属的动环设备最严重的状态
	 * @param collectDeviceId 采集设备id
	 * @return
	 * @Since :2016年1月22日 下午3:16:08
	 */
	public StateEnum findSeverityStateByCollectId(Integer collectDeviceId);
	/**
	 * @Description:根据采集设备的IP、设备类型、接口号获取动环设备对象
	 * @param ip 采集设备IP
	 * @param switchinput 设备类型
	 * @param index 接口号
	 * @return
	 * @Since :2016年1月25日 上午9:43:55
	 */
	public List<PeDevice> getPeDeviceByIpAndDeviceTypeAndIndex(String ip,
			DeviceType switchinput, Integer index);

	/**
	 * @Description:获取设备运行状态的数量
	 * @return
	 * @Since :2016年2月18日 下午5:21:20
	 */
	public List<Map<String, Object>> getCurrentStateAndCount();
	/**
	 * @Description:根据二级设备类型获取网点下面的库存数量
	 * @param orgId
	 * @param secondType
	 * @return
	 * @Since :2016年3月8日 下午12:57:30
	 */
	public Long getInventoryCountBySecondDeviceType(Integer orgId,SecondDeviceType secondType);

	/**
	 * @Description:根据二级设备类型获取网点下面的所有设备数量
	 * @param orgId
	 * @param secondType
	 * @return
	 * @Since :2016年3月8日 下午6:05:33
	 */
	public Long getCountAll(Integer orgId,SecondDeviceType secondType);
	
	public List<PeDevice> getListByQueryMap(Map<String, Object> map);

}
