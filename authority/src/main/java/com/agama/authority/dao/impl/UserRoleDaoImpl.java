package com.agama.authority.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.authority.dao.IUserRoleDao;
import com.agama.authority.entity.User;
import com.agama.authority.entity.UserRole;
import com.agama.common.dao.impl.HibernateDaoImpl;

/**
 * @Description:用户角色DAO实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:47:18
 */
@Repository("userRoleDao")
public class UserRoleDaoImpl extends HibernateDaoImpl<UserRole, Integer> implements IUserRoleDao {

	/**
	 * 删除用户角色
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void deleteUR(Integer userId, Integer roleId) {
		String hql = "delete UserRole ur where ur.user.id=?0 and ur.role.id=?1";
		batchExecute(hql, userId, roleId);
	}

	/**
	 * 查询用户拥有的角色id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findRoleIds(Integer userId) {
		String hql = "select ur.role.id from UserRole ur where ur.user.id=?0";
		Query query = createQuery(hql, userId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<User> findUserList(Integer roleId) {
		String hql = "select ur.user from UserRole ur where ur.role.id=?0";
		Query query = createQuery(hql, roleId);
		return query.list();
	}

}
