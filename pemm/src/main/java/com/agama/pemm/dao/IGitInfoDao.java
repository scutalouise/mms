package com.agama.pemm.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.pemm.domain.GitInfo;

public interface IGitInfoDao extends IBaseDao<GitInfo, Integer>{

	public void updateStatusByIds(String ids);
	/**
	 * 查询所有状态正常的网关设备
	 * @param status
	 * @return
	 */
	public List<GitInfo> findListByStatus(Integer status);



	

}
