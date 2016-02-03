package com.agama.pemm.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.pemm.domain.SwitchInputStatus;

/**
 * @Description:开关量输入信息状态数据访问层接口
 * @Author:ranjunfeng
 * @Since :2015年10月15日 下午3:29:21
 */
public interface ISwitchInputStatusDao extends
		IBaseDao<SwitchInputStatus, Integer> {
	/**
	 * @Description:根据上位机ID和连接设备的类型查询最新的开关量输入的状态信息
	 * @param gitInfoId
	 *            上位机ID
	 * @param deviceInterfaceType
	 *            连接设备的类型
	 * @return
	 * @Since :2015年10月19日 上午11:33:00
	 */
	public List<SwitchInputStatus> findLatestData(Integer gitInfoId,
			DeviceInterfaceType deviceInterfaceType);

	/**
	 * @Description:根据git的id字符串修改开关量输出的状态信息的状态
	 * @param gitInfoIds git id字符串
	 * @param status 状态
	 * @Since :2015年11月24日 下午4:11:00
	 */
	public void updateStatusByGitInfoIds(String gitInfoIds, int status);
	/**
	 * @Description:根据设备Id字符串修改开关量输出的状态信息的状态
	 * @param deviceIds
	 * @param status
	 * @Since :2015年11月24日 下午4:49:15
	 */
	public void updateStatusDeviceIds(String deviceIds, int status);

}
