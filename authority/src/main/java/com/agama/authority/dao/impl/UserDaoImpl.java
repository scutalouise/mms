package com.agama.authority.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.agama.authority.dao.IUserDao;
import com.agama.authority.entity.User;
import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;

/**
 * @Description:用户DAO实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:43:49
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateDaoImpl<User, Integer>implements IUserDao {

	@Override
	public Page<User> getUsersByOrganizationId(Page<User> page, Integer organizationId) {
		Assert.notNull(page, "page不能为空");
		String hql = "select u from User u ,UserOrg uo,Organization o " + " WHERE u.id = uo.userId AND uo.orgId = o.id AND o.id =?0";
		return findPage(page, hql, organizationId);
	}

}
