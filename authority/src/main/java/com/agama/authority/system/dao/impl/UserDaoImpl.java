package com.agama.authority.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.authority.system.dao.IUserDao;
import com.agama.authority.system.entity.User;
import com.agama.common.dao.impl.HibernateDaoImpl;

/**
 * @Description:用户DAO实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:43:49
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateDaoImpl<User, Integer> implements IUserDao {

}
