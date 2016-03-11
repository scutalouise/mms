package com.agama.device.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.device.domain.SupplyMaintainOrg;

/**
 * @Description:供应商与运维组织机构数据库访问层接口
 * @Author:杨远高
 * @Since :2016年2月29日 上午11:15:40
 */
public interface ISupplyMaintainOrgDao extends IBaseDao<SupplyMaintainOrg, Integer> {
	/**
	 * @Description: 根据pid获取机构集合
	 * @param pid
	 * @return
	 * @Since :2015年10月12日 下午5:16:55
	 */
	public List<SupplyMaintainOrg> findListByPid(Integer pid);
	
	/**
	 * @Description:根据区域id获取机构；
	 * @param areaId
	 * @return
	 * @Since :2016年2月29日 上午11:55:59
	 */
	public List<SupplyMaintainOrg> findSupplyMaintainOrgsByAreaId(Integer areaId);
	
	/**
	 * @Description:获取用户拥有机构id集合
	 * @param userId
	 * @return
	 * @Since :2016年3月1日 下午3:44:18
	 */
	public List<Integer> getOrgIdList(Integer userId);
	
}
