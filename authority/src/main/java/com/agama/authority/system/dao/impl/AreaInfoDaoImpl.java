package com.agama.authority.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.authority.system.dao.IAreaInfoDao;
import com.agama.authority.system.entity.AreaInfo;
import com.agama.common.dao.impl.HibernateDaoImpl;

/**
 * @Description:区域Dao实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:31:16
 */
@Repository("areaInfoDao")
public class AreaInfoDaoImpl extends HibernateDaoImpl<AreaInfo, Integer> implements IAreaInfoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AreaInfo> findListByPid(Integer pid) {
		StringBuffer hql=new StringBuffer("from AreaInfo where 1=1");
		if(pid!=null){
			hql.append(" and pid=").append(pid);
		}else{
			hql.append(" and pid is null");
		}
		hql.append(" order by areaCode asc");
		return getSession().createQuery(hql.toString()).list();
	}

}
