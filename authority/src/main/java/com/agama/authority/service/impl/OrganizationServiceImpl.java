package com.agama.authority.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IOrganizationDao;
import com.agama.authority.entity.Organization;
import com.agama.authority.service.IOrganizationService;
import com.agama.common.dao.IRecycleDao;
import com.agama.common.dao.utils.EntityUtils;
import com.agama.common.domain.TreeBean;
import com.agama.common.entity.Recycle;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.RecycleEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * @Description:机构service具体实现
 * @Author:scuta
 * @Since :2015年8月27日 上午10:09:47
 */
@Service("organizationService")
@Transactional(readOnly = true)
public class OrganizationServiceImpl extends BaseServiceImpl<Organization, Integer> implements IOrganizationService {

	@Autowired
	private IOrganizationDao organizationDao;

	@Autowired
	private IRecycleDao recycleDao;

	@Autowired
	private EntityUtils entityUtils;

	/**
	 * 根据机构的父id查找对应的父id下的所有子机构；
	 */
	@Override
	public List<TreeBean> getTreeByPid(Integer pid) {
		List<Organization> organizations = organizationDao.findListByPid(pid);
		List<TreeBean> treeBeans = new ArrayList<TreeBean>();
		for (Organization organization : organizations) {
			TreeBean treeBean = new TreeBean();
			treeBean.setId(organization.getId());
			treeBean.setText(organization.getOrgName());
			if (organizationDao.find(Restrictions.eq("pid", organization.getId())).size() > 0) {
				treeBean.setState("closed");
			} else {
				treeBean.setState("open");
			}
			treeBeans.add(treeBean);
		}
		return treeBeans;
	}

	/**
	 * @Description:递归调用，根据pid查找所有的orgs；
	 * @param pid
	 * @return
	 * @Since :2016年2月25日 下午2:31:53
	 */
	private String recursiveByPid(Integer pid) {
		StringBuffer pidStr = new StringBuffer();
		List<Organization> organizations = organizationDao.findListByPid(pid);
		for (Organization organization : organizations) {
			pidStr.append(",").append(organization.getId());
			pidStr.append(recursiveByPid(organization.getId()));

		}
		return pidStr.toString();
	}

	/**
	 * 根据id查找所有此id及其包含的子机构；递归调用；
	 */
	@Override
	public String getOrganizationIdStrById(Integer id) {
		String idStr = null;
		if (id != null) {
			idStr = id + recursiveByPid(id);
		}
		return idStr;
	}

	/**
	 * 根据gitIds查找机构；
	 */
	@Override
	public List<Organization> getOrganizationListByGitIds(String gitIds) {
		StringBuffer hql = new StringBuffer("select o from Organization o,GitInfo g where g.organizationId=o.id ");
		hql.append(" and o.enable='" + EnabledStateEnum.ENABLED + "' and o.status='" + StatusEnum.NORMAL + "' ");// 加入对状态与可用性的的判断；
		if (gitIds != null) {
			hql.append(" and g.id in (").append(gitIds).append(")");
		}
		hql.append("group by o.id");
		return organizationDao.find(hql.toString());
	}

	/**
	 * 根据orgCode查找机构
	 */
	@Override
	public Organization getOrganizationByOrgCode(String orgCode) {
		// String hql = "from Organization where orgCode = '" + orgCode + "'";
		StringBuffer hql = new StringBuffer("from Organization o where orgCode = '" + orgCode + "'");
		hql.append(" and o.enable='" + EnabledStateEnum.ENABLED + "' and o.status='" + StatusEnum.NORMAL + "' ");// 加入对状态与可用性的的判断；
		List<Organization> list = organizationDao.find(hql.toString());
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 添加支持回收站的逻辑删除；
	 */
	@Transactional(readOnly = false)
	@Override
	public void delete(Integer id, Integer opUserId) {
		/**
		 * 删除操作的步骤： 
		 * 一.组合要删除记录的记录信息，进行保存，即保存删除记录到Recycle 
		 * 二.修改当前实体删除的逻辑状态
		 * 三.修改列表查询中筛选的条件，将逻辑删除的记录排除掉；
		 */
		List<Organization> list = recursiveOrganizationsByPid(id);
		list.add(0, organizationDao.find(id));//要包含当前的级别；
		List<Integer> ids = new ArrayList<Integer>();
		if(list.size() > 0){
			for(Organization organization : list){
				organization.setStatus(StatusEnum.DELETED);// 此处要确认是使用Id还是name，还是使用string本身
				organizationDao.update(organization);
				ids.add(organization.getId());
			}
			Recycle recycle = new Recycle();
			recycle.setContent(ids.toString());
			recycle.setIsRecovery(RecycleEnum.NO);
			recycle.setOpTime(new Date());
			recycle.setOpUserId(opUserId);
			recycle.setTableName(entityUtils.getTableNameByEntity(Organization.class.getName()));
			recycle.setTableRecordId(entityUtils.getIdNameByEntityName(Organization.class.getName()));
			recycleDao.save(recycle);
		}
	}

	/**
	 * @Description:递归调用，根据pid查找所有的组织机构Organization；
	 * @param pid
	 * @return
	 * @Since :2016年2月26日 下午1:31:39
	 */
	private List<Organization> recursiveOrganizationsByPid(Integer pid) {
		List<Organization> list = new ArrayList<Organization>();
		List<Organization> organizations = organizationDao.findListByPid(pid);
		for (Organization organization : organizations) {
			list.add(organization);
			list.addAll(recursiveOrganizationsByPid(organization.getId()));
		}
		return list;
	}
}
