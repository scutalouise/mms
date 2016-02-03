package com.agama.pemm.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IUpsStatusService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpsStatusServiceTest {
	private String ipAddress = "192.168.2.22";
	@Autowired
	private IUpsStatusService upsStatusService;

	@Test
	public void findAll() {
		List<UpsStatus> upsStatusList = upsStatusService.getAll();
		for (UpsStatus upsStatus : upsStatusList) {
			
		}

	}

	@Test
	public void getUpsStatus() {
		
		

	}

	@Test
	public void findLatestDataByGitInfoId() {
		List<UpsStatus> upsStatusList = upsStatusService
				.findLatestDataByGitInfoId(5);
		for (UpsStatus upsStatus : upsStatusList) {
			System.out.println(upsStatus.getId());
		}
	}

	@Test
	public void saveUpsStatus() {
		
	}

}
