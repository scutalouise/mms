package com.agama.pemm.service;

import java.util.List;
import java.util.Map;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.service.IBaseService;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.domain.Device;

public interface IDeviceService extends IBaseService<Device, Integer> {
	/**
	 * 根据主机ID、设备类型、设备入口查询设备信息
	 * 
	 * @param gitInfoId
	 * @param deviceType
	 * @param index
	 * @return
	 */
	public Device getListByGitInfoIdAndDeviceTypeAndIndex(Integer gitInfoId,
			DeviceType deviceType, Integer index);

	/**
	 * 扫描主机下面的设备信息并保存设备信息
	 * 
	 * @param gitInfoIdList
	 * @param deviceIndexList
	 */
	public void addDeviceForScan(List<Integer> gitInfoIdList,
			List<Map<String, Object>> deviceIndexList);

	/**
	 * 根据ID批量更改状态/逻辑删除
	 * 
	 * @param ids
	 */
	public void updateStatusByIds(String ids);

	public Page<Device> searchListByAreaInfoId(Integer areaInfoId,
			Page<Device> page, List<PropertyFilter> filters);

	public List<DeviceStateRecord> getDeviceStateRecordByAreaInfoId(
			String areaInfoIdStr);

	/**
	 * 根据主机ID和设备类型查找设备
	 * 
	 * @param gitInfoId
	 * @param deviceType
	 */
	public List<Device> getListByGitInfoIdAndDeviceType(Integer gitInfoId,
			DeviceType deviceType);

}
