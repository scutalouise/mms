package com.agama.device.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.DeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.domain.PeDevice;
import com.agama.device.service.IPeDeviceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml"})
public class PeDeviceServiceImplTest {
	@Autowired
	private IPeDeviceService peDeviceService;
	@Test
	public void saveUps(){
		PeDevice peDevice=new PeDevice();
		peDevice.setAlarmTemplateId(1);
		peDevice.setCollectDeviceId(1);
		peDevice.setStatus(StatusEnum.NORMAL);
		peDevice.setIdentifier("04160122001");
		peDevice.setName("测试动环设备");
		peDevice.setManagerId(1);
		peDevice.setPurchaseId(1);
		peDevice.setOrganizationId(1);
		peDevice.setUserDeviceTypeId(1);
		peDevice.setDhDeviceType(DeviceType.UPS);
		peDevice.setDhDeviceInterfaceType(DeviceInterfaceType.UPS);
		peDevice.setDhDeviceIndex(1);
		peDevice.setCurrentState(StateEnum.good);
		peDeviceService.save(peDevice);
		System.out.println(peDevice.getId());
	}
	@Test
	public void saveTh(){
		PeDevice peDevice=new PeDevice();
		peDevice.setAlarmTemplateId(1);
		peDevice.setCollectDeviceId(1);
		peDevice.setStatus(StatusEnum.NORMAL);
		peDevice.setIdentifier("04160122001");
		peDevice.setName("测试动环设备");
		peDevice.setManagerId(1);
		peDevice.setPurchaseId(1);
		peDevice.setOrganizationId(1);
		peDevice.setUserDeviceTypeId(1);
		peDevice.setDhDeviceType(DeviceType.TH);
		peDevice.setDhDeviceInterfaceType(DeviceInterfaceType.TH);
		peDevice.setDhDeviceIndex(1);
		peDevice.setCurrentState(StateEnum.good);
		peDeviceService.save(peDevice);
		System.out.println(peDevice.getId());
	}

}
