package com.agama.device.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.IHostDeviceDao;
import com.agama.device.domain.CollectionDevice;
import com.agama.device.service.ICollectionDeviceService;
import com.agama.device.service.IHostDeviceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml"})
public class CollectionDeviceServiceImplTest {
	@Autowired
	private ICollectionDeviceService collectionDeviceService;
	@Autowired
	private IHostDeviceService hostDeviceService;
	@Test
	public void getListByStatus(){
		List<CollectionDevice> collectionDevices=collectionDeviceService.getListByStatus(StatusEnum.DELETED);
		for (CollectionDevice collectionDevice : collectionDevices) {
			System.out.println(collectionDevice.getIdentifier());
		}
	}
	@Test
	public void add(){
		CollectionDevice collectionDevice=new CollectionDevice();
		collectionDevice.setEnable(EnabledStateEnum.ENABLED);
		collectionDevice.setIdentifier("011601061550001");
		collectionDevice.setIp("192.168.2.23");
		collectionDevice.setManagerId(1);
		collectionDevice.setName("动环设备采集器");
		collectionDevice.setOrganizationId(1);
		collectionDevice.setPurchaseId(1);
		collectionDevice.setUserDeviceTypeId(1);
		collectionDevice.setStatus(StatusEnum.NORMAL);
		collectionDevice.setCurrentState(StateEnum.good);
		collectionDeviceService.save(collectionDevice);
		System.out.println(collectionDevice.getIdentifier());
	}
	@Test
	public void delete(){
		collectionDeviceService.delete(4);
	}
	

}
