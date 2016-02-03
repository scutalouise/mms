package com.agama.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IBrandDao;
import com.agama.device.domain.Brand;
import com.agama.device.service.IBrandService;


@Service
@Transactional
public class BrandServiceImpl extends BaseServiceImpl<Brand, Integer>implements IBrandService {

	@Autowired
	private IBrandDao brandDao;

	@Override
	public List<Brand> getBrandBySecondDeviceType(SecondDeviceType secondDeviceType) {
		String hql = "from Brand where secondDeviceType=?0 and enable=?1 and status=?2";
		return brandDao.find(hql, secondDeviceType, EnabledStateEnum.ENABLED, StatusEnum.NORMAL);
	}

	@Override
	public List<Brand> validBrand(SecondDeviceType secondDeviceType, Integer id) {
		StringBuffer hql = new StringBuffer("from Brand where status=0 and secondDeviceType=")
				.append(secondDeviceType.ordinal());
		if (id != null) {
			hql.append(" and id!=").append(id);
		}
		return brandDao.find(hql.toString());
	}

	@Override
	public void updateStatusById(int id) {
		String hql = "update Brand set status=1 where id=?0";
		brandDao.batchExecute(hql, id);
	}
}
