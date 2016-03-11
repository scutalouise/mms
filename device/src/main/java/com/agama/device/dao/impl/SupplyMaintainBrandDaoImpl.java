package com.agama.device.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.device.dao.ISupplyMaintainBrandDao;
import com.agama.device.domain.SupplyMaintainBrand;

/**
 * @Description:供应商(运维)组织与品牌关联关系Dao实现；
 * @Author:杨远高
 * @Since :2016年2月29日 下午5:54:38
 */
@Repository("supplyMaintainBrandDao")
public class SupplyMaintainBrandDaoImpl extends HibernateDaoImpl<SupplyMaintainBrand, Integer> implements ISupplyMaintainBrandDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getBrandIdList(Integer supplyMaintainOrgId) {
		StringBuffer hql = new StringBuffer("select smb.brandId from SupplyMaintainBrand smb ");
		hql.append(" where smb.supplyMaintainOrgId = ?0 ");
		Query query = createQuery(hql.toString(), supplyMaintainOrgId);
		return query.list();
	}

	@Override
	public void deleteSupplyMaintainBrand(Integer supplyMaintainOrgId, Integer brandId) {
		StringBuffer hql = new StringBuffer("delete SupplyMaintainBrand smb where smb.supplyMaintainOrgId=?0 and smb.brandId=?1 ");
		batchExecute(hql.toString(), supplyMaintainOrgId, brandId);
	}

}
