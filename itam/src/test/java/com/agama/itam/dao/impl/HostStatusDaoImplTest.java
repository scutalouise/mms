package com.agama.itam.dao.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;












import com.agama.authority.entity.Organization;
import com.agama.authority.service.IOrganizationService;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.dao.ICollectionDeviceDao;
import com.agama.device.domain.CollectionDevice;
import com.agama.device.service.ICollectionDeviceService;
import com.agama.itam.mongo.domain.HostStatus;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:itam-applicationContext.xml"})
public class HostStatusDaoImplTest {
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private ICollectionDeviceService collectionDeviceService;
	@Test
	public void test(){
		Organization o=organizationService.get(3);
		System.out.println(o);
	}
	@Test
	public void a(){
		List<CollectionDevice> collectionDevices=collectionDeviceService.getListByStatus(StatusEnum.NORMAL);
	
	}

}
