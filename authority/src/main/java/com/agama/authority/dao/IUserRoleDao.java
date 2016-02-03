package com.agama.authority.dao;

import java.util.List;

import com.agama.authority.entity.User;
import com.agama.authority.entity.UserRole;
import com.agama.common.dao.IBaseDao;

/**
 * @Description:用户角色DAO
 * @Author:scuta
 * @Since :2015年8月27日 上午9:46:28
 */
public interface IUserRoleDao extends IBaseDao<UserRole, Integer> {

	/**
	 * 删除用户角色
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void deleteUR(Integer userId, Integer roleId);

	/**
	 * 查询用户拥有的角色id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> findRoleIds(Integer userId);
	
	/**
	 * 查询角色下所有的用户
	 * 
	 * @param roleId
	 * @return 结果集合
	 */
	public List<User> findUserList(Integer roleId);

}
