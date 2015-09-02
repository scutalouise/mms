package com.agama.authority.system.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.authority.system.dao.IUserOrgDao;
import com.agama.authority.system.entity.UserOrg;
import com.agama.common.dao.impl.HibernateDaoImpl;

/**
 * @Description:用户机构Dao实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:45:36
 */
@Repository("userOrgDao")
public class UserOrgDaoImpl extends HibernateDaoImpl<UserOrg, Integer> implements IUserOrgDao{

	/**
	 * 删除用户机构
	 * @param userId
	 * @param orgId
	 */
	public void deleteUO(Integer userId){
		String hql="delete UserOrg ur where ur.userId=?0 ";
		batchExecute(hql, userId);
	}
	
	/**
	 * 查询用户拥有的机构id集合
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findOrgIds(Integer userId){
		String hql="select ur.orgId from UserOrg ur where ur.userId=?0";
		Query query= createQuery(hql, userId);
		return query.list();
	}
	
}
