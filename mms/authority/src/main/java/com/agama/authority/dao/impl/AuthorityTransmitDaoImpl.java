package com.agama.authority.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.authority.dao.IAuthorityTransmitDao;
import com.agama.authority.entity.AuthorityTransmit;
import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.ConstantStateEnum;

/**
 * @Description:权限移交DAO层实现类
 * @Author:scuta
 * @Since :2015年12月30日 上午10:55:59
 */
@Repository("authorityTransmitDao")
public class AuthorityTransmitDaoImpl extends HibernateDaoImpl<AuthorityTransmit, Integer> implements IAuthorityTransmitDao {

	/**
	 * 权限移交的属性查找，且其状态为已经已经移交的状态，非已经撤销的状态
	 */
	@SuppressWarnings("unchecked")
	public List<AuthorityTransmit> findByProperty(String propertyName, Object value){
		String hql = "from AuthorityTransmit at where at.isTransmit=?0 and at." + propertyName + "=?1";
		Query query = createQuery(hql, Integer.valueOf(ConstantStateEnum.YES.getId()),value);
		return query.list();
	}

	/**
	 * 判断是否为接受了移交权限的人；
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AuthorityTransmit> isTransmited(Integer acceptUserId) {
		String hql = "from AuthorityTransmit at where at.isTransmit=?0 and at.acceptUserId=?1";
		Query query = createQuery(hql, Integer.valueOf(ConstantStateEnum.YES.getId()),acceptUserId);
		return query.list();
	}
}
