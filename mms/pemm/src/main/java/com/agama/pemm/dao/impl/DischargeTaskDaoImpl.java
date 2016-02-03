package com.agama.pemm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.IDischargeTaskDao;
import com.agama.pemm.domain.DischargeTask;

@Repository
public class DischargeTaskDaoImpl extends
		HibernateDaoImpl<DischargeTask, Integer> implements IDischargeTaskDao {

	@Override
	public void updateStatusByScheduleNameAndScheduleGroup(String scheduleName,
			String scheduleGroup) {
		StringBuffer hql = new StringBuffer(
				"update DischargeTask set status=1 where status=0");
		if (scheduleGroup != null) {
			hql.append(" and scheduleGroup='").append(scheduleGroup)
					.append("'");
		}
		if (scheduleName != null) {
			hql.append(" and scheduleName='").append(scheduleName).append("'");
		}
		this.batchExecute(hql.toString());

	}

	@Override
	public List<DischargeTask> findListByDischargeTask(
			DischargeTask dischargeTask) {
		StringBuffer hql = new StringBuffer("from DischargeTask where 1=1");
		if (dischargeTask.getStatus() != null) {
			hql.append(" and status=").append(dischargeTask.getStatus());
		}
		if (dischargeTask.getScheduleGroup() != null) {
			hql.append(" and scheduleGroup='")
					.append(dischargeTask.getScheduleGroup()).append("'");
		}
		if (dischargeTask.getScheduleName() != null) {
			hql.append(" and scheduleName='")
					.append(dischargeTask.getScheduleName()).append("'");
		}
		return this.find(hql.toString());
	}
}
