package com.agama.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.enumbean.DeviceType;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.IPeDeviceDao;
import com.agama.device.domain.PeDevice;
import com.agama.device.service.IPeDeviceService;

@Service
@Transactional
public class PeDeviceServiceImpl extends BaseServiceImpl<PeDevice, Integer>implements IPeDeviceService {
	@Autowired
	private IPeDeviceDao peDeviceDao;
	@Override
	public List<PeDevice> getPeDeviceByIpAndDeviceTypeAndIndex(String ip,
			DeviceType switchinput, Integer index) {
		
		return peDeviceDao.getPeDeviceByIpAndDeviceTypeAndIndex(ip,
				switchinput, index);
	}

}
