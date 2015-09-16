package com.agama.pemm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.domain.GitInfo;
@Repository
public class GitInfoDaoImpl extends HibernateDaoImpl<GitInfo, Integer> implements
		IGitInfoDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql=new StringBuffer("update GitInfo set status=1 where id in (").append(ids).append(")");
		this.getSession().createQuery(hql.toString()).executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GitInfo> findListByStatus(Integer status) {
		StringBuffer hql=new StringBuffer(" from GitInfo where status=").append(status);
		return this.getSession().createQuery(hql.toString()).list();
	}

	

	

	

}
