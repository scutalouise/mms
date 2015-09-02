package com.agama.authority.system.service;

import java.util.List;

import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.system.entity.RolePermission;
import com.agama.common.service.IBaseService;

/**
 * @Description:角色权限service
 * @Author:scuta
 * @Since :2015年8月27日 上午10:33:22
 */
public interface IRolePermissionService extends IBaseService<RolePermission, Integer> {
	
	/**
	 * 获取角色权限id集合
	 * @param id
	 * @return List
	 */
	public List<Integer> getPermissionIds(Integer roleId);
	
	/**
	 * 修改角色权限
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateRolePermission(Integer id,List<Integer> oldList,List<Integer> newList);
	
	/**
	 * 清空该角色用户的权限缓存
	 */
	public void clearUserPermCache(PrincipalCollection pc);
	
	/**
	 * 构造角色权限对象
	 * @param roleId
	 * @param permissionId
	 * @return RolePermission
	 */
	RolePermission getRolePermission(Integer roleId,Integer permissionId);
	
}
