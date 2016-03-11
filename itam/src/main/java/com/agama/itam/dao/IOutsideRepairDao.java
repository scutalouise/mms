package com.agama.itam.dao;

import java.io.Serializable;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.itam.domain.OutsideRepair;

/**
 * @Description:外修记录Dao接口
 * @Author:佘朝军
 * @Since :2016年3月2日 上午10:53:05
 */
public interface IOutsideRepairDao extends IBaseDao<OutsideRepair, Serializable> {
	
	public Page<OutsideRepair> getListForPage(Page<OutsideRepair> page, String startTime, String endTime);

}
