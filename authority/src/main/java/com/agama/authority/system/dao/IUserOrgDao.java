package com.agama.authority.system.dao;

import java.util.List;

import com.agama.authority.system.entity.UserOrg;
import com.agama.common.dao.IBaseDao;

/**
 * @Description:用户机构Dao
 * @Author:scuta
 * @Since :2015年8月27日 上午9:44:31
 */
public interface IUserOrgDao extends IBaseDao<UserOrg, Integer> {

	/**
	 * 删除用户机构
	 * 
	 * @param userId
	 * @param orgId
	 */
	public void deleteUO(Integer userId);

	/**
	 * 查询用户拥有的机构id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> findOrgIds(Integer userId);

}
