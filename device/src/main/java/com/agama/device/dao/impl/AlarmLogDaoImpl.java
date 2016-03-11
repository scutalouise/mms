package com.agama.device.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.device.dao.IAlarmLogDao;
import com.agama.device.domain.AlarmLog;

/**
 * @Description:告警日志数据访问层实现类
 * @Author:ranjunfeng
 * @Since :2015年12月25日 下午4:37:12
 */
@Repository
public class AlarmLogDaoImpl extends HibernateDaoImpl<AlarmLog, Integer>implements IAlarmLogDao {

	@Override
	public Object getAlarmNumAndTime(String organizationIdIdStr,DeviceInterfaceType deviceInterfaceType, String beginDate, String endDate) {
		StringBuffer hql = new StringBuffer(
				"select new map (count(currentState) as num,date_format(collectTime,'%Y-%m-%d') as collectTime) from AlarmLog where currentState=2");
		if (organizationIdIdStr != null) {
			hql.append(
					" and device.id in (select id from Device where status=0 and gitInfo.id in ( select id from GitInfo where status=0 and organizationId in (")
					.append(organizationIdIdStr).append(")))");
		}
		if(deviceInterfaceType!=null){
			hql.append(" and deviceInterfaceType=").append(deviceInterfaceType.ordinal());
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
				"select new map (count(currentState) as num,date_format(collectTime,'%Y-%m') as collectTime) from AlarmLog where currentState=2");
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
				"select new map (count(currentState) as num,date_format(collectTime,'%Y-%m-%d') as collectTime) from AlarmLog where currentState=2");
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
				"select new map (count(currentState) as num,date_format(collectTime,'%Y-%m-%d %H') as collectTime) from AlarmLog where currentState=2");
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
		//此处目前展示的是动环系统的告警日志，后期需要加入其他设备的日志记录
		String hql = "SELECT al.id AS id, al.current_state AS runState, al.collect_time AS collectTime, al.content AS content, al.alarm_option_type AS deviceTypeIndex, d. NAME AS deviceName, o.org_name AS organizationName FROM Alarm_Log al RIGHT JOIN ( SELECT max(alog.collect_Time) AS collectTime, alog.identifier AS identifier FROM alarm_Log alog, pe_device d WHERE alog. STATUS = 0 AND d.identifier = alog.identifier AND d.current_state != 0 AND d. STATUS = 0 GROUP BY identifier ) a ON al. STATUS = 0 AND a.collectTime = al.collect_Time AND al.identifier = a.identifier LEFT JOIN pe_device d ON al.identifier = d.identifier LEFT JOIN organization o ON o.id = d.organization_id WHERE d. STATUS = 0 ORDER BY al.collect_Time";
		return this.getSession().createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(AlarmLog.class))
				.list();
	}

	@Override
	public Object getAlarmNum(String organizationIdIdStr,Integer top,String beginDate,String endDate) {
		StringBuffer hql=new StringBuffer("select new map(count(al.id) as num,d.name as name,d.deviceInterfaceType as deviceInterfaceType,g.organizationName as organizationName) from AlarmLog al,Device d,GitInfo g where al.device.id=d.id and d.gitInfo.id=g.id and g.status=0 and d.status=0 ");
		if(organizationIdIdStr!=null){
			hql.append("and g.organizationId in (").append(organizationIdIdStr).append(")");
		}
		if (beginDate != null) {
			hql.append("and date_format(al.collectTime,'%Y-%m-%d')>='").append(beginDate).append("' ");
		}
		if (endDate != null) {
			hql.append("and date_format(al.collectTime,'%Y-%m-%d')<='").append(endDate).append("' ");
		}
		hql.append("group by d.name, g.organizationName  ORDER BY count(al.id) desc ");
		
		Query query=getSession().createQuery(hql.toString());
		query.setFetchSize(0);
		query.setMaxResults(top);
		return query.list();
	}

	@Override
	public void updateStatusByGitInfoIds(String gitInfoIds, int status) {
		StringBuffer hql=new StringBuffer("update AlarmLog set status=").append(status).append(" where status!=").append(status);
		if(gitInfoIds!=null){
			hql.append(" and device.id in (select id from Device where gitInfo.id in (").append(gitInfoIds).append("))");
		}
		this.batchExecute(hql.toString());
	}

	@Override
	public void updateStatusDeviceIds(String deviceIds, int status) {
		StringBuffer hql=new StringBuffer("update AlarmLog set status=").append(status).append(" where status!=").append(status);
		if(deviceIds!=null){
			hql.append(" and device.id in (").append(deviceIds).append(")");
		}
		this.batchExecute(hql.toString());
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlarmLog> getAlarmLogforTop(Integer top) {
		String hql = "SELECT al.id AS id, al.alarm_type AS runState, al.collect_time AS collectTime, al.content AS content, al.device_interface_type AS deviceTypeIndex, d.NAME AS deviceName, g.organization_name AS organizationName FROM Alarm_Log al RIGHT JOIN (SELECT max(alog.collect_Time) AS collectTime, alog.device_id AS deviceId FROM alarm_Log alog, device d WHERE alog. STATUS = 0 AND d.id = alog.device_id AND d.current_state != 0 AND d. STATUS = 0 GROUP BY deviceId) a ON al.STATUS = 0 AND a.collectTime = al.collect_Time AND al.device_id = a.deviceId LEFT JOIN device d ON al.device_id = d.id LEFT JOIN git_info g ON g.id = d.git_info_id where d.status=0 and g.status=0 ORDER BY al.collect_Time";
		if(top!=null){
			hql+=" limit 0,"+top;
		}
		return this.getSession().createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(AlarmLog.class))
				.list();
	}

}
