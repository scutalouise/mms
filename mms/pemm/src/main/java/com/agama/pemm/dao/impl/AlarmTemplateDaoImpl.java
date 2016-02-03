package com.agama.pemm.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.IAlarmTemplateDao;
import com.agama.pemm.domain.AlarmTemplate;
@Repository
public class AlarmTemplateDaoImpl extends
		HibernateDaoImpl<AlarmTemplate, Integer> implements IAlarmTemplateDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql=new StringBuffer("update AlarmTemplate set status=1 where id in (").append(ids).append(")");
		this.getSession().createQuery(hql.toString()).executeUpdate();
		
	}

	

}
