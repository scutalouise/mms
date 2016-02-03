package com.agama.das.quartz;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description:
 * @Author:佘朝军
 * @Since :2015年11月23日 下午2:34:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc.xml", "classpath:applicationContext.xml","classpath:applicationContext-quartz.xml" })
public class TaskTest {

	@Autowired
	ExecuteTask et;
	@Resource
	Scheduler scheduler;
	@Resource
	SimpleTrigger hostTrigger;
	@Resource
	JobDetail hostJobDetail;

	@Test
	public void executeInspectTask() {
		et.checkRunStatusForHost();
	}
	
	@Test
	public void executeInteractTask(){
		et.dataInteractTask();
	}
	
	@Test
	public void modifyCron(){
		try {
			SimpleTriggerImpl stl = new SimpleTriggerImpl();
			stl.setKey(hostTrigger.getKey());
			stl.setRepeatInterval(5000);
			stl.setJobKey(hostJobDetail.getKey());
			stl.setMisfireInstruction(hostTrigger.getMisfireInstruction());
			stl.setNextFireTime(hostTrigger.getNextFireTime());
			stl.setStartTime(hostTrigger.getStartTime());
			SimpleTrigger st = stl;
			scheduler.rescheduleJob(hostTrigger.getKey(), st);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
