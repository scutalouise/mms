package com.agama.authority.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.system.dao.IRoleDao;
import com.agama.authority.system.entity.Role;
import com.agama.authority.system.service.IRoleService;
import com.agama.common.service.impl.BaseServiceImpl;

 /**
 * @Description:角色service
 * @Author:scuta
 * @Since :2015年8月27日 上午10:30:48
 */
@Service("roleService")
@Transactional(readOnly = true)
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer> implements IRoleService{
	
	@Autowired
	private IRoleDao roleDao;
	
	public Role getRoleByName(String name){
		return roleDao.findUniqueBy("name", name);
	}

	@Override
	public Role getRoleByCode(String roleCode) {
		return roleDao.findUniqueBy("roleCode", roleCode);
	}

}
