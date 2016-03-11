package com.agama.device.dao;

import java.util.List;

import com.agama.authority.entity.User;
import com.agama.common.dao.IBaseDao;
import com.agama.device.domain.UserAdminOrg;

/**
 * @Description:内部人员对应的负责的机构（均是内部人员与机构）
 * @Author:scuta
 * @Since :2015年8月27日 上午9:44:31
 */
public interface IUserAdminOrgDao extends IBaseDao<UserAdminOrg, Integer> {

	/**
	 * 删除用户机构
	 * 
	 * @param userId
	 * @param orgId
	 */
	public void deleteUserAdminOrg(Integer userId, Integer orgId);
	
	/**
	 * 查询用户拥有的机构id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> findOrgIds(Integer userId);
	
	public List<User> getUserListByOrgId(Integer orgId);

}
