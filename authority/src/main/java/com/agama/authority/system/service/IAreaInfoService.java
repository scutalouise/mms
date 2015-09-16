package com.agama.authority.system.service;

import java.util.List;

import com.agama.authority.system.entity.AreaInfo;
import com.agama.common.domain.TreeBean;
import com.agama.common.service.IBaseService;

/**
 * @Description:区域service
 * @Author:scuta
 * @Since :2015年8月27日 上午9:51:20
 */
public interface IAreaInfoService extends IBaseService<AreaInfo, Integer>{
	public List<TreeBean> getTreeByPid(Integer pid);
	/**
	 * 获取区域下面所有区域的ID凭借为字符串并用，符号隔开
	 * @param id
	 * @return
	 */
	public String getAreaInfoIdStrById(Integer id);
	
	
	
}
