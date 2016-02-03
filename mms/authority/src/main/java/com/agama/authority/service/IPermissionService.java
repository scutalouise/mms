package com.agama.authority.service;

import java.util.List;

import com.agama.authority.entity.Permission;
import com.agama.common.service.IBaseService;

/**
 * @Description:权限service
 * @Author:scuta
 * @Since :2015年8月27日 上午10:24:51
 */
public interface IPermissionService extends IBaseService<Permission, Integer>{
	
	/**
	 * 添加菜单基础操作
	 * @param pid 菜单id
	 * @param pName 菜单权限标识名
	 */
	public void addBaseOpe(Integer pid,String pClassName);
	
	/**
	 * 获取角色拥有的权限集合
	 * @param userId
	 * @param code
	 * @return 结果集合
	 */
	public List<Permission> getPermissions(Integer userId,String code);
	
	/**
	 * 获取角色拥有的菜单
	 * @param userId
	 * @return 菜单集合
	 */
	public List<Permission> getMenus(Integer userId);
	
	/**
	 * 获取所有菜单
	 * @return 菜单集合
	 */
	public List<Permission> getMenus();
	
	/**
	 * 获取菜单下的操作
	 * @param pid
	 * @return 操作集合
	 */
	public List<Permission> getMenuOperation(Integer pid);

}
