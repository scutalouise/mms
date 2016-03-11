package com.agama.device.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.authority.entity.User;
import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IUserAdminOrgDao;
import com.agama.device.domain.UserAdminOrg;

/**
 * @Description：内部用户负责的机构Dao实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:45:36
 */
@Repository("userAdminOrgDao")
public class UserAdminOrgDaoImpl extends HibernateDaoImpl<UserAdminOrg, Integer> implements IUserAdminOrgDao{

	/**
	 * 删除用户机构
	 * @param userId
	 * @param orgId
	 */
	public void deleteUserAdminOrg(Integer userId,Integer orgId){
		String hql="delete UserAdminOrg uao where uao.userId=?0 and uao.orgId=?1 ";
		batchExecute(hql, userId,orgId);
	}
	
	/**
	 * 查询用户拥有的机构id集合
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findOrgIds(Integer userId){
		String hql="select uao.orgId from UserAdminOrg uao where uao.userId=?0";
		Query query= createQuery(hql, userId);
		return query.list();
	}

	@Override
	public List<User> getUserListByOrgId(Integer orgId) {
		String hql = "select u from UserAdminOrg uao, User u where u.id = uao.userId and uao.orgId = " + orgId 
				+ " and u.status = '" + StatusEnum.NORMAL.toString() + "' ";
		return this.find(hql);
	}

}
