package com.agama.authority.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IAuthorityTransmitDao;
import com.agama.authority.dao.IUserRoleDao;
import com.agama.authority.entity.AuthorityTransmit;
import com.agama.authority.entity.Role;
import com.agama.authority.entity.User;
import com.agama.authority.entity.UserRole;
import com.agama.authority.service.IUserRoleService;
import com.agama.common.enumbean.ConstantStateEnum;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * @Description:用户角色service实现
 * @Author:scuta
 * @Since :2015年8月27日 上午10:46:19
 */
@Service("userRoleService")
@Transactional(readOnly = true)
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, Integer> implements IUserRoleService{

	@Autowired
	private IUserRoleDao userRoleDao;
	
	@Autowired
	private IAuthorityTransmitDao authorityTransmitDao;
	
	/**
	 * 添加修改用户角色
	 * 
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateUserRole(Integer userId, List<Integer> oldList,List<Integer> newList) {
		// 是否删除
		for (int i = 0, j = oldList.size(); i < j; i++) {
			if (!newList.contains(oldList.get(i))) {
				userRoleDao.deleteUR(userId, oldList.get(i));
			}
		}

		// 是否添加
		for (int m = 0, n = newList.size(); m < n; m++) {
			if (!oldList.contains(newList.get(m))) {
				userRoleDao.save(getUserRole(userId, newList.get(m)));
			}
		}
	}

	/**
	 * 构建UserRole
	 * 
	 * @param userId
	 * @param roleId
	 * @return UserRole
	 */
	public UserRole getUserRole(Integer userId, Integer roleId) {
		UserRole ur = new UserRole();
		ur.setUser(new User(userId));
		ur.setRole(new Role(roleId));
		return ur;
	}

	/**
	 * 获取用户拥有角色id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getRoleIdList(Integer userId) {
		return userRoleDao.findRoleIds(userId);
	}

	/**
	 * 权限移交的时候，将移交人的角色都移交给接受人，并且记录到权限移交表中；
	 * @param userId
	 * @param acceptUserId
	 */
	@Transactional(readOnly=false)
	public int transmitRole(Integer userId, Integer acceptUserId) {
		/**
		 * 目的：增加修改接收人的角色：角色取并集
		 * 处理过程：
		 * 1.判断是否为接收了权限移交的人，如果是,那么要排除接受了别人移交过来的，自己没有的权限；
		 * 2.组合需要新增的角色，准备将这些角色，新增给需要接受移交的人；
		 * 实例：甲、乙、丙三人；若甲本身拥有a,b,c角色，甲移交权限给了乙；乙有a,b,c角色，其中，a,c角色为甲移交权限所得，即乙接收了权限移交，未将权限移交给别人，乙本身只拥有b角色；丙本身拥有a角色，丙未接收也未移交权限给别人；
		 * 操作：
		 * 甲不允许移交权限给乙，不可以，权限移交只能进行一次，需要再次移交需要先收回，即先撤销已经移交的权限，再移交权限给第三人；
		 * 乙移交权限给丙，可以，丙只能接收到乙的b角色；
		 * 丙可以移交权限给别人，但不能移交给权限比自己还多的人，即丙不可以移交权限给甲，可以移交给乙；
		 */
		//
		//记录移交人都移交了哪些角色；角色存入数据库时格式为:(id1,id2,id3)
		List<Integer> transmitRolesList = new ArrayList<Integer>();
//		StringBuffer sb = new StringBuffer();
		List<Integer> acceptUserOringinalRolesList = new ArrayList<Integer>();//接受权限移交的人，原始状态拥有的角色
		List<AuthorityTransmit> acceptUserATList = authorityTransmitDao.findByProperty("acceptUserId", acceptUserId);//查找下，接受了权限移交的人，他被别人移交过来的角色有哪些；
		if(acceptUserATList.size() > 0){
			acceptUserOringinalRolesList.addAll(getRoleIdList(acceptUserId));
			AuthorityTransmit acceptUserAT = acceptUserATList.get(0);
			for(String role : acceptUserAT.getTransmitRoles().replace("[", "").replace("]", "").split(",")){
				acceptUserOringinalRolesList.remove(Integer.valueOf(role.trim()));
			}
		}else{//没有别人移交过来的角色
			acceptUserOringinalRolesList.addAll(getRoleIdList(acceptUserId));
		}
		
		List<Integer> transmitUserOringinalRolesList = new ArrayList<Integer>();//进行权限移交的人，原始状态拥有的角色
		List<AuthorityTransmit> transmitUserATList = authorityTransmitDao.findByProperty("acceptUserId", userId);//查找下，要进行权限移交的人，他被别人移交过来的角色有哪些；
		if(transmitUserATList.size() > 0){
			transmitUserOringinalRolesList.addAll(getRoleIdList(userId));
			AuthorityTransmit transmitUserAT = transmitUserATList.get(0);
			for(String role : transmitUserAT.getTransmitRoles().replace("[", "").replace("]", "").split(",")){
				transmitUserOringinalRolesList.remove(Integer.valueOf(role.trim()));
			}
		}else{//没有别人移交过来的角色，
			transmitUserOringinalRolesList.addAll(getRoleIdList(userId));
		}
		
		//组合出哪些角色是变化的，且只保存接受移交权限的人没有的角色；
		for(int i=0 ;i<transmitUserOringinalRolesList.size();i++){
			if(!acceptUserOringinalRolesList.contains(transmitUserOringinalRolesList.get(i))){
				transmitRolesList.add(transmitUserOringinalRolesList.get(i));
//				if(i == (transmitUserOringinalRolesList.size() - 1)){
//					sb.append(transmitUserOringinalRolesList.get(i));
//				}else{
//					sb.append(transmitUserOringinalRolesList.get(i) + ",");
//				}
			}
		}
		
		//取并集
		for(int i = 0; i < acceptUserOringinalRolesList.size(); i++){
			if(!transmitUserOringinalRolesList.contains(acceptUserOringinalRolesList.get(i))){
				transmitUserOringinalRolesList.add(acceptUserOringinalRolesList.get(i));
			}
		}
		updateUserRole(acceptUserId, acceptUserOringinalRolesList, transmitUserOringinalRolesList);
		
		if(transmitRolesList.size()>0){
			AuthorityTransmit at = new AuthorityTransmit();
			at.setTransmitUserId(userId);
			at.setAcceptUserId(acceptUserId);
			at.setTransmitRoles(transmitRolesList.toString());
			at.setTransmitTime(new Date());
			at.setIsTransmit(ConstantStateEnum.YES.getId());
			authorityTransmitDao.save(at);
		}
		return transmitRolesList.size() > 0 ? 1 : -1;//有真正意义上的角色移交，返回1，没有返回-1；
	}
	
	/**
	 * @Description:解除权限移交；
	 * @param acceptUserId
	 * @Since :2015年12月30日 下午2:12:20
	 */
	@Transactional(readOnly=false)
	public Integer cancelTransmitRole(Integer userId){
		//查到，移交权限的人，都移交了什么权限给接收的人；
		AuthorityTransmit at = authorityTransmitDao.findByProperty("transmitUserId", userId).get(0);
		String rolesString = at.getTransmitRoles().replace("[", "").replace("]", "");
		ArrayList<Integer> transmitRoles = new ArrayList<Integer>();
		String[] rolesArray = rolesString.split(",");
		for(String role : rolesArray){
			try {
				transmitRoles.add(Integer.parseInt(role.trim()));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//查到当前接受权限移交的人，所拥有的角色id，更改接收移交权限的人的角色为接受权限移交前的情况；
		for(int i=0; i<transmitRoles.size();i++){
			userRoleDao.deleteUR(at.getAcceptUserId(), transmitRoles.get(i));
		}
		//修改移交记录的状态为否：
		at.setIsTransmit(ConstantStateEnum.NO.getId());
		at.setCancelTransmitTime(new Date());
		authorityTransmitDao.update(at);
		return at.getAcceptUserId();
	}

}
