package com.agama.device.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IDeviceAlarmRuleDao;
import com.agama.device.domain.DeviceAlarmRule;

/**
 * @Description:设备告警规则数据访问层实现类
 * @Author:ranjunfeng
 * @Since :2016年3月3日 下午1:27:07
 */
@Repository
public class DeviceAlarmRuleDaoImpl extends
		HibernateDaoImpl<DeviceAlarmRule, Integer> implements
		IDeviceAlarmRuleDao {

	@Override
	public Page<DeviceAlarmRule> searchByAlarmTemplateIdAndStatus(
			Page<DeviceAlarmRule> page,
			Integer alarmTemplateId, StatusEnum status) {
		StringBuffer hql=new StringBuffer("from DeviceAlarmRule where status='").append(status.toString()).append("'and alarmTemplate.id=").append(alarmTemplateId);
		
		return this.findPage(page, hql.toString());
	}

	@Override
	public List<DeviceAlarmRule> findListByTemplateId(Integer alarmTemplateId) {
		StringBuffer hql=new StringBuffer("from DeviceAlarmRule where status='").append(StatusEnum.NORMAL).append("' and alarmTemplate.id=").append(alarmTemplateId);
		return this.find(hql.toString());
	}

	
}
