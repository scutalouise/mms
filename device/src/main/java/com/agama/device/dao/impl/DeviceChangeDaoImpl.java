package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.device.dao.IDeviceChangeDao;
import com.agama.device.domain.DeviceChange;

@Repository
public class DeviceChangeDaoImpl extends HibernateDaoImpl<DeviceChange, Integer>implements IDeviceChangeDao {

}
