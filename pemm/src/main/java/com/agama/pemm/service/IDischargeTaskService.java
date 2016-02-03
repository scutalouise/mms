package com.agama.pemm.service;

import java.util.List;

import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.DischargeTask;

public interface IDischargeTaskService extends
		IBaseService<DischargeTask, Integer> {

	public void add(String scheduleName, String scheduleGroup,
			List<Integer> deviceIds);

	/**
	 * @Description:根据设备ID字符串修改放电计划状态为（删除）
	 * @param deviceIds
	 * @Since :2015年11月27日 下午3:36:46
	 */
	public void updateByDeviceIds(String deviceIds);
	/**
	 * @Description:删除放电任务
	 * @param scheduleName
	 * @param scheduleGroup
	 * @Since :2015年12月1日 上午11:56:29
	 */
	public void delDischargeTask(String scheduleName, String scheduleGroup);
    /**
     * @Description: 根据放电计划对象查询放电计划集合
     * @param dischargeTask
     * @return
     * @Since :2015年12月1日 下午2:20:29
     */
	public List<DischargeTask> getListByDischargeTask(DischargeTask dischargeTask);
   /**
    * @Description:根据设备id进行放电
    * @param deviceId
    * @Since :2015年12月1日 下午6:14:25
    */
	public void discharge(Integer deviceId);

}
