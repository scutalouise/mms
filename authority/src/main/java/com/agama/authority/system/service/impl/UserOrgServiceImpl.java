package com.agama.authority.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.system.dao.IUserOrgDao;
import com.agama.authority.system.entity.UserOrg;
import com.agama.authority.system.service.IUserOrgService;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * 
 * @author kkomge
 * @date 2015年5月9日 
 */
/**
 * @Description:用户机构Service实现
 * @Author:scuta
 * @Since :2015年8月27日 上午10:44:00
 */
@Service("userOrgService")
@Transactional(readOnly = true)
public class UserOrgServiceImpl extends BaseServiceImpl<UserOrg, Integer> implements IUserOrgService{

	@Autowired
	private IUserOrgDao userOrgDao;
	
	/**
	 * 添加修改用户机构
	 * 
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateUserOrg(Integer userId,List<Integer> newList) {
		// 删除老的机构关系
		userOrgDao.deleteUO(userId);
		// 添加新的机构关系
		for (Integer integer : newList) {
			userOrgDao.save(new UserOrg(userId, integer));
		}
	}

	/**
	 * 获取用户拥有机构id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getOrgIdList(Integer userId) {
		return userOrgDao.findOrgIds(userId);
	}

}
