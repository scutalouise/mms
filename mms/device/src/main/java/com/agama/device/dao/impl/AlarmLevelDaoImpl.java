package com.agama.device.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IAlarmLevelDao;
import com.agama.device.domain.AlarmLevel;

/**
 * @Description:告警等级数据访问层实现类
 * @Author:ranjunfeng
 * @Since :2015年12月25日 下午4:37:40
 */
@Repository
public class AlarmLevelDaoImpl extends HibernateDaoImpl<AlarmLevel, Integer>
		implements IAlarmLevelDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql = new StringBuffer(
				"update AlarmLevel set status=1 where id in (").append(ids)
				.append(")");
		this.getSession().createQuery(hql.toString()).executeUpdate();

	}

	@Override
	public List<AlarmLevel> getListByStatus(StatusEnum status) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("from AlarmLevel where 1=1");
		if (status != null) {
			hql.append(" and status=").append(status.getId());
		}
		return find(hql.toString());
	}

}
