package com.agama.itam.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.itam.dao.IArrangeDao;
import com.agama.itam.domain.Arrange;
import com.agama.itam.service.IArrangeService;

/**
 * @Description:
 * @Author:佘朝军
 * @Since :2016年2月1日 下午3:50:53
 */
@Transactional(readOnly = true)
@Service
public class ArrangeServiceImpl extends BaseServiceImpl<Arrange, Serializable> implements IArrangeService {
	@Autowired
	private IArrangeDao arrangeDao;

	public Arrange findByUserId(Integer userId) {
		return userId == null ? null : arrangeDao.findByUserId(userId);
	}
	
	public Arrange findEnableArrangeByUserId(Integer userId) {
		return userId == null ? null : arrangeDao.findEnableArrangeByUserId(userId);
	}

}
