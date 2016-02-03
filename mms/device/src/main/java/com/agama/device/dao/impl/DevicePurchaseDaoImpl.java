package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.device.dao.IDevicePurchaseDao;
import com.agama.device.domain.DevicePurchase;


@Repository
public class DevicePurchaseDaoImpl extends HibernateDaoImpl<DevicePurchase, Integer>implements IDevicePurchaseDao {

}
