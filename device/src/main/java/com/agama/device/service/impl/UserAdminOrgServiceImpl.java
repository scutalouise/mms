package com.agama.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.entity.User;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IUserAdminOrgDao;
import com.agama.device.domain.UserAdminOrg;
import com.agama.device.service.IUserAdminOrgService;

/**
 * @Description:用户运维机构Service实现
 * @Author:杨远高
 * @Since :2016年2月29日 上午11:13:52
 */
@Service("userAdminOrgService")
@Transactional(readOnly = true)
public class UserAdminOrgServiceImpl extends BaseServiceImpl<UserAdminOrg, Integer> implements IUserAdminOrgService{

	@Autowired
	private IUserAdminOrgDao userAdminOrgDao;
	
	/**
	 * 添加修改用户机构
	 * 
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	/* (non-Javadoc)
	 * @see com.agama.device.service.IUserMaintainOrgService#updateUserOrg(java.lang.Integer, java.util.List, java.util.List)
	 */
	@Transactional(readOnly = false)
	public void updateUserOrg(Integer userId, List<Integer>oldList, List<Integer> newList) {
		//是否删除
		for(int i=0, j=oldList.size();i<j;i++){
			if(!newList.contains(oldList.get(i))){
				userAdminOrgDao.deleteUserAdminOrg(userId, oldList.get(i));
			}
		}
		//是否新增
		for(int m=0,n=newList.size(); m<n; m++){
			if(!oldList.contains(newList.get(m))){
				UserAdminOrg userAdminOrg = new UserAdminOrg();
				userAdminOrg.setOrgId(newList.get(m));
				userAdminOrg.setUserId(userId);
				userAdminOrgDao.save(userAdminOrg);
			}
		}
	}

	/**
	 * 获取用户拥有机构id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getOrgIdList(Integer userId) {
		return userAdminOrgDao.findOrgIds(userId);
	}

	@Override
	public List<User> getUserListByOrgId(Integer orgId) {
		return userAdminOrgDao.getUserListByOrgId(orgId);
	}

}
