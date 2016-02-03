package com.agama.device.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.domain.DevicePurchase;
import com.agama.device.service.IDevicePurchaseService;

@Service
@Transactional
public class DevicePurchaseServiceImpl extends BaseServiceImpl<DevicePurchase, Integer>implements IDevicePurchaseService {

}
