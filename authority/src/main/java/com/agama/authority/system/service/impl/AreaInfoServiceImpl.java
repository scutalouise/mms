package com.agama.authority.system.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.authority.system.dao.IAreaInfoDao;
import com.agama.authority.system.entity.AreaInfo;
import com.agama.authority.system.service.IAreaInfoService;
import com.agama.common.service.impl.BaseServiceImpl;

@Service("areaInfoService")
@Transactional
public class AreaInfoServiceImpl extends BaseServiceImpl<AreaInfo, Integer> implements IAreaInfoService {
	
	@Autowired
	private IAreaInfoDao areaInfoDao;
	
}
