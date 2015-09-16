package com.agama.authority.system.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.agama.authority.system.dao.IUserDao;
import com.agama.authority.system.entity.User;
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
	public Page<User> getUsersAreaInfoId(Page<User> page, Integer areaInfoId) {
		Assert.notNull(page, "page不能为空");
		String hql = "select u from User u ,UserOrg uo,Organization o ,AreaInfo a  " + " WHERE u.id = uo.userId AND uo.orgId = o.id " + " AND a.id = o.areaId AND a.id=?0";
		return findPage(page, hql, areaInfoId);
	}

}
