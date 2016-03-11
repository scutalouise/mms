package com.agama.device.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.ISupplyMaintainOrgDao;
import com.agama.device.domain.SupplyMaintainOrg;

/**
 * @Description:供应厂商与运维组织机构
 * @Author:杨远高
 * @Since :2016年2月29日 上午11:17:13
 */
@Repository("supplyMaintainOrgDao")
public class SupplyMaintainOrgDaoImpl extends HibernateDaoImpl<SupplyMaintainOrg, Integer> implements ISupplyMaintainOrgDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SupplyMaintainOrg> findListByPid(Integer pid) {
		StringBuffer hql = new StringBuffer("from SupplyMaintainOrg where 1=1");
		hql.append(" and enable='" + EnabledStateEnum.ENABLED + "' and status='" + StatusEnum.NORMAL + "' ");// 加入对状态与可用性的检查；
		if (pid != null) {
			hql.append(" and pid=").append(pid);
		} else {
			hql.append(" and pid is null");
		}
		hql.append(" order by orgCode asc");
		return getSession().createQuery(hql.toString()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SupplyMaintainOrg> findSupplyMaintainOrgsByAreaId(Integer areaId) {
		StringBuffer hql = new StringBuffer("from SupplyMaintainOrg smo ");
		hql.append(" where enable='" + EnabledStateEnum.ENABLED + "' and status='" + StatusEnum.NORMAL + "' ");
		hql.append(" and areaId=?0 ");
		hql.append(" order by orgCode asc");
		return createQuery(hql.toString(), areaId).list();	//根据areaId获取到当前的所有SupplyMaintainOrg；;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getOrgIdList(Integer userId) {
		StringBuffer hql = new StringBuffer("select umo.orgId from UserMaintainOrg umo ");
		hql.append(" where umo.userId=?0 ");
		Query query = createQuery(hql.toString(), userId);
		return query.list();
	}
}
