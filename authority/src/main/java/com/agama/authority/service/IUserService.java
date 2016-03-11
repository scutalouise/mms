package com.agama.authority.service;

import java.util.List;

import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.InternalEnum;
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
	 * 根据组织id查找到User的封装page,加入对内外部人员的排查；
	 * 
	 * @param orgId
	 * @return
	 */
	public Page<User> getUsersByOrganizationId(Page<User> page, Integer organizationId,InternalEnum internal);
	
	/**
	 * @Description:根据组织id查找到User的封装page
	 * @param page
	 * @param organizationId
	 * @return
	 * @Since :2016年3月1日 上午11:48:50
	 */
	public Page<User> getUsersByOrganizationId(Page<User> page, Integer organizationId);
	
	/**
	 * @Description:根据sql语句返回Page
	 * @param page
	 * @param sql
	 * @param values
	 * @return
	 * @Since :2016年3月1日 上午11:21:33
	 */
	public Page<User> findPage(final Page<User> page,InternalEnum internal,List<PropertyFilter> filters) ;

	/**
	 * @Description 根据运维人员id获得电话
	 * @param id
	 * @return
	 * @Since 2016年1月26日 下午2:20:26
	 */
	public String getPhoneNumberById(Integer id);
	
	/**
	 * @Description:提供支持逻辑删除与回收站功能的删除操作；
	 * @param id
	 * @param opUserId
	 * @Since :2016年2月25日 下午4:45:47
	 */
	public void delete(Integer id, Integer opUserId);
	
	/**
	 * @Description 获取所用状态没有被逻辑删除的用户
	 * @return List
	 * @Since :2016年3月4日 上午11:43:20
	 */
	public List<User> getAllList();

}
