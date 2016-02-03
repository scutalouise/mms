package com.agama.das.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.agama.aws.dao.MongoDao;
import com.agama.das.model.entity.SystemConfig;
import com.agama.das.service.SystemConfigService;

/**
 * @Description:实现类，实现系统配置的操作方法
 * @Author:佘朝军
 * @Since :2015年11月18日 上午11:12:32
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

	@Autowired
	private MongoDao mongoDao;

	@Override
	public void insert(SystemConfig systemConfig) {
		mongoDao.save(systemConfig);
	}

	@Override
	public void update(SystemConfig systemConfig) {
		mongoDao.updateBeanByObjectId(systemConfig.getId(), systemConfig, SystemConfig.class);
	}

	@Override
	public SystemConfig getFirst() {
		List<SystemConfig> scList = mongoDao.queryListByCriteriaAndSort(new Criteria(), null, SystemConfig.class);
		SystemConfig systemConfig = null;
		if (!scList.isEmpty()) {
			systemConfig = scList.get(0);
		}
		return systemConfig;
	}

	@Override
	public void saveOrUpdateConnectCenter(boolean connectCenter) {
		SystemConfig systemConfig = getFirst();
		if (systemConfig != null) {
			systemConfig.setConnectCenter(connectCenter);
			update(systemConfig);
		} else {
			systemConfig = new SystemConfig();
			systemConfig.setConnectCenter(connectCenter);
			insert(systemConfig);
		}
	}

	@Override
	public void saveOrUpdateLastDataGetTime(long lastDataGetTime) {
		SystemConfig systemConfig = getFirst();
		if (systemConfig != null) {
			systemConfig.setLastDataGetTime(lastDataGetTime);
			update(systemConfig);
		} else {
			systemConfig = new SystemConfig();
			systemConfig.setLastDataGetTime(lastDataGetTime);
			insert(systemConfig);
		}

	}

}
