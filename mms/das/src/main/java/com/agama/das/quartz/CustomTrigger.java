package com.agama.das.quartz;

import java.util.Set;

import javax.annotation.Resource;

import lombok.extern.log4j.Log4j;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;

/**
 * @Description:处理自定义定时器时间戳的类
 * @Author:佘朝军
 * @Since :2015年12月11日 下午3:10:43
 */
@Log4j
@Component
public class CustomTrigger {

	@Resource
	Scheduler scheduler;

	/**
	 * @Description:根据任务名称修改定时任务的执行频率
	 * @param tiggerName
	 * @param minutes
	 * @Since :2015年12月11日 下午4:08:02
	 */
	public void modifyTriggerByName(String tiggerName, int minutes) {
		TriggerKey tk = new TriggerKey(tiggerName);
		try {
			Trigger tigger = scheduler.getTrigger(tk);
			SimpleTrigger st = (SimpleTrigger) TriggerBuilder.newTrigger()
					.withIdentity(tigger.getKey())
					.withPriority(tigger.getPriority())
					.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(minutes))
					.build();
			scheduler.rescheduleJob(tigger.getKey(), st);
		} catch (SchedulerException e) {
			log.error("修改名称为：'" + tiggerName + "'的任务的时间异常", e);
			e.printStackTrace();
		}
	}

	/**
	 * @Description:根据关键词，批量修改任务的执行频率
	 * @param key
	 * @param minutes
	 * @Since :2015年12月11日 下午4:08:50
	 */
	public void modifyTriggersByKey(String key, int minutes) {
		if ("interactTrigger".equals(key)) {
			modifyTriggerByName(key, minutes);
		} else {
			try {
				GroupMatcher<TriggerKey> matcher = GroupMatcher.anyTriggerGroup();
				Set<TriggerKey> tiggerKeys = scheduler.getTriggerKeys(matcher);
				for (TriggerKey tk : tiggerKeys) {
					if (!"interactTrigger".equals(tk.getName())) {
						modifyTriggerByName(tk.getName(), minutes);
					}
				}
			} catch (SchedulerException e) {
				log.error("修改任务调度器异常！", e);
				e.printStackTrace();
			}
		}

	}

}
