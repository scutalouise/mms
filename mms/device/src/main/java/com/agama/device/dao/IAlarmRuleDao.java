package com.agama.device.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.AlarmRuleType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.domain.AlarmRule;
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
	public List<AlarmRule> getListByConditionIdAndAlarmOptionType(Integer alarmConditionId,AlarmOptionType alarmOptionType);
	/**
	 * @Description:根据告警条件id、分页条件和状态查询告警规则
	 * @param page
	 * @param alarmConditionId 
	 * @param status
	 * @return
	 * @Since :2016年1月5日 下午1:33:55
	 */
	
	public Page<AlarmRule> searchByAlarmConditionIdAndStatus(
			Page<AlarmRule> page, Integer alarmConditionId, StatusEnum status);

	

}
