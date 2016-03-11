package com.agama.itam.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.itam.dao.IProblemTypeDao;
import com.agama.itam.domain.ProblemType;

/**
 * @Description:问题类型DAO实现类
 * @Author:佘朝军
 * @Since :2016年1月19日 上午9:49:05
 */
@SuppressWarnings("unchecked")
@Repository
public class ProblemTypeDaoImpl extends HibernateDaoImpl<ProblemType, Serializable> implements IProblemTypeDao {

	@Override
	public List<ProblemType> getListByDeviceType(FirstDeviceType deviceType) throws Exception {
		String hql = "from ProblemType where deviceType = '" + deviceType + "' and status = " + StatusEnum.NORMAL.ordinal();
		return (List<ProblemType>)getSession().createQuery(hql).list();
	}

	@Override
	public List<ProblemType> getAllList() throws Exception {
		String hql = "from ProblemType where status = " + StatusEnum.NORMAL.ordinal();
		return (List<ProblemType>)getSession().createQuery(hql).list();
	}

}
