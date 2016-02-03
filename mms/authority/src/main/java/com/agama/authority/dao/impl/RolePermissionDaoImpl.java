package com.agama.authority.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.authority.dao.IRolePermissionDao;
import com.agama.authority.entity.RolePermission;
import com.agama.common.dao.impl.HibernateDaoImpl;

/**
 * @Description:角色权限DAO
 * @Author:scuta
 * @Since :2015年8月27日 上午9:42:25
 */
@Repository("rolePermissionDao")
public class RolePermissionDaoImpl extends HibernateDaoImpl<RolePermission, Integer> implements IRolePermissionDao {

	/**
	 * 查询角色拥有的权限id
	 * 
	 * @param roleId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findPermissionIds(Integer roleId) {
		String hql = "select rp.permission.id from RolePermission rp where rp.role.id=?0";
		Query query = createQuery(hql, roleId);
		return query.list();
	}

	/**
	 * 删除角色权限
	 * 
	 * @param roleId
	 * @param permissionId
	 */
	public void deleteRP(Integer roleId, Integer permissionId) {
		String hql = "delete RolePermission rp where rp.role.id=?0 and rp.permission.id=?1";
		batchExecute(hql, roleId, permissionId);
	}

}
