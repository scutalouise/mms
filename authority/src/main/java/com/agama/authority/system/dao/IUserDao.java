package com.agama.authority.system.dao;

import com.agama.authority.system.entity.User;
import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;

/**
 * @Description:用户DAO
 * @Author:scuta
 * @Since :2015年8月27日 上午9:43:08
 */
public interface IUserDao extends IBaseDao<User, Integer> {
	
	public Page<User> getUsersAreaInfoId(Page<User> page,Integer areaInfoId);
}
