package com.agama.authority.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.authority.dao.IRoleDao;
import com.agama.authority.entity.Role;
import com.agama.common.dao.impl.HibernateDaoImpl;

/**
 * @Description:角色DAO实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:40:03
 */
@Repository("roleDao")
public class RoleDaoImpl extends HibernateDaoImpl<Role, Integer> implements IRoleDao {

}
