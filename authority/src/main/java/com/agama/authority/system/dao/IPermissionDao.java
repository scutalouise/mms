package com.agama.authority.system.dao;

import java.util.List;

import com.agama.authority.system.entity.Permission;
import com.agama.common.dao.IBaseDao;

/**
 * @Description:权限DAO
 * @Author:scuta
 * @Since :2015年8月27日 上午9:35:57
 */
public interface IPermissionDao extends IBaseDao<Permission, Integer> {

	/**
	 * 查询用户拥有的权限
	 * 
	 * @param userId
	 *            用户id
	 * @return 结果集合
	 */
	public List<Permission> findPermissions(Integer userId);

	/**
	 * 查询所有的菜单
	 * 
	 * @param userId
	 * @return 菜单集合
	 */
	public List<Permission> findMenus();

	/**
	 * 查询用户拥有的菜单权限
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Permission> findMenus(Integer userId);

	/**
	 * 查询菜单下的操作权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<Permission> findMenuOperation(Integer pid);
}
