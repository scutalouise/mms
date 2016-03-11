package com.agama.pemm.service;

import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AcConfig;

/**
 * @Description:空调配置业务层接口类
 * @Author:ranjunfeng
 * @Since :2016年2月23日 上午11:15:50
 */
public interface IAcConfigService extends IBaseService<AcConfig, Integer> {
	/**
	 * @Description:根据设备id和启用状态获取空调配置对象
	 * @param deviceId
	 * @param enabeld
	 * @return
	 * @Since :2016年2月23日 上午11:16:33
	 */
	public AcConfig getAcConfigByDeviceIdAndEnabled(Integer deviceId,EnabledStateEnum enabled);

}
