package com.agama.pemm.service;

import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.AcStatus;
import com.agama.pemm.domain.UpsStatus;

/**
 * @Description:空调状态业务逻辑处理
 * @Author:ranjunfeng
 * @Since :2016年2月1日 上午10:11:15
 */
public interface IAcStatusService extends IBaseService<AcStatus, Integer> {
	/**
	 * @Description:采集空调状态信息
	 * @param ipAaddress  ip地址
	 * @param index 接口编号
	 * @param deviceId 设备id
	 * @Since :2016年2月1日 上午10:12:54
	 */
	public void collectAcStatus(String ipAaddress, Integer index,
			Integer deviceId);

	/**
	 * @Description:关闭空调
	 * @param ip
	 * @param index
	 * @param command
	 * @return
	 * @Since :2016年2月2日 下午3:47:47
	 */
	public boolean closeOrOpenOfAc(String ip, Integer index,Integer command);

	public void saveS(Integer id);
}
