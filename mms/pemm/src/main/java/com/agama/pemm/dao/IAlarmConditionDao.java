package com.agama.pemm.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.pemm.domain.AlarmCondition;

/**
 * @Description:报警条件数据访问层接口
 * @Author:ranjunfeng
 * @Since :2015年10月10日 上午10:27:34
 */
public interface IAlarmConditionDao extends IBaseDao<AlarmCondition, Integer> {
	/**
	 * @Description:根据ID字符串修改状态
	 * @param ids
	 * @Since :2015年10月10日 上午10:28:05
	 */
	public void updateStatusByIds(String ids);

	/**
	 * @Description:根据设备ID获取告警条件
	 * @param deviceId
	 *            设备ID
	 * @return
	 * @Since :2015年10月10日 上午10:29:03
	 */
	public List<AlarmCondition> getListByTemplateId(Integer templateId,
			DeviceInterfaceType deviceInterfaceType);
	

}
