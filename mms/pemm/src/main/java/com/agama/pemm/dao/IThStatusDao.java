package com.agama.pemm.dao;

import java.util.Date;
import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.bean.ThChartBean;
import com.agama.pemm.domain.ThStatus;

/**
 * @Description:温湿度状态数据访问层接口
 * @Author:ranjunfeng
 * @Since :2015年10月13日 上午11:31:22
 */
public interface IThStatusDao extends IBaseDao<ThStatus, Integer> {
	/**
	 * @Description:根据git的主键id查询最新的温湿度状态信息
	 * @param gitInfoId
	 * @return
	 * @Since :2015年11月24日 下午4:42:47
	 */
	public List<ThStatus> findLatestDataByGitInfoId(Integer gitInfoId);

	/**
	 * @Description:根据时间段和设备id查询温湿度信息，并封装到ThChartBean实体中
	 * @param deviceId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @Since :2015年11月24日 下午4:39:46
	 */
	public List<ThChartBean> getListByDeviceId(Integer deviceId,
			Date beginDate, Date endDate);

	/**
	 * @Description:根据git的id字符串修改温湿度状态信息的状态
	 * @param gitInfoIds git的主键id字符串
	 * @param status 修改后的状态
	 * @Since :2015年11月24日 下午3:57:47
	 */
	public void updateStatusByGitInfoIds(String gitInfoIds, int status);

	/**
	 * @Description:根据设备id字符串修改温湿度状态信息的状态
	 * @param deviceIds
	 * @param status
	 * @Since :2015年11月24日 下午4:41:08
	 */
	public void updateStatusDeviceIds(String deviceIds, int status);

}
