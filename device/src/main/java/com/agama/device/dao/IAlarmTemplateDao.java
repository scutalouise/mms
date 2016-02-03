package com.agama.device.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.device.domain.AlarmTemplate;

/**
 * @Description:告警模板数据访问层接口
 * @Author:ranjunfeng
 * @Since :2015年12月25日 下午4:28:40
 */
public interface IAlarmTemplateDao extends IBaseDao<AlarmTemplate, Integer> {
    /**
     * @Description:更具ids字符串修改状态
     * @param ids
     * @Since :2015年12月25日 下午4:29:09
     */
	public void updateStatusByIds(String ids);

}
