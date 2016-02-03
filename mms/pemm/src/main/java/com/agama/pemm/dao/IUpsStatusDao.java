package com.agama.pemm.dao;

import java.util.Date;
import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.bean.UpsChartBean;
import com.agama.pemm.domain.UpsStatus;
/**
 * @Description:UPS状态信息数据访问层接口类
 * @Author:ranjunfeng
 * @Since :2015年12月9日 上午10:54:16
 */
public interface IUpsStatusDao extends IBaseDao<UpsStatus, Integer> {

	public List<UpsStatus> findLatestDataByGitInfoId(Integer gitInfoId);

	/**
	 * @Description:根据id字符串修改ups设备信息状态
	 * @param ids
	 * @Since :2015年12月9日 上午10:53:14
	 */
	public void updateStatusByIds(String ids);

	public List<UpsChartBean> getUpsChartListByDeviceId(Integer deviceId,
			Date beginDate, Date endDate);

	/**
	 * @Description:根据git的id字符串修改ups状态信息的状态
	 * @param gitInfoIds git的主键id字符串
	 * @param status 状态
	 * @Since :2015年11月24日 下午3:23:58
	 */
	public void updateStatusByGitInfoIds(String gitInfoIds, int status);

	/**
	 * @Description:根据设备id字符串修改ups状态信息的状态
	 * @param deviceIds 设备id字符串
	 * @param status  修改后的状态
	 * @Since :2015年11月24日 下午4:32:34
	 */
	public void updateStatusDeviceIds(String deviceIds, int status);

	/**
	 * @Description:根据设备id查询最新的UPS状态信息
	 * @param deviceId  设备id
	 * @return
	 * @Since :2015年12月9日 上午10:52:09
	 */
	public UpsStatus findNewData(Integer deviceId);

}
