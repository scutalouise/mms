package com.agama.pemm.service;

import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.SendConfig;

public interface ISendConfigService extends IBaseService<SendConfig, Integer> {
	/**
	 * @Description:根据ip关闭上位机上面的报警器
	 * @param ip ip地址
	 * @return 修改后的状态
	 * @Since :2015年11月13日 下午5:35:27
	 */
	public boolean closeAlarm(String ip);

}
