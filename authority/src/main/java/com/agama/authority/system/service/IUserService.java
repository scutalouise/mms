package com.agama.authority.system.service;

import com.agama.authority.system.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.service.IBaseService;

/**
 * @Description:用户service
 * @Author:scuta
 * @Since :2015年8月27日 上午10:47:16
 */
public interface IUserService extends IBaseService<User, Integer> {

	/** 加密方法 */
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	static final int SALT_SIZE = 8; // 盐长度

	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	public void save(User user);

	/**
	 * 修改密码
	 * 
	 * @param user
	 */
	public void updatePwd(User user);

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 按登录名查询用户
	 * 
	 * @param loginName
	 * @return 用户对象
	 */
	public User getUser(String loginName);

	/**
	 * 判断是否超级管理员
	 * 
	 * @param id
	 * @return boolean
	 */
	boolean isSupervisor(Integer id);

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	void entryptPassword(User user);

	/**
	 * 验证原密码是否正确
	 * 
	 * @param user
	 * @param oldPwd
	 * @return
	 */
	public boolean checkPassword(User user, String oldPassword);

	/**
	 * 修改用户登录
	 * 
	 * @param user
	 */
	public void updateUserLogin(User user);

	/**
	 * 根据组织id查找到User的封装page
	 * 
	 * @param orgId
	 * @return
	 */
	public Page<User> getUsersByAreaInfoId(Page<User> page, Integer areaInfoId);

}
