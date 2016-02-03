package com.agama.authority.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IAuthorityTransmitDao;
import com.agama.authority.entity.AuthorityTransmit;
import com.agama.authority.service.IAuthorityTransmitService;
import com.agama.common.service.impl.BaseServiceImpl;

@Service("authorityTransmitService")
@Transactional(readOnly=true)
public class AuthorityTransmitServiceImpl extends BaseServiceImpl<AuthorityTransmit, Integer>
		implements IAuthorityTransmitService {

	@Autowired
	private IAuthorityTransmitDao authorityTransmitDao;

	/**
	 * 根据属性查找对象是否存在
	 */
	public boolean isExistsByProperty(String propertyName, Object value) {
		List<AuthorityTransmit>  userList = authorityTransmitDao.findByProperty(propertyName, value);
		return userList.size() > 0 ? true : false;
	}

	/**
	 * 判断是否为接受了移交权限的人；
	 */
	@Override
	public boolean isTransmited(Integer acceptUserId) {
		List<AuthorityTransmit>  userList = authorityTransmitDao.isTransmited(acceptUserId);
		return userList.size() > 0 ? true : false;
	}

}
