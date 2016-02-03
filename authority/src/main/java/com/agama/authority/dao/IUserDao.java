package com.agama.authority.dao;

import com.agama.authority.entity.User;
import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;

/**
 * @Description:用户DAO
 * @Author:scuta
 * @Since :2015年8月27日 上午9:43:08
 */
public interface IUserDao extends IBaseDao<User, Integer> {
	
	public Page<User> getUsersByOrganizationId(Page<User> page,Integer organizationId);
}
