package com.agama.pemm.service.impl;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.dao.IAcConfigDao;
import com.agama.pemm.domain.AcConfig;
import com.agama.pemm.service.IAcConfigService;

/**
 * @Description:空调配置
 * @Author:ranjunfeng
 * @Since :2016年2月23日 上午11:22:33
 */
@Service
@Transactional
public class AcConfigServiceImpl extends BaseServiceImpl<AcConfig, Integer>
		implements IAcConfigService {
	@Autowired
	private IAcConfigDao acConfigDao;
	@Override
	public AcConfig getAcConfigByDeviceIdAndEnabled(Integer deviceId,
			EnabledStateEnum enabled) {
		StringBuffer hql=new StringBuffer("from AcConfig where enabled='").append(enabled.toString()).append("'").append(" and deviceId=").append(deviceId);
		
		return acConfigDao.findUnique(hql.toString());
	}
}
