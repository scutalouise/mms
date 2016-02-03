package com.agama.pemm.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.IDischargeLogDao;
import com.agama.pemm.domain.DischargeLog;

/**
 * @Description:放电日志数据访问层实现类
 * @Author:ranjunfeng
 * @Since :2015年12月2日 上午9:49:49
 */
@Repository
public class DischargeLogDaoImpl extends
		HibernateDaoImpl<DischargeLog, Integer> implements IDischargeLogDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql = new StringBuffer(
				"update DischargeLog set status=1 where id in (").append(ids)
				.append(")");
		this.getSession().createQuery(hql.toString()).executeUpdate();
		
	}

	
}
