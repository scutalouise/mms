package com.agama.authority.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.system.dao.IUserRoleDao;
import com.agama.authority.system.entity.Role;
import com.agama.authority.system.entity.User;
import com.agama.authority.system.entity.UserRole;
import com.agama.authority.system.service.IUserRoleService;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * @Description:用户角色service实现
 * @Author:scuta
 * @Since :2015年8月27日 上午10:46:19
 */
@Service("userRoleService")
@Transactional(readOnly = true)
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, Integer> implements IUserRoleService{

	@Autowired
	private IUserRoleDao userRoleDao;
	
	/**
	 * 添加修改用户角色
	 * 
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateUserRole(Integer userId, List<Integer> oldList,List<Integer> newList) {
		// 是否删除
		for (int i = 0, j = oldList.size(); i < j; i++) {
			if (!newList.contains(oldList.get(i))) {
				userRoleDao.deleteUR(userId, oldList.get(i));
			}
		}

		// 是否添加
		for (int m = 0, n = newList.size(); m < n; m++) {
			if (!oldList.contains(newList.get(m))) {
				userRoleDao.save(getUserRole(userId, newList.get(m)));
			}
		}
	}

	/**
	 * 构建UserRole
	 * 
	 * @param userId
	 * @param roleId
	 * @return UserRole
	 */
	public UserRole getUserRole(Integer userId, Integer roleId) {
		UserRole ur = new UserRole();
		ur.setUser(new User(userId));
		ur.setRole(new Role(roleId));
		return ur;
	}

	/**
	 * 获取用户拥有角色id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getRoleIdList(Integer userId) {
		return userRoleDao.findRoleIds(userId);
	}

}
