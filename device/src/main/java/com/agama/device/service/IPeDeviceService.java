package com.agama.device.service;

import java.util.List;

import com.agama.common.enumbean.DeviceType;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.PeDevice;

/**
 * @Description:动环设备业务逻辑接口
 * @Author:ranjunfeng
 * @Since :2016年1月25日 上午9:28:15
 */
public interface IPeDeviceService extends IBaseService<PeDevice, Integer> {
	/**
	 * @Description:根据采集设备的IP、设备类型、接口号获取动环设备对象
	 * @param ip 采集设备IP
	 * @param switchinput 设备类型
	 * @param index 接口号
	 * @return
	 * @Since :2016年1月25日 上午9:34:00
	 */
	public List<PeDevice> getPeDeviceByIpAndDeviceTypeAndIndex(String ip,
			DeviceType switchinput, Integer index);

}
