package com.agama.pemm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.pemm.dao.IAlarmRuleDao;
import com.agama.pemm.domain.AlarmRule;

@Repository
public class AlarmRuleDaoImpl extends HibernateDaoImpl<AlarmRule, Integer>
		implements IAlarmRuleDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql=new StringBuffer("update AlarmRule set status=1 ");
		if(ids!=null){
			hql.append(" where id in (").append(ids).append(")");
			
		}
		
		this.getSession().createQuery(hql.toString()).executeUpdate();
	}
	@Override
	public List<AlarmRule> getListByConditionIdAndDeviceInterfaceType(Integer alarmConditionId,DeviceInterfaceType deviceInterfaceType) {
		StringBuffer hql=new StringBuffer("select ar from AlarmRule  ar,AlarmCondition ac where ar.alarmConditionId=ac.id and ar.status=0 and ac.status=0");
		if(alarmConditionId!=null){
			hql.append(" and ac.id=").append(alarmConditionId);
		}
		hql.append(" and ar.deviceInterfaceType='").append(deviceInterfaceType.toString()).append("'");
		
		return this.find(hql.toString());
	}

	

}
