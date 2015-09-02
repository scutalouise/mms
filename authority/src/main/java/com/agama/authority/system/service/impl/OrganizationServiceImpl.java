package com.agama.authority.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.system.dao.IOrganizationDao;
import com.agama.authority.system.entity.Organization;
import com.agama.authority.system.service.IOrganizationService;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * @Description:区域service实现
 * @Author:scuta
 * @Since :2015年8月27日 上午10:09:47
 */
@Service("organizationService")
@Transactional(readOnly=true)
public class OrganizationServiceImpl extends BaseServiceImpl<Organization, Integer> implements IOrganizationService{
	
	@Autowired
	private IOrganizationDao organizationDao;
	
}
