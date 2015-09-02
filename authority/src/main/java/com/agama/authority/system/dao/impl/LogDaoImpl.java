package com.agama.authority.system.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.authority.system.dao.ILogDao;
import com.agama.authority.system.entity.Log;
import com.agama.common.dao.impl.HibernateDaoImpl;


/**
 * @Description:日志DAO实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:30:50
 */
@Repository("logDao")
public class LogDaoImpl extends HibernateDaoImpl<Log, Integer> implements ILogDao{
	
	/**
	 * 批量删除日志
	 * @param ids 日志id列表
	 */
	public void deleteBatch(List<Integer> idList){
		String hql="delete from Log log where log.id in (:idList)";
		Query query=getSession().createQuery(hql);
		query.setParameterList("idList", idList);
		query.executeUpdate();
	}
}
