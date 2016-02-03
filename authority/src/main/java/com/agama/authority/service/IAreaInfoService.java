package com.agama.authority.service;

import java.util.List;

import com.agama.authority.entity.AreaInfo;
import com.agama.common.domain.StateEnum;
import com.agama.common.domain.TreeBean;
import com.agama.common.service.IBaseService;

/**
 * @Description:区域service
 * @Author:scuta
 * @Since :2015年8月27日 上午9:51:20
 */
public interface IAreaInfoService extends IBaseService<AreaInfo, Integer>{
	/**
	 * @Description:根据pid获取树结构
	 * @param pid
	 * @return
	 * @Since :2015年10月12日 下午5:14:49
	 */
	public List<TreeBean> getTreeByPid(Integer pid);
	/**
	 * 获取区域下面所有区域的ID凭借为字符串并用，符号隔开
	 * @param id
	 * @return
	 */
	public String getAreaInfoIdStrById(Integer id);
	/**
	 * @param searchValue 
	 * @Description: 查询所有关联了机构的区域
	 * @return
	 * @Since :2015年10月30日 下午1:30:32
	 */
	public List<AreaInfo> getListRelevancyOrganization(StateEnum stateEnum, String searchValue);
	
	
	
}
