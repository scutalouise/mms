package com.agama.authority.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IPermissionDao;
import com.agama.authority.entity.Permission;
import com.agama.authority.service.IPermissionService;
import com.agama.common.dao.IRecycleDao;
import com.agama.common.dao.utils.EntityUtils;
import com.agama.common.entity.Recycle;
import com.agama.common.enumbean.RecycleEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * @Description:权限service实现
 * @Author:scuta
 * @Since :2015年8月27日 上午10:26:50
 */
@Service("permissionService")
@Transactional(readOnly=true)
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Integer> implements IPermissionService{
	
	@Autowired
	private IPermissionDao permissionDao;
	
	@Autowired
	private IRecycleDao recycleDao;
	
	@Autowired
	private EntityUtils entityUtils;
	
	/**
	 * 添加菜单基础操作
	 * @param pid 菜单id
	 * @param pName 菜单权限标识名
	 */
	@Transactional(readOnly = false)
	public void addBaseOpe(Integer pid,String pClassName){
		List<Permission> pList=new ArrayList<Permission>();
		pList.add(new Permission(pid, "添加", "O", "", "sys:"+pClassName+":add"));
		pList.add(new Permission(pid, "删除", "O", "", "sys:"+pClassName+":delete"));
		pList.add(new Permission(pid, "修改", "O", "", "sys:"+pClassName+":update"));
		pList.add(new Permission(pid, "查看", "O", "", "sys:"+pClassName+":view"));
		
		//添加没有的基本操作
		List<Permission> existPList=getMenuOperation(pid);
		for(Permission permission:pList){
			boolean exist=false;
			for(Permission existPermission:existPList){
				if(permission.getPermCode().equals(existPermission.getPermCode())){
					exist=true;
					break;
				}else{
					exist=false;
				}
			}
			if(!exist)
				save(permission);
		}
	}
	
	/**
	 * 获取角色拥有的权限集合
	 * @param userId
	 * @return 结果集合
	 */
	public List<Permission> getPermissions(Integer userId,String code){
		return permissionDao.findPermissions(userId,code);
	}
	
	/**
	 * 获取角色拥有的菜单
	 * @param userId
	 * @return 菜单集合
	 */
	public List<Permission> getMenus(Integer userId){
		return permissionDao.findMenus(userId);
	}
	
	/**
	 * 获取所有菜单
	 * @return 菜单集合
	 */
	public List<Permission> getMenus(){
		return permissionDao.findMenus();
	}
	
	/**
	 * 获取菜单下的操作
	 * @param pid
	 * @return 操作集合
	 */
	public List<Permission> getMenuOperation(Integer pid){
		return permissionDao.findMenuOperation(pid);
	}

	/**
	 * 提供支持回收站的逻辑删除； 
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
		List<Permission> list = recursivePermissionsByPid(id);
		list.add(0, permissionDao.find(id));//当前菜单；
		List<Integer> ids = new ArrayList<Integer>();
		if(list.size() > 0){//此处加入判断：当一个页面打开，很久没用，但session没过期，在进行操作前，另一个人已经进行了操作，导致数据不一致；加入判断可以处理此类异常,NULL POINTER Exception;
			for(Permission permission : list){
				permission.setStatus(StatusEnum.DELETED);//此处要确认是使用Id还是name，还是使用string本身
				permissionDao.update(permission);
				ids.add(permission.getId());
			}
			
			Recycle recycle = new Recycle();
			recycle.setContent(ids.toString());
			recycle.setIsRecovery(RecycleEnum.NO);
			recycle.setOpTime(new Date());
			recycle.setOpUserId(opUserId);
			recycle.setTableName(entityUtils.getTableNameByEntity(Permission.class.getName()));
			recycle.setTableRecordId(entityUtils.getIdNameByEntityName(Permission.class.getName()));
			recycleDao.save(recycle);
		}
	}
	
	/**
	 * @Description:根据当前菜单，选择此菜单下所有的操作或者菜单；
	 * @param pid
	 * @return
	 * @Since :2016年2月26日 下午2:11:33
	 */
	private List<Permission> recursivePermissionsByPid(Integer pid){
		List<Permission> list = new ArrayList<Permission>();
		List<Permission> permissions = permissionDao.findAllPermissionsByPid(pid);//所有pid为当前pid值得下一级菜单；
		for(Permission permission:permissions){
			list.add(permission);
			list.addAll(recursivePermissionsByPid(permission.getId()));
		}
		return list;
	}
}
