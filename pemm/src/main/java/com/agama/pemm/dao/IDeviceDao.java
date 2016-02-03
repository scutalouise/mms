package com.agama.pemm.dao;

import java.util.List;
import java.util.Map;

import com.agama.common.dao.IBaseDao;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.common.enumbean.DeviceType;
import com.agama.pemm.domain.Device;

/**
 * @Description:设备数据访问层接口
 * @Author:ranjunfeng
 * @Since :2015年10月29日 下午2:22:49
 */
public interface IDeviceDao extends IBaseDao<Device, Integer> {
	/**
	 * @Description:根据id字符串修改设备的状态
	 * @param ids
	 * @Since :2015年10月29日 下午2:23:08
	 */
	public void updateStatusByIds(String ids);

	/**
	 * @Description:根据机构的id字符串获取设备状态几率
	 * @param organizationIdStr
	 * @return
	 * @Since :2015年10月29日 下午2:24:24
	 */
	public List<DeviceStateRecord> getDeviceStateRecordByOrganizationId(
			String organizationIdStr);

	/**
	 * @Description:根据id更改设备的运行状态
	 * @param id
	 * @param stateEnum
	 * @Since :2015年10月29日 下午2:23:58
	 */
	public void updateCurrentStateById(Integer id, StateEnum stateEnum);

	/**
	 * @Description:获取设备运行状态的数量
	 * @return
	 * @Since :2015年10月29日 下午2:23:35
	 */
	public List<Map<String, Object>> getCurrentStateAndCount();

	/**
	 * @Description:根据状态统计网点的个数
	 * @param stateEnum状态类型
	 * @return
	 * @Since :2015年10月29日 下午2:26:38
	 */
	public Integer statisticBranchStateNum(StateEnum stateEnum);

	/**
	 * @Description:根据gitId字符串修改设备状态
	 * @param gitInfoIds
	 * @Since :2014年9月3日 上午10:17:28
	 */
	public void updateStatusByGitInfoIds(String gitInfoIds, int status);

	/**
	 * @Description:根据上位机id和状态查询设备集合
	 * @param gitInfoId
	 * @param status
	 * @return
	 * @Since :2015年11月20日 下午1:29:07
	 */
	public List<Device> getDeviceListByGitIdAndStatus(Integer gitInfoId,
			int status);

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
	 * @Description:根据上位机ip、设备类型、设备接口好获取设备集合对象
	 * @param ip
	 * @param deviceType
	 * @return
	 * @Since :2015年12月10日 上午11:33:21
	 */
	public List<Device> getDeviceByIpAndDeviceTypeAndIndex(String ip,
			DeviceType deviceType, Integer index);

	/**
	 * @Description:根据git的id和设备接口类型获取设备集合
	 * @param id
	 * @param water
	 * @return
	 * @Since :2015年12月10日 下午4:37:44
	 */
	public List<Device> getWaterDeviceByGitInfoIdAndDeviceInterfaceType(
			Integer id, DeviceInterfaceType water);

	/**
	 * @Description:根据git网关id获取git挂接的设备最严重的状态
	 * @param id
	 * @return
	 * @Since :2016年1月21日 下午6:16:06
	 */
	public StateEnum findSeverityStateByGitInfoId(Integer id);
}
