package com.agama.pemm.dao.impl;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.IAlarmLogDao;
import com.agama.pemm.domain.AlarmLog;

@Repository
public class AlarmLogDaoImpl extends HibernateDaoImpl<AlarmLog, Integer>implements IAlarmLogDao {

	@Override
	public Object getAlarmNumAndTime(String areaInfoStr, String beginDate, String endDate) {
		StringBuffer hql = new StringBuffer(
				"select new map (count(alarmType) as num,date_format(collectTime,'%Y-%m-%d') as collectTime) from AlarmLog where alarmType=2");
		if (areaInfoStr != null) {
			hql.append(
					"and deviceId in (select id from Device where status=0 and gitInfo.id in ( select id from GitInfo where status=0 and areaInfoId in (")
					.append(areaInfoStr).append(")))");
		}
		if (beginDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d')>='").append(beginDate).append("'");
		}
		if (endDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d')<='").append(endDate).append("'");
		}
		hql.append(" group by date_format(collectTime,'%Y-%m-%d')");
		return this.getSession().createQuery(hql.toString()).list();
	}

	@Override
	public Object getAlarmNumAndTimeForYear(String beginDate, String endDate) {
		StringBuffer hql = new StringBuffer(
				"select new map (count(alarmType) as num,date_format(collectTime,'%Y-%m') as collectTime) from AlarmLog where alarmType=2");
		if (beginDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m')>='").append(beginDate).append("'");
		}
		if (endDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m')<='").append(endDate).append("'");
		}
		hql.append(" group by date_format(collectTime,'%Y-%m')  order  by date_format(collectTime,'%Y-%m')");
		return this.getSession().createQuery(hql.toString()).list();
	}

	@Override
	public Object getAlarmNumAndTimeForMonth(String beginDate, String endDate) {
		StringBuffer hql = new StringBuffer(
				"select new map (count(alarmType) as num,date_format(collectTime,'%Y-%m-%d') as collectTime) from AlarmLog where alarmType=2");
		if (beginDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d')>='").append(beginDate).append("'");
		}
		if (endDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d')<='").append(endDate).append("'");
		}
		hql.append(" group by date_format(collectTime,'%Y-%m-%d')  order  by date_format(collectTime,'%Y-%m-%d')");
		return this.getSession().createQuery(hql.toString()).list();
	}

	@Override
	public Object getAlarmNumAndTimeForDay(String beginDate, String endDate) {
		StringBuffer hql = new StringBuffer(
				"select new map (count(alarmType) as num,date_format(collectTime,'%Y-%m-%d %H') as collectTime) from AlarmLog where alarmType=2");
		if (beginDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d %H')>='").append(beginDate).append("'");
		}
		if (endDate != null) {
			hql.append(" and date_format(collectTime,'%Y-%m-%d %H')<='").append(endDate).append("'");
		}
		hql.append(" group by date_format(collectTime,'%Y-%m-%d %H') order  by date_format(collectTime,'%Y-%m-%d %H')");
		return this.getSession().createQuery(hql.toString()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlarmLog> getAlarmLog() {
		String hql = "SELECT al.id AS id, al.alarm_type AS runState, al.collect_time AS collectTime, al.content AS content, al.device_type AS deviceTypeIndex, d. NAME AS deviceName, g.ip AS gitInfoIp FROM Alarm_Log al RIGHT JOIN ( SELECT max(collect_Time) AS collectTime, device_id AS deviceId FROM alarm_Log WHERE STATUS = 0 GROUP BY deviceId ) a ON STATUS = 0 AND a.collectTime = al.collect_Time AND al.device_id = a.deviceId LEFT JOIN device d ON al.device_id = d.id LEFT JOIN git_info g ON g.id = d.git_info_id ORDER BY al.collect_Time";
		return this.getSession().createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(AlarmLog.class))
				.list();
	}

}
