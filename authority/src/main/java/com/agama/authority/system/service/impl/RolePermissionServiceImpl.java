package com.agama.authority.system.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.system.dao.IRolePermissionDao;
import com.agama.authority.system.entity.Permission;
import com.agama.authority.system.entity.Role;
import com.agama.authority.system.entity.RolePermission;
import com.agama.authority.system.service.IRolePermissionService;
import com.agama.authority.system.service.UserRealm;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * @Description:角色权限service
 * @Author:scuta
 * @Since :2015年8月27日 上午10:34:48
 */
@Service("rolePermissionService")
@Transactional(readOnly=true)
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission, Integer> implements IRolePermissionService {
	
	@Autowired
	private IRolePermissionDao rolePermissionDao;
	
	/**
	 * 获取角色权限id集合
	 * @param id
	 * @return List
	 */
	public List<Integer> getPermissionIds(Integer roleId){
		return rolePermissionDao.findPermissionIds(roleId);
	}
	
	/**
	 * 修改角色权限
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateRolePermission(Integer id,List<Integer> oldList,List<Integer> newList){
		//是否删除
		for(int i=0,j=oldList.size();i<j;i++){
			if(!newList.contains(oldList.get(i))){
				rolePermissionDao.deleteRP(id,oldList.get(i));
			}
		}
		
		//是否添加
		for(int i=0,j=newList.size();i<j;i++){
			if(!oldList.contains(newList.get(i))){
				rolePermissionDao.save(getRolePermission(id,newList.get(i)));
			}
		}
	}
	
	/**
	 * 清空该角色用户的权限缓存
	 */
	public void clearUserPermCache(PrincipalCollection pc){
		RealmSecurityManager securityManager =  (RealmSecurityManager) SecurityUtils.getSecurityManager();
		UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
	    userRealm.clearCachedAuthorizationInfo(pc);
	}
	
	/**
	 * 构造角色权限对象
	 * @param roleId
	 * @param permissionId
	 * @return RolePermission
	 */
	public RolePermission getRolePermission(Integer roleId,Integer permissionId){
		RolePermission rp=new RolePermission();
		rp.setRole(new Role(roleId));
		rp.setPermission(new Permission(permissionId));
		return rp;
	}
	
}
