package com.agama.device.service;

import java.util.List;

import com.agama.common.service.IBaseService;
import com.agama.device.domain.SupplyMaintainBrand;

/**
 * @Description:供应商与品牌关联关系；
 * @Author:杨远高
 * @Since :2016年2月29日 下午5:57:13
 */
public interface ISupplyMaintainBrandService extends IBaseService<SupplyMaintainBrand, Integer> {

	/**
	 * @Description:设置供应商与品牌关联关系
	 * @param supplyMaintainOrgId
	 * @param oldBrandIds
	 * @param newBrandIds
	 * @Since :2016年3月2日 上午10:59:03
	 */
	public void setBrands(Integer supplyMaintainOrgId ,List<Integer> oldBrandIds, List<Integer> newBrandIds);
	
	/**
	 * @Description:根据供应商机构id查找到对应的品牌ids
	 * @param orgId
	 * @return
	 * @Since :2016年3月2日 上午10:38:51
	 */
	public List<Integer> getBrandIdList(Integer supplyMaintainOrgId);

}
