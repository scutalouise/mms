package com.agama.pemm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.pemm.dao.IAlarmConditionDao;
import com.agama.pemm.domain.AlarmCondition;
@Repository
public class AlarmConditionDaoImpl extends
		HibernateDaoImpl<AlarmCondition, Integer> implements IAlarmConditionDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql=new StringBuffer("update AlarmCondition set status=1 where id in (").append(ids).append(")");
		this.getSession().createQuery(hql.toString()).executeUpdate();
		
	}

	@Override
	public List<AlarmCondition> getListByTemplateId(Integer alarmTemplateId,DeviceInterfaceType deviceInterfaceType) {
		StringBuffer hql=new StringBuffer("select ac from AlarmCondition ac,AlarmTemplate at where ac.alarmTemplateId=at.id and at.status=0 and ac.status=0");
		if(alarmTemplateId!=null){
			hql.append(" and at.id=").append(alarmTemplateId);
		}
		hql.append(" and ac.deviceInterfaceType=").append(deviceInterfaceType.ordinal());
		return this.find(hql.toString());
	}

	

	
}
