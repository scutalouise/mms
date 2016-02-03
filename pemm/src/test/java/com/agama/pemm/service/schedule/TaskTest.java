package com.agama.pemm.service.schedule;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.authority.entity.ScheduleJob;
import com.agama.authority.service.IScheduleJobService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml"})
public class TaskTest {
	@Autowired
	private IScheduleJobService scheduleJobService;
	@Test
	public void getAllScheduleJob(){
		List<ScheduleJob> list=scheduleJobService.getAllRuningScheduleJob();
	for (ScheduleJob scheduleJob : list) {
		System.out.println(scheduleJob.getName()+"==============");
	}
	}
	public static void main(String[] args) {
		System.out.println(UpsDischargeJob.class.getName());
	}

	

}
