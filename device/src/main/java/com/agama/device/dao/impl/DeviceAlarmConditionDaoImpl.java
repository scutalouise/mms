package com.agama.device.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.dao.utils.PropertyFilter.MatchType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.controller.DeviceAlarmConditionController;
import com.agama.device.dao.IDeviceAlarmConditionDao;
import com.agama.device.domain.DeviceAlarmCondition;
@Repository
public class DeviceAlarmConditionDaoImpl extends
		HibernateDaoImpl<DeviceAlarmCondition, Integer> implements
		IDeviceAlarmConditionDao {

	@Override
	public void updateStatusById(Integer deviceAlarmConditionId,
			StatusEnum status) {
		StringBuffer hql=new StringBuffer("update DeviceAlarmCondition set status='").append(status.toString()).append("' where id=").append(deviceAlarmConditionId);
		this.batchExecute(hql.toString());
		
	}

	@Override
	public List<DeviceAlarmCondition> findListByTemplateId(
			Integer alarmTemplateId) {
		StringBuffer hql=new StringBuffer("from DeviceAlarmCondition where status='").append(StatusEnum.NORMAL).append("' and alarmTemplate.id=").append(alarmTemplateId);
		return this.find(hql.toString());
	}

	
}
