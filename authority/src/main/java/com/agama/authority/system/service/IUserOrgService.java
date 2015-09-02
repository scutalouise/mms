package com.agama.authority.system.service;

import java.util.List;

import com.agama.authority.system.entity.UserOrg;
import com.agama.common.service.IBaseService;

/**
 * @Description:用户机构Service
 * @Author:scuta
 * @Since :2015年8月27日 上午10:42:55
 */
public interface IUserOrgService extends IBaseService<UserOrg, Integer> {

	/**
	 * 添加修改用户机构
	 * 
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	public void updateUserOrg(Integer userId,List<Integer> newList) ;

	/**
	 * 获取用户拥有机构id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getOrgIdList(Integer userId) ;

}
