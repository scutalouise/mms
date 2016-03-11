package com.agama.itam.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.StatusEnum;
import com.agama.itam.dao.IArrangeDao;
import com.agama.itam.domain.Arrange;

/**
 * @Description:运维排程Dao实现类
 * @Author:佘朝军
 * @Since :2016年2月1日 下午3:44:58
 */
@Repository
public class ArrangeDaoImpl extends HibernateDaoImpl<Arrange, Serializable> implements IArrangeDao {
	
	public Arrange findByUserId(Integer userId) {
		String hql = " from Arrange where userId = " + userId + " and status = " + StatusEnum.NORMAL.ordinal();
		return findUnique(hql);
	}
	
	public Arrange findEnableArrangeByUserId(Integer userId) {
		String hql = " from Arrange where userId = " + userId + " and status = " + StatusEnum.NORMAL.ordinal() + " and enable = true";
		return findUnique(hql);
	}

}
