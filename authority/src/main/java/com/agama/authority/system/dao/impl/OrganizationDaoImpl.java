package com.agama.authority.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.authority.system.dao.IOrganizationDao;
import com.agama.authority.system.entity.Organization;
import com.agama.common.dao.impl.HibernateDaoImpl;

/**
 * @Description:机构DAO实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:35:08
 */
@Repository("organizationDao")
public class OrganizationDaoImpl extends HibernateDaoImpl<Organization, Integer> implements IOrganizationDao{

}
