package com.agama.authority.dao;

import java.util.List;

import com.agama.authority.entity.AreaInfo;
import com.agama.common.dao.IBaseDao;
import com.agama.common.domain.StateEnum;

/**
 * @Description:区域DAO
 * @Author:scuta
 * @Since :2015年8月27日 上午9:29:24
 */
public interface IAreaInfoDao extends IBaseDao<AreaInfo, Integer> {

	public List<AreaInfo> findListByPid(Integer pid);

	/**
	 * @param searchValue 
	 * @Description:查询所有关联了机构的区域
	 * @return
	 * @Since :2015年10月30日 下午1:56:18
	 */
	

	public List<AreaInfo> getListRelevancyOrganization(StateEnum stateEnum, String searchValue);

}
