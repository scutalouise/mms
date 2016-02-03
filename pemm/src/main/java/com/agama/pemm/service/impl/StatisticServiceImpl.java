package com.agama.pemm.service.impl;

import java.math.BigInteger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.domain.StateEnum;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.service.IStatisticService;

/**
 * @Description:数据统计业务实体
 * @Author:ranjunfeng
 * @Since :2015年10月29日 下午2:20:24
 */
@Service
@Transactional
public class StatisticServiceImpl implements IStatisticService {
	@Autowired
	private IDeviceDao deviceDao;

	@Override
	public  Integer statisticBrancheStateNum(StateEnum stateEnum) {
		// TODO Auto-generated method stub
		return deviceDao.statisticBranchStateNum(stateEnum);
	}

}
