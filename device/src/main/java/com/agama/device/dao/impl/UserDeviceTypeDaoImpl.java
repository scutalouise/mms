package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.device.dao.IUserDeviceTypeDao;
import com.agama.device.domain.UserDeviceType;

@Repository
public class UserDeviceTypeDaoImpl extends HibernateDaoImpl<UserDeviceType, Integer>implements IUserDeviceTypeDao {

}
