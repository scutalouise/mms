package com.agama.authority.system.dao;

import java.util.List;

import com.agama.authority.system.entity.RolePermission;
import com.agama.common.dao.IBaseDao;

/**
 * @Description:角色权限DAO
 * @Author:scuta
 * @Since :2015年8月27日 上午9:41:20
 */
public interface IRolePermissionDao extends IBaseDao<RolePermission, Integer> {

	/**
	 * 查询角色拥有的权限id
	 * 
	 * @param roleId
	 * @return 结果集合
	 */
	public List<Integer> findPermissionIds(Integer roleId);

	/**
	 * 删除角色权限
	 * 
	 * @param roleId
	 * @param permissionId
	 */
	public void deleteRP(Integer roleId, Integer permissionId);

}
