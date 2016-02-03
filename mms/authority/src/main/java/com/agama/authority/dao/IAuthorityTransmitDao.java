package com.agama.authority.dao;

import java.util.List;

import com.agama.authority.entity.AuthorityTransmit;
import com.agama.common.dao.IBaseDao;

/**
 * @Description:权限移交
 * @Author:scuta
 * @Since :2015年12月30日 上午10:52:31
 */
public interface IAuthorityTransmitDao extends IBaseDao<AuthorityTransmit, Integer> {

	/**
	 * @Description:权限移交的属性查找，且其状态为已经已经移交的状态，非已经撤销的状态
	 * @param propertyName
	 * @param value
	 * @return
	 * @Since :2015年12月31日 上午11:06:01
	 */
	public List<AuthorityTransmit> findByProperty(String propertyName, Object value);
	
	/**
	 * @Description:判断是否为接受了移交权限的人；
	 * @param acceptUserId
	 * @return
	 * @Since :2015年12月31日 上午11:07:39
	 */
	public List<AuthorityTransmit> isTransmited(Integer acceptUserId);
	
}
