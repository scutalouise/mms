package com.agama.pemm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.StateEnum;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.task.DataCollectionService;

@Repository
public class GitInfoDaoImpl extends HibernateDaoImpl<GitInfo, Integer>
		implements IGitInfoDao {

	@Override
	public void updateStatusByIds(String ids) {
		StringBuffer hql = new StringBuffer(
				"update GitInfo set status=1 where id in (").append(ids)
				.append(")");
		this.getSession().createQuery(hql.toString()).executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GitInfo> findListByStatus(Integer status) {
		StringBuffer hql = new StringBuffer(" from GitInfo where status=")
				.append(status);
		return this.getSession().createQuery(hql.toString()).list();
	}

	@Override
	public List<GitInfo> findListByOrganizationIdS(String organizationIds) {
		StringBuffer hql = new StringBuffer("from GitInfo where status=0");
		if (organizationIds != null) {
			hql.append(" and organizationId in (").append(organizationIds)
					.append(")");
		}
		hql.append("order by organizationId");
		return this.find(hql.toString());
	}

	@Override
	public void updateCurrentStateById(Integer id, StateEnum stateEnum) {
		
		StringBuffer hql = new StringBuffer("update GitInfo set currentState='").append(stateEnum.name())
				.append("' where id=").append(id);
		this.batchExecute(hql.toString());
		
	}

}
