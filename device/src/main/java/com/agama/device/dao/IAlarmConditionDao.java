package com.agama.device.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.AlarmDeviceType;
import com.agama.common.enumbean.AlarmOptionType;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.device.domain.AlarmCondition;

/**
 * @Description:报警条件数据访问层接口
 * @Author:ranjunfeng
 * @Since :2015年10月10日 上午10:27:34
 */
public interface IAlarmConditionDao extends IBaseDao<AlarmCondition, Integer> {
	/**
	 * @Description:根据id字符串修改状态
	 * @param ids
	 * @Since :2015年10月10日 上午10:28:05
	 */
	public void updateStatusByIds(String ids);
	/**
	 * @Description:根据设备ID获取告警条件
	 * @param deviceId 设备ID
	 * @return
	 * @Since :2015年10月10日 上午10:29:03
	 */
	public List<AlarmCondition> getListByTemplateId(Integer templateId,AlarmDeviceType alarmTemplateId,AlarmOptionType alarmOptionType);

	/**
	 * @Description:根据模板id和状态查询告警条件分页
	 * @param page
	 * @param alarmTemplateId
	 * @param status
	 * @return
	 * @Since :2015年12月31日 下午2:52:17
	 */
	public Page<AlarmCondition> searchByAlarmTemplateIdAndStatus(
			Page<AlarmCondition> page, Integer alarmTemplateId,
			StatusEnum status);
}
