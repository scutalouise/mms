package com.agama.device.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IUserDao;
import com.agama.authority.entity.User;
import com.agama.common.dao.IRecycleDao;
import com.agama.common.dao.utils.EntityUtils;
import com.agama.common.domain.TreeBean;
import com.agama.common.entity.Recycle;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.RecycleEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.ISupplyMaintainOrgDao;
import com.agama.device.dao.IUserMaintainOrgDao;
import com.agama.device.domain.SupplyMaintainOrg;
import com.agama.device.domain.UserMaintainOrg;
import com.agama.device.service.ISupplyMaintainOrgService;

/**
 * @Description:运维机构service具体实现
 * @Author:scuta
 * @Since :2015年8月27日 上午10:09:47
 */
@Service("supplyMaintainOrgService")
@Transactional(readOnly = true)
public class SupplyMaintainOrgServiceImpl extends BaseServiceImpl<SupplyMaintainOrg, Integer> implements ISupplyMaintainOrgService {

	@Autowired
	private ISupplyMaintainOrgDao supplyMaintainOrgDao;
	
	@Autowired
	private IUserMaintainOrgDao userMaintainOrgDao;

	@Autowired
	private IRecycleDao recycleDao;

	@Autowired
	private EntityUtils entityUtils;
	
	@Autowired
	private IUserDao userDao;

	/**
	 * 根据机构的父id查找对应的父id下的所有子机构；
	 */
	@Override
	public List<TreeBean> getTreeByPid(Integer pid) {
		List<SupplyMaintainOrg> supplyMaintainOrgs = supplyMaintainOrgDao.findListByPid(pid);
		List<TreeBean> treeBeans = new ArrayList<TreeBean>();
		for (SupplyMaintainOrg supplyMaintainOrg : supplyMaintainOrgs) {
			TreeBean treeBean = new TreeBean();
			treeBean.setId(supplyMaintainOrg.getId());
			treeBean.setText(supplyMaintainOrg.getOrgName());
			if (supplyMaintainOrgDao.find(Restrictions.eq("pid", supplyMaintainOrg.getId())).size() > 0) {
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
		List<SupplyMaintainOrg> supplyMaintainOrgs = supplyMaintainOrgDao.findListByPid(pid);
		for (SupplyMaintainOrg supplyMaintainOrg : supplyMaintainOrgs) {
			pidStr.append(",").append(supplyMaintainOrg.getId());
			pidStr.append(recursiveByPid(supplyMaintainOrg.getId()));

		}
		return pidStr.toString();
	}

	/**
	 * 根据id查找所有此id及其包含的子机构；递归调用；
	 */
	@Override
	public String getSupplyMaintainOrgIdStrById(Integer id) {
		String idStr = null;
		if (id != null) {
			idStr = id + recursiveByPid(id);
		}
		return idStr;
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
		List<SupplyMaintainOrg> list = recursiveSupplyMaintainOrgsByPid(id);
		list.add(0, supplyMaintainOrgDao.find(id));//要包含当前的级别；
		List<Integer> ids = new ArrayList<Integer>();
		if(list.size() > 0){
			for(SupplyMaintainOrg supplyMaintainOrg : list){
				supplyMaintainOrg.setStatus(StatusEnum.DELETED);// 此处要确认是使用Id还是name，还是使用string本身
				supplyMaintainOrgDao.update(supplyMaintainOrg);
				ids.add(id);
			}
			Recycle recycle = new Recycle();
			recycle.setContent(ids.toString());
			recycle.setIsRecovery(RecycleEnum.NO);
			recycle.setOpTime(new Date());
			recycle.setOpUserId(opUserId);
			recycle.setTableName(entityUtils.getTableNameByEntity(SupplyMaintainOrg.class.getName()));
			recycle.setTableRecordId(entityUtils.getIdNameByEntityName(SupplyMaintainOrg.class.getName()));
			recycleDao.save(recycle);
		}
	}

	/**
	 * @Description:递归调用，根据pid查找所有的组织机构SupplyMaintainOrg；
	 * @param pid
	 * @return
	 * @Since :2016年2月26日 下午1:31:39
	 */
	private List<SupplyMaintainOrg> recursiveSupplyMaintainOrgsByPid(Integer pid) {
		List<SupplyMaintainOrg> list = new ArrayList<SupplyMaintainOrg>();
		List<SupplyMaintainOrg> supplyMaintainOrgs = supplyMaintainOrgDao.findListByPid(pid);
		for (SupplyMaintainOrg supplyMaintainOrg : supplyMaintainOrgs) {
			list.add(supplyMaintainOrg);
			list.addAll(recursiveSupplyMaintainOrgsByPid(supplyMaintainOrg.getId()));
		}
		return list;
	}

	@Override
	public List<Integer> getOrgIdList(Integer userId) {
		return supplyMaintainOrgDao.getOrgIdList(userId);
	}

	
	@Transactional(readOnly = false)
	@Override
	public void updateUserOrg(Integer userId, List<Integer> newList) {
		// 删除老的机构关系
		userMaintainOrgDao.deleteUO(userId);
		// 添加新的机构关系
		for (Integer integer : newList) {
			userMaintainOrgDao.save(new UserMaintainOrg(userId, integer));
		}
	} 

	@Override
	public List<SupplyMaintainOrg> getSupplyMaintainOrgListByBrandId(Integer brandId) {
		String hql="from SupplyMaintainOrg s where s.id in (select supplyMaintainOrgId from SupplyMaintainBrand where brandId=?0) "
				+ "and s.status=?1 and s.enable=?2";
		return supplyMaintainOrgDao.find(hql, brandId,StatusEnum.NORMAL,EnabledStateEnum.ENABLED);
	}

	@Override
	public List<User> getUserListByOrgId(Integer orgId) {
		String hql = "select u from User u, UserMaintainOrg umo where umo.orgId = " + orgId + " and u.id = umo.userId";
		return userDao.find(hql);
	}
}
