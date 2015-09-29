package com.agama.pemm.service;

import java.util.Date;
import java.util.List;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.IBaseService;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.bean.UpsChartBean;
import com.agama.pemm.domain.UpsStatus;

public interface IUpsStatusService extends IBaseService<UpsStatus, Integer> {
	/**
	 * 根据IP地址获取UPS的状态信息
	 * 
	 * @param ipAaddress
	 *            IP地址
	 * @return UPS状态
	 */
	public UpsStatus collectUspStatus(String ipAaddress, Integer index,
			Integer deviceId);

	/**
	 * 根据网关ID查询最新的UPS状态信息
	 * 
	 * @param gitInfoId
	 * @return
	 */

	public List<UpsStatus> findLatestDataByGitInfoId(Integer gitInfoId);

	public void saveUpsStatus();

	public Page<UpsStatus> searchByGitInfoIdAndDeviceTypeAndIndex(
			Page<UpsStatus> page, Integer gitInfoId, DeviceType ups,
			Integer index);

	/**
	 * 根据ID批量更改状态/逻辑删除
	 * 
	 * @param ids
	 */
	public void updateStatusByIds(String ids);

	/**
	 * 根据设备ID查询UPS状态信息集合
	 * 
	 * @param deviceId
	 * @return
	 */
	public List<UpsChartBean> getListbyDeviceId(Integer deviceId,Date beginDate,Date endDate);

}
