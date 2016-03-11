package com.agama.authority.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IRoleDao;
import com.agama.authority.entity.Role;
import com.agama.authority.service.IRoleService;
import com.agama.common.dao.IRecycleDao;
import com.agama.common.dao.utils.EntityUtils;
import com.agama.common.entity.Recycle;
import com.agama.common.enumbean.RecycleEnum;
import com.agama.common.enumbean.StatusEnum;
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
	
	@Autowired
	private EntityUtils entityUtils;
	
	@Autowired
	private IRecycleDao recycleDao;
	
	public Role getRoleByName(String name){
		return roleDao.findUniqueBy("name", name);
	}

	@Override
	public Role getRoleByCode(String roleCode) {
		return roleDao.findUniqueBy("roleCode", roleCode);
	}

	/**
	 * 提供支持回收站的逻辑删除操作；
	 */
	@Transactional(readOnly = false)
	@Override
	public void delete(Integer id, Integer opUserId) {
		/**
		 * 删除操作的步骤：
		 * 一.组合要删除记录的记录信息，进行保存，即保存删除记录到Recycle
		 * 二.修改当前实体删除的逻辑状态
		 * 三.修改列表查询中筛选的条件，将逻辑删除的记录排除掉；
		 */
		Role role = roleDao.find(id);

		List<Integer> ids = new ArrayList<Integer>();
		ids.add(id);
		Recycle recycle = new Recycle();
		recycle.setContent(ids.toString());
		recycle.setIsRecovery(RecycleEnum.NO);
		recycle.setOpTime(new Date());
		recycle.setOpUserId(opUserId);
		recycle.setTableName(entityUtils.getTableNameByEntity(Role.class.getName()));
		recycle.setTableRecordId(entityUtils.getIdNameByEntityName(Role.class.getName()));
		recycleDao.save(recycle);

		role.setStatus(StatusEnum.DELETED);//此处要确认是使用Id还是name，还是使用string本身
		roleDao.update(role);
	}

}
