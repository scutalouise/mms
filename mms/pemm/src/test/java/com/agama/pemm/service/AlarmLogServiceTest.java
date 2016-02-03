package com.agama.pemm.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.authority.service.IAreaInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AlarmLogServiceTest {
	@Autowired
	private IAreaInfoService areaInfoService;
	@Autowired
	private IAlarmLogService alarmLogService;
	@Test
	public void getData(){
		String areaInfoStr=areaInfoService.getAreaInfoIdStrById(null);
		
	}

}
