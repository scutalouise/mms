package com.agama.pemm.service;

import com.agama.common.domain.StateEnum;

/**
 * @Description:数据统计业务接口
 * @Author:ranjunfeng
 * @Since :2015年10月29日 下午1:59:48
 */
public interface IStatisticService {
	/**
	 * @Description:统计网点运行状态的个数
	 * @return stateEnum
	 * @Since :2015年10月29日 下午2:19:20
	 */
	public Integer statisticBrancheStateNum(StateEnum stateEnum);

}
