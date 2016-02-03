package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.device.dao.IUnintelligentDeviceDao;
import com.agama.device.domain.UnintelligentDevice;

@Repository
public class UnintelligentDeviceDaoImpl extends HibernateDaoImpl<UnintelligentDevice, Integer>
		implements IUnintelligentDeviceDao {

}
