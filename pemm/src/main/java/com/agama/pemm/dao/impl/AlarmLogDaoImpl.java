package com.agama.pemm.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.IAlarmLogDao;
import com.agama.pemm.domain.AlarmLog;

@Repository
public class AlarmLogDaoImpl extends HibernateDaoImpl<AlarmLog, Integer>
		implements IAlarmLogDao {

	@Override
	public Object getAlarmNumAndTime(String areaInfoStr, String beginDate,
			String endDate) {
		StringBuffer hql = new StringBuffer(
				"select new map (count(alarmType) as num,date_format(collectTime,'%Y-%m-%d') as collectTime) from AlarmLog where alarmType=2");
		if (areaInfoStr != null) {
			hql.append(
					"and deviceId in (select id from Device where status=0 and gitInfo.id in ( select id from GitInfo where status=0 and areaInfoId in (")
					.append(areaInfoStr).append(")))");
		}
		if (beginDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d')>='").append(
					beginDate).append("'");
		}
		if(endDate!=null){
			hql.append(" and date_format(collectTime,'%Y-%m-%d')<='").append(
					endDate).append("'");
		}
		hql.append(" group by date_format(collectTime,'%Y-%m-%d')");
		return this.getSession().createQuery(hql.toString()).list();
	}

}
