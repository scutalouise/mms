package com.agama.pemm.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.domain.AcConfig;

/**
 * @Description:空调配置数据访问层接口
 * @Author:ranjunfeng
 * @Since :2016年2月25日 下午3:03:43
 */
public interface IAcConfigDao extends IBaseDao<AcConfig, Integer> {
	/**
	 * @Description:根据空调设备id获取空调配置信息对象
	 * @param deviceId
	 * @return
	 * @Since :2016年2月25日 下午3:02:40
	 */
	public AcConfig findAcConfigByDeviceId(Integer deviceId);
}
