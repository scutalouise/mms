package com.agama.device.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.device.domain.SupplyMaintainBrand;

/**
 * @Description:供应商(运维)机构与品牌的关联；
 * @Author:杨远高
 * @Since :2016年2月29日 下午5:52:44
 */
public interface ISupplyMaintainBrandDao extends IBaseDao<SupplyMaintainBrand, Integer> {
	/**
	 * @Description:根据供应商机构id查到对应的品牌；
	 * @param supplyMaintainOrgId
	 * @return
	 * @Since :2016年3月2日 上午10:42:48
	 */
	public List<Integer> getBrandIdList(Integer supplyMaintainOrgId);
	
	
	/**
	 * @Description:删除掉supplyMaintainBrand
	 * @param supplyMaintainOrgId
	 * @param brandId
	 * @Since :2016年3月2日 上午11:02:44
	 */
	public void deleteSupplyMaintainBrand(Integer supplyMaintainOrgId, Integer brandId);
}
