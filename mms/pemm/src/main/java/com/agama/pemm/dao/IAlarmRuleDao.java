package com.agama.pemm.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.pemm.domain.AlarmRule;
/**
 * @Description:告警规则接口层
 * @Author:ranjunfeng
 * @Since :2015年9月30日 上午10:27:45
 */
public interface IAlarmRuleDao extends IBaseDao<AlarmRule, Integer>{
	/**
	 * @Description:根据ID字符串修改状态
	 * @param ids ID字符串 用,隔开
	 * @Since :2015年9月30日 上午10:29:14
	 */
	public void updateStatusByIds(String ids);
	/**
	 * @Description:根据设备ID获取告警规则集合
	 * @param deviceId 设备ID
	 * @return
	 * @Since :2015年9月30日 上午10:30:21
	 */
	 public List<AlarmRule> getListByConditionIdAndDeviceInterfaceType(Integer alarmConditionId,DeviceInterfaceType deviceInterfaceType);

	

}
