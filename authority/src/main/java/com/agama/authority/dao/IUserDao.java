package com.agama.authority.dao;

import java.util.List;

import com.agama.authority.entity.User;
import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.InternalEnum;

/**
 * @Description:用户DAO
 * @Author:scuta
 * @Since :2015年8月27日 上午9:43:08
 */
public interface IUserDao extends IBaseDao<User, Integer> {
	
	public Page<User> getUsersByOrganizationId(Page<User> page,Integer organizationId,InternalEnum internal);
	
	public Page<User> getUsersByOrganizationId(Page<User> page,Integer organizationId);
	
	public List<User> getAllList();

}
