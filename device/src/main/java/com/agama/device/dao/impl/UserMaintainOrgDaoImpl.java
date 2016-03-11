package com.agama.device.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.authority.entity.User;
import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IUserMaintainOrgDao;
import com.agama.device.domain.UserMaintainOrg;

/**
 * @Description:用户机构Dao实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:45:36
 */
@Repository("userMaintainOrgDao")
public class UserMaintainOrgDaoImpl extends HibernateDaoImpl<UserMaintainOrg, Integer> implements IUserMaintainOrgDao{

	/**
	 * 删除用户机构
	 * @param userId
	 * @param orgId
	 */
	public void deleteUO(Integer userId){
		String hql="delete UserMaintainOrg umo where umo.userId=?0 ";
		batchExecute(hql, userId);
	}
	
	/**
	 * 查询用户拥有的机构id集合
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findOrgIds(Integer userId){
		String hql="select umo.orgId from UserMaintainOrg umo where umo.userId=?0";
		Query query= createQuery(hql, userId);
		return query.list();
	}

	/**
	 * 删除运维人员与机构的关系；
	 */
	@Override
	public void deleteUserMaintainOrg(Integer userId, Integer orgId) {
		String hql="delete UserMaintainOrg umo where umo.userId=?0 and umo.orgId=?1 ";
		Query query = createQuery(hql.toString(), userId,orgId);
		query.executeUpdate();
	}
	
	public List<User> getUserListByOrgId(Integer orgId) {
		String hql = "select u from UserMaintainOrg umo, User u where u.id = umo.userId and umo.orgId = " + orgId 
				+ " and u.status = '" + StatusEnum.NORMAL.toString() + "' ";
		return this.find(hql);
	}
	
}
