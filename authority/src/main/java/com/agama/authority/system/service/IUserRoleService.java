package com.agama.authority.system.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.system.entity.UserRole;
import com.agama.common.service.IBaseService;

/**
 * @Description:用户角色service
 * @Author:scuta
 * @Since :2015年8月27日 上午10:45:00
 */
public interface IUserRoleService extends IBaseService<UserRole, Integer> {

	/**
	 * 添加修改用户角色
	 * 
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateUserRole(Integer userId, List<Integer> oldList, List<Integer> newList);

	/**
	 * 构建UserRole
	 * 
	 * @param userId
	 * @param roleId
	 * @return UserRole
	 */
	UserRole getUserRole(Integer userId, Integer roleId);

	/**
	 * 获取用户拥有角色id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getRoleIdList(Integer userId);

}
