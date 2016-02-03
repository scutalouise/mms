package com.agama.pemm.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.domain.StateEnum;
import com.agama.pemm.domain.GitInfo;

public interface IGitInfoDao extends IBaseDao<GitInfo, Integer>{

	public void updateStatusByIds(String ids);
	/**
	 * 查询所有状态正常的网关设备
	 * @param status
	 * @return
	 */
	public List<GitInfo> findListByStatus(Integer status);
	/** 
	 * @Description:根据机构Id字符串查询网关集合
	 * @param organizationIds 机构字符串Id
	 * @return
	 * @Since :2015年11月2日 下午12:57:10
	 */
	public List<GitInfo> findListByOrganizationIdS(String organizationIds);
	/**
	 * @Description:根据gitId修改git对象状态
	 * @param id
	 * @param stateEnum
	 * @Since :2016年1月19日 上午10:27:19
	 */
	public void updateCurrentStateById(Integer id,StateEnum stateEnum);



	

}
