package com.agama.das.service;

import com.agama.das.model.entity.SystemConfig;

/**@Description:接口类，提供系统配置操作的方法
 * @Author:佘朝军
 * @Since :2015年11月18日 上午10:58:59
 */
public interface SystemConfigService {
	
	public void insert(SystemConfig systemConfig);
	
	public void update(SystemConfig systemConfig);
	
	public SystemConfig getFirst();
	
	public void saveOrUpdateConnectCenter(boolean connectCenter);
	
	public void saveOrUpdateLastDataGetTime(long lastDataGetTime);

}
