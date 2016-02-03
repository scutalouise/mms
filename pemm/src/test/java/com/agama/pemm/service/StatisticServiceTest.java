package com.agama.pemm.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.common.domain.StateEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class StatisticServiceTest {
	@Autowired
	private IStatisticService statisticService;
	@Test
	public void statisticBrancheStateNum(){ 
		System.out.println(statisticService.statisticBrancheStateNum(StateEnum.warning));
	}

}
