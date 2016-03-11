package com.agama.pemm.service;

import java.util.List;
import java.util.Map;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.StateEnum;
import com.agama.common.service.IBaseService;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.common.enumbean.DeviceType;
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

	public Page<Device> searchListByOrganizationId(Integer organizationId,
			Page<Device> page, List<PropertyFilter> filters);

	public Page<Device> searchListByOrganizationIdStr(String organizationIds,String deviceType,
			Page<Device> page);

	public List<DeviceStateRecord> getDeviceStateRecordByOrganizationId(
			String organizationIdStr);

	/**
	 * 根据主机ID和设备类型查找设备
	 * 
	 * @param gitInfoId
	 * @param deviceType
	 */
	public List<Device> getListByGitInfoIdAndDeviceType(Integer gitInfoId,
			DeviceType deviceType);

	/**
	 * 根据设备ID修改设备状态
	 * 
	 * @param id
	 */
	public void updateCurrentStateById(Integer id, StateEnum stateEnum);

	/**
	 * 获取设备当前状态和数量
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getCurrentStateAndCount();

	/**
	 * @Description:根据定时器的名称、定时器组、管理员、类型获取放电设备分页集合
	 * @param page
	 * @param scheduleName
	 *            定时器名称
	 * @param scheduleGroup
	 *            定时器组
	 * @param managerId
	 *            管理员Id
	 * @param type
	 *            类型 0:已选择的设备 1：未选择的设备
	 * @return
	 * @Since :2015年11月26日 下午5:14:33
	 */
	public Page<Device> getDischargeDevice(Page<Device> page,
			String scheduleName, String scheduleGroup, Integer managerId,
			Integer type);

	/**
	 * 根据计划放电任务名称和任务组查询放电设备集合
	 * 
	 * @param scheduleName
	 *            任务名称
	 * @param scheduleGroup
	 *            任务组
	 * @return
	 */
	public List<Device> getDeviceListByScheduleNameAndScheduleGroup(
			String scheduleName, String scheduleGroup);

	/**
	 * @Description:根据设备id关闭指定的ups设备
	 * @param deviceId
	 * @Since :2015年12月2日 上午9:13:29
	 */
	public void upsClose(Integer deviceId);
	 /**
     * @Description:根据上位机ip、设备类型、设备接口好获取设备集合对象
     * @param ip
     * @param deviceType
     * @return
     * @Since :2015年12月10日 上午11:33:21
     */
    public  List<Device> getDeviceByIpAndDeviceTypeAndIndex(String ip,DeviceType deviceType,Integer index);


}
