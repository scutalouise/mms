package com.agama.pemm.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
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

	
}
