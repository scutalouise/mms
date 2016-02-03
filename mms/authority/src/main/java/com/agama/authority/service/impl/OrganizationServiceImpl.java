package com.agama.authority.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IOrganizationDao;
import com.agama.authority.entity.AreaInfo;
import com.agama.authority.entity.Organization;
import com.agama.authority.service.IOrganizationService;
import com.agama.common.domain.TreeBean;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * @Description:区域service实现
 * @Author:scuta
 * @Since :2015年8月27日 上午10:09:47
 */
@Service("organizationService")
@Transactional(readOnly=true)
public class OrganizationServiceImpl extends BaseServiceImpl<Organization, Integer> implements IOrganizationService{
	
	@Autowired
	private IOrganizationDao organizationDao;

	@Override
	public List<TreeBean> getTreeByPid(Integer pid) {
		List<Organization> organizations=organizationDao.findListByPid(pid);
		List<TreeBean> treeBeans=new ArrayList<TreeBean>();
		for (Organization organization : organizations) {
			TreeBean treeBean = new TreeBean();
			treeBean.setId(organization.getId());
			treeBean.setText(organization.getOrgName());
			if (organizationDao.find(Restrictions.eq("pid", organization.getId()))
					.size() > 0) {
				treeBean.setState("closed");
			} else {
				treeBean.setState("open");
			}
			treeBeans.add(treeBean);
		}
		return treeBeans;
	}
	private String recursiveByPid(Integer pid) {
		StringBuffer pidStr = new StringBuffer();
		List<Organization> organizations = organizationDao.findListByPid(pid);
		for (Organization organization : organizations) {
			pidStr.append(",").append(organization.getId());
			pidStr.append(recursiveByPid(organization.getId()));

		}

		return pidStr.toString();
	}
	@Override
	public String getOrganizationIdStrById(Integer id) {
		String idStr = null;
		if (id != null) {
			idStr = id + recursiveByPid(id);
		}
		return idStr;
	}
	@Override
	public List<Organization> getOrganizationListByGitIds(String gitIds) {
		StringBuffer hql=new StringBuffer("select o from Organization o,GitInfo g where g.organizationId=o.id ");
		if(gitIds!=null){
			hql.append(" and g.id in (").append(gitIds).append(")");
		}
		hql.append("group by o.id");
		return organizationDao.find(hql.toString());
	}
	@Override
	public Organization getOrganizationByOrgCode(String orgCode) {
		String hql = "from Organization where orgCode = '" + orgCode + "'";
		List<Organization> list =  organizationDao.find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
}
