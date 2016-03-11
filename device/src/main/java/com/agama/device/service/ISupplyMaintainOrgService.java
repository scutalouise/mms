package com.agama.device.service;

import java.util.List;

import com.agama.authority.entity.User;
import com.agama.common.domain.TreeBean;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.SupplyMaintainOrg;

/**
 * @Description:运维机构service接口
 * @Author:scuta
 * @Since :2015年8月27日 上午10:09:23
 */
public interface ISupplyMaintainOrgService extends IBaseService<SupplyMaintainOrg, Integer> {

	public List<TreeBean> getTreeByPid(Integer pid);

	public String getSupplyMaintainOrgIdStrById(Integer SupplyMaintainOrgId);

	/**
	 * @Description:添加支持回收站的逻辑删除；
	 * @param id
	 * @param opUserId
	 * @Since :2016年2月25日 下午4:31:14
	 */
	public void delete(Integer id, Integer opUserId) ;
	
	/**
	 * 获取用户拥有机构id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getOrgIdList(Integer userId) ;
	
	/**
	 * 添加修改用户机构
	 * 
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	public void updateUserOrg(Integer userId,List<Integer> newList) ;
	
	/**
	 * 通过品牌Id查询供应商运维组织
	 * @Description
	 * @param brandId
	 * @return
	 */
	public List<SupplyMaintainOrg> getSupplyMaintainOrgListByBrandId(Integer brandId);
	
	/**
	 * 根据运维组织id获取旗下运维人员列表
	 * @Description
	 * @param orgId
	 * @return
	 */
	public List<User> getUserListByOrgId(Integer orgId);

}
