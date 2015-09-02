package com.agama.pemm.service;

import java.util.List;

import com.agama.common.service.IBaseService;
import com.agama.pemm.domain.UpsStatus;



public interface IUpsStatusService extends IBaseService<UpsStatus, Long>{
	/**
	 * 根据IP地址获取UPS的状态信息
	 * @param ipAaddress IP地址
	 * @return UPS状态
	 */
	public UpsStatus collectUspStatus(String ipAaddress);

}
