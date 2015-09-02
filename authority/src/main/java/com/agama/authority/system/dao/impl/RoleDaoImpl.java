package com.agama.authority.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.authority.system.dao.IRoleDao;
import com.agama.authority.system.entity.Role;
import com.agama.common.dao.impl.HibernateDaoImpl;

/**
 * @Description:角色DAO实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:40:03
 */
@Repository("roleDao")
public class RoleDaoImpl extends HibernateDaoImpl<Role, Integer> implements IRoleDao {

}
