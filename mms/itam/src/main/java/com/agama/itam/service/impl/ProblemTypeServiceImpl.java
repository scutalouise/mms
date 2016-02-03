package com.agama.itam.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.service.IDeviceService;
import com.agama.itam.dao.IProblemTypeDao;
import com.agama.itam.domain.ProblemType;
import com.agama.itam.service.IProblemTypeService;

/**
 * @Description:问题类型业务实现类
 * @Author:佘朝军
 * @Since :2016年1月19日 上午9:55:08
 */
@Service
@Transactional(readOnly = true)
public class ProblemTypeServiceImpl extends BaseServiceImpl<ProblemType, Serializable> implements IProblemTypeService {

	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IProblemTypeDao problemTypeDao;

	@Override
	public List<ProblemType> getListByIdentifier(String identifier) throws Exception {
		FirstDeviceType deviceType = deviceService.getFirstDeviceTypeByIdentifier(identifier);
		return problemTypeDao.getListByDeviceType(deviceType);
	}

	@Override
	public List<Map<String, Object>> getListByIdentifierForHandset(String identifier) throws Exception {
		FirstDeviceType deviceType = deviceService.getFirstDeviceTypeByIdentifier(identifier);
		List<ProblemType> typeList = problemTypeDao.getListByDeviceType(deviceType);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (ProblemType problemType : typeList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", problemType.getId());
			map.put("name", problemType.getName());
			list.add(map);
		}
		return list;
	}

	@Override
	public List<ProblemType> getAllList() throws Exception {
		return problemTypeDao.getAllList();
	}
}
