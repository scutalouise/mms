package com.agama.authority.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.entity.UserRole;
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
	
	/**
	 * @Description:权限的移交
	 * @param userId
	 * @param acceptUserId
	 * @Since :2015年12月30日 上午11:34:30
	 */
	public int transmitRole(Integer userId, Integer acceptUserId);
	
	/**
	 * @Description:撤销权限的移交
	 * @param userId
	 * @return Integer:返回被撤销用户的id
	 * @Since :2015年12月30日 下午2:53:55
	 */
	public Integer cancelTransmitRole(Integer userId);
	

}
