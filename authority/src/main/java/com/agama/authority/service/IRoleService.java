package com.agama.authority.service;

import com.agama.authority.entity.Role;
import com.agama.common.service.IBaseService;

/**
 * @Description:角色service
 * @Author:scuta
 * @Since :2015年8月27日 上午10:29:50
 */
public interface IRoleService extends IBaseService<Role, Integer> {

	public Role getRoleByName(String name);
	
	public Role getRoleByCode(String roleCode);
	
	/**
	 * @Description:提供支持回收站的逻辑删除操作；
	 * @param id
	 * @param opUserId
	 * @Since :2016年2月25日 下午5:06:42
	 */
	public void delete(Integer id, Integer opUserId);
	
}
