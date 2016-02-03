package com.agama.pemm.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.IAlarmLevelDao;
import com.agama.pemm.domain.AlarmLevel;

/**
 * @Author:ranjunfeng
 * @Since :2015年9月18日 上午10:23:07
 * @Description:告警等级数据访问实体
 */
@Repository
public class AlarmLevelDaoImpl extends HibernateDaoImpl<AlarmLevel, Integer>
		implements IAlarmLevelDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql=new StringBuffer("update AlarmLevel set status=1 where id in (").append(ids).append(")");
		this.getSession().createQuery(hql.toString()).executeUpdate();
		
	}

}
