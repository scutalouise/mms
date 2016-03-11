package com.agama.device.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.domain.DeviceAlarmRule;

/**
 * @Description:设备告警规则
 * @Author:ranjunfeng
 * @Since :2016年3月3日 下午1:23:41
 */
public interface IDeviceAlarmRuleDao extends IBaseDao<DeviceAlarmRule, Integer> {

	public Page<DeviceAlarmRule> searchByAlarmTemplateIdAndStatus(Page<DeviceAlarmRule> page,
			Integer alarmTemplateId,StatusEnum status);
	
	public List<DeviceAlarmRule> findListByTemplateId(Integer alarmTemplateId);

}
