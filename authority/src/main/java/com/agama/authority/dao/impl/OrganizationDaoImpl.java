package com.agama.authority.dao.impl;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.agama.authority.dao.IOrganizationDao;
import com.agama.authority.entity.Organization;
import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;

/**
 * @Description:机构DAO实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:35:08
 */
@Repository("organizationDao")
public class OrganizationDaoImpl extends HibernateDaoImpl<Organization, Integer> implements IOrganizationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> findListByPid(Integer pid) {
		StringBuffer hql = new StringBuffer("from Organization where 1=1");
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
	public List<Organization> findListByAreaIdAndStatus(Integer areaId, StateEnum state, String searchValue) {
		String str = " and o.id NOT IN ( SELECT id FROM ( SELECT o.id, d.current_state FROM organization o, git_info g, device d "
				+ "WHERE o.id = g.organization_id AND g.id = d.git_info_id AND g.status = 0 AND d.status = 0 " 
				+ " and o.enable='" + EnabledStateEnum.ENABLED + "' and o.status='" + StatusEnum.NORMAL + "' "//此处加入可用性状态与是否删除的判断；
				+ "GROUP BY o.id, d.current_state " + ") t WHERE ";
		if (state == StateEnum.good) {
			str += "t.current_state = 1 OR t.current_state = 2)";
		} else if (state == StateEnum.warning) {
			str += "t.current_state = 2)";
		} else {
			str = "";
		}

		String hql = "SELECT o.id as id,o.org_name as orgName,o.pid as pid,o.org_type as orgType,o.org_sort as orgSort, o.org_Level as orgLevel,o.org_code as orgCode,o.area_id as areaId,d.current_state as currentState FROM organization o, git_info g, device d WHERE  o.id = g.organization_id AND d.git_info_id = g.id and g.status = 0 AND d.status = 0 AND d.current_state = "
				+ state.ordinal() + str + " and o.area_id=" + areaId + " group by id";
		return this.getSession().createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(Organization.class)).list();
	}

}
