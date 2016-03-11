package com.agama.pemm.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.IAcConfigDao;
import com.agama.pemm.domain.AcConfig;
@Repository
public class AcConfigDaoImpl extends HibernateDaoImpl<AcConfig, Integer>
		implements IAcConfigDao {

	@Override
	public AcConfig findAcConfigByDeviceId(Integer deviceId) {
		
		
		return findUnique("from AcConfig where deviceId="+deviceId);
	}
	

}
