package com.agama.pemm.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.pemm.dao.IDischargeStatisticDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml"})
@Service
public class DischargeStatisticServiceTest {
	@Autowired
	private IDischargeStatisticDao dischargeStatisticDao;
	@Test
	public void findAvgLoadByDeviceIdAndBatch(){
		Double a=dischargeStatisticDao.findAvgLoadByDeviceIdAndBatch(347, 1449647569189L);
		System.out.println(a);
	}

}
