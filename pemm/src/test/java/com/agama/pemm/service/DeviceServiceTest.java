package com.agama.pemm.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.authority.system.service.impl.AreaInfoServiceImpl;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.domain.Device;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
public class DeviceServiceTest {
	@Autowired
	private AreaInfoServiceImpl areaInfoService;
	@Autowired
	private IDeviceService deviceService;
	@Test
	public void recursiveByPid(){
	
		System.out.println(areaInfoService.getAreaInfoIdStrById(1));
	}
	@Test
	public void  getDeviceStateRecordByAreaInfoId(){
		List<DeviceStateRecord> deviceStateRecords=deviceService.getDeviceStateRecordByAreaInfoId(null);
		for (DeviceStateRecord deviceStateRecord : deviceStateRecords) {
			System.out.println(deviceStateRecord.getCount());
		}
	}

}
