package com.agama.device.service;

import java.util.Map;

import com.agama.common.dao.utils.Page;
import com.agama.common.domain.StateEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.AlarmCondition;


/**
 * @Description:报警条件业务接口
 * @Author:ranjunfeng
 * @Since :2015年10月13日 下午2:47:28
 */
public interface IAlarmConditionService extends
		IBaseService<AlarmCondition, Integer> {
	/**
	 * @Description:根据id字符串修改状态
	 * @param ids
	 * @Since :2015年12月25日 下午4:42:58
	 */
	public void updateStatusByIds(String ids);
	/**
	 * @Description:根据模板id查询告警条件分页
	 * @param page
	 * @param alarmtemplateId
	 * @param status
	 * @return
	 * @Since :2015年12月31日 下午2:42:50
	 */
	public Page<AlarmCondition> searchByAlarmTemplateIdAndStatus(Page<AlarmCondition> page,Integer alarmtemplateId,StatusEnum status);

	
	public StateEnum alarmConditionHandle(Integer managerId,String identifier,StateEnum currenState,
			AlarmCondition alarmCondition, Map<String, Object> alarmMap);
	
	public Integer getUserId(String identifier);
}
