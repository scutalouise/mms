package com.agama.pemm.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.authority.entity.AreaInfo;
import com.agama.authority.service.IAreaInfoService;
import com.agama.common.domain.StateEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class AreaInfoServiceTest {
	@Autowired
	private IAreaInfoService areaInfoService;
	@Test
	public void getListRelevancyOrganization(){
		List<AreaInfo> areaInfoList=areaInfoService.getListRelevancyOrganization(StateEnum.good,"");
		for (AreaInfo areaInfo : areaInfoList) {
			System.out.println(areaInfo.getAreaName());
		}
	}

}
