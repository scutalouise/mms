package com.agama.authority.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IDictDao;
import com.agama.authority.entity.Dict;
import com.agama.authority.service.IDictService;
import com.agama.common.service.impl.BaseServiceImpl;

/**
 * @Description:字典service实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:59:38
 */
@Service
@Transactional(readOnly = true)
public class DictServiceImpl extends BaseServiceImpl<Dict, Integer> implements IDictService {

	@Autowired
	private IDictDao dictDao;

}
