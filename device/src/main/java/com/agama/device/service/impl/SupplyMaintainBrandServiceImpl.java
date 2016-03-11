package com.agama.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.ISupplyMaintainBrandDao;
import com.agama.device.domain.SupplyMaintainBrand;
import com.agama.device.service.ISupplyMaintainBrandService;

/**
 * @Description:用户运维机构Service实现
 * @Author:杨远高
 * @Since :2016年2月29日 上午11:13:52
 */
@Service("supplyMaintainBrandService")
@Transactional(readOnly = true)
public class SupplyMaintainBrandServiceImpl extends BaseServiceImpl<SupplyMaintainBrand, Integer> implements ISupplyMaintainBrandService{

	@Autowired
	private ISupplyMaintainBrandDao supplyMaintainBrandDao;

	@Transactional(readOnly = false)
	@Override
	public void setBrands(Integer supplyMaintainOrgId,List<Integer> oldBrandIds, List<Integer> newBrandIds) {
		//一，是否删除、
		for(int i=0,j=oldBrandIds.size();i<j;i++){
			if(!newBrandIds.contains(oldBrandIds.get(i))){
				supplyMaintainBrandDao.deleteSupplyMaintainBrand(supplyMaintainOrgId, oldBrandIds.get(i));
			}
		}
		//二，是否添加
		for(int m=0,n=newBrandIds.size();m<n;m++){
			if(!oldBrandIds.contains(newBrandIds.get(m))){
				SupplyMaintainBrand smb = new SupplyMaintainBrand();
				smb.setSupplyMaintainOrgId(supplyMaintainOrgId);
				smb.setBrandId(newBrandIds.get(m));
				supplyMaintainBrandDao.save(smb);
			}
		}
	}

	@Override
	public List<Integer> getBrandIdList(Integer supplyMaintainOrgId) {
		return supplyMaintainBrandDao.getBrandIdList(supplyMaintainOrgId);
	}

}
