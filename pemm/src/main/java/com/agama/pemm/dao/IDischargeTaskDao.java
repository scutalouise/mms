package com.agama.pemm.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.domain.DischargeTask;

public interface IDischargeTaskDao extends IBaseDao<DischargeTask, Integer> {
	/**
	 * @Description:根据放电任务名称和任务组修改状态
	 * @param scheduleName
	 * @param scheduleGroup
	 * @Since :2015年12月1日 上午11:59:04
	 */
	public void updateStatusByScheduleNameAndScheduleGroup(String scheduleName,String scheduleGroup);
	/**
     * @Description: 根据放电计划对象查询放电计划集合
     * @param dischargeTask
     * @return
     * @Since :2015年12月1日 下午2:20:29
     */
	public List<DischargeTask> findListByDischargeTask(
			DischargeTask dischargeTask);

}
