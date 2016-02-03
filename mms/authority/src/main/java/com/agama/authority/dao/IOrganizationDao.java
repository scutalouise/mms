package com.agama.authority.dao;

import java.util.List;

import com.agama.authority.entity.Organization;
import com.agama.common.dao.IBaseDao;
import com.agama.common.domain.StateEnum;

/**
 * @Description:机构DAO
 * @Author:scuta
 * @Since :2015年8月27日 上午9:34:06
 */
public interface IOrganizationDao extends IBaseDao<Organization, Integer> {
	/**
	 * @Description: 根据pid获取机构集合
	 * @param pid
	 * @return
	 * @Since :2015年10月12日 下午5:16:55
	 */
	public List<Organization> findListByPid(Integer pid);
	
	
	public List<Organization> findListByAreaIdAndStatus(Integer areaId,StateEnum state, String searchValue);

}
