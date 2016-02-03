package com.agama.pemm.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.IDischargeStatisticDao;
import com.agama.pemm.domain.DischargeStatistic;

/**
 * @Description:放电统计数据访问层实现业务
 * @Author:ranjunfeng
 * @Since :2015年12月9日 上午11:14:27
 */
@Repository
@Transactional
public class DischargeStatisticDaoImpl extends
		HibernateDaoImpl<DischargeStatistic, Integer> implements
		IDischargeStatisticDao {

	@Override
	public DischargeStatistic findBeginDateForNewData(Integer deviceId,Long batch,String order) {
		String hql="from DischargeStatistic where deviceId="+deviceId;
		if(batch!=null){
			hql+=" and batch="+batch;
		}
		hql+=" order by collectTime "+order;
		Query query=this.getSession().createQuery(hql);
		query.setFirstResult(0);
		query.setFetchSize(1);
		List<DischargeStatistic> list=query.list();
		if(list!=null&&list.size()>0){
			return  list.get(0);
		}
		return null;
	}

	@Override
	public Double findAvgLoadByDeviceIdAndBatch(Integer deviceId, Long batch) {
		// TODO Auto-generated method stub
		String hql="select avg(deviceLoad) from DischargeStatistic where deviceId="+deviceId+" and batch="+batch;
		List<Double> list=getSession().createQuery(hql).list();
		
		return list.get(0);
	}

	@Override
	public DischargeStatistic findBeforeDataByDeviceIdAndBatch(
			Integer deviceId, Long batch, String order) {
		String hql="from DischargeStatistic where deviceId="+deviceId;
		if(batch!=null){
			hql+=" and batch!="+batch;
		}
		hql+=" order by collectTime "+order;
		Query query=this.getSession().createQuery(hql);
		query.setFirstResult(0);
		query.setFetchSize(1);
		List<DischargeStatistic> list=query.list();
		if(list!=null&&list.size()>0){
			return  list.get(0);
		}
		return null;
	}

	
}
