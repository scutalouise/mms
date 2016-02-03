package com.agama.itam.dao;

import com.agama.common.dao.IBaseDao;
import com.agama.itam.domain.DischargeStatistic;

/**
 * @Description:放电统计数据访问层接口
 * @Author:ranjunfeng
 * @Since :2015年12月9日 下午12:01:30
 */
public interface IDischargeStatisticDao extends
		IBaseDao<DischargeStatistic, Integer> {
	/**
	 * @Description:根据设备id查询开始放电时间最新的数据
	 * @param deviceId
	 * @return
	 * @Since :2015年12月9日 下午12:01:50
	 */
	public DischargeStatistic findBeginDateForNewData(Integer deviceId,Long batch,String order);
	/**
	 * @Description:根据设备id和放电批次号获取平均负载
	 * @param deviceId
	 * @param batch
	 * @return
	 * @Since :2015年12月9日 下午2:59:56
	 */
	public Double findAvgLoadByDeviceIdAndBatch(Integer deviceId, Long batch);
	
	/**
	 * @Description:查询当前设备上一个批次的放电统计
	 * @param deviceId
	 * @param batch
	 * @param string
	 * @return
	 * @Since :2015年12月9日 下午6:18:45
	 */
	public DischargeStatistic findBeforeDataByDeviceIdAndBatch(
			Integer deviceId, Long batch, String order);

}
