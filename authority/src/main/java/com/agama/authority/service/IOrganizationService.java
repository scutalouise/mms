package com.agama.authority.service;

import java.util.List;

import com.agama.authority.entity.Organization;
import com.agama.common.domain.TreeBean;
import com.agama.common.service.IBaseService;

/**
 * @Description:区域service
 * @Author:scuta
 * @Since :2015年8月27日 上午10:09:23
 */
public interface IOrganizationService extends IBaseService<Organization, Integer> {

	public List<TreeBean> getTreeByPid(Integer pid);

	public String getOrganizationIdStrById(Integer organizationId);

	/**
	 * @Description: 根据网关Id字符串查询机构名称集合
	 * @param gitIds
	 * @return
	 * @Since :2015年11月3日 上午11:38:59
	 */
	public List<Organization> getOrganizationListByGitIds(String gitIds);

	public Organization getOrganizationByOrgCode(String orgCode);

}
