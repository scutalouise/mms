package com.agama.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.ICollectionDeviceDao;
import com.agama.device.domain.CollectionDevice;
import com.agama.device.service.ICollectionDeviceService;

@Service
@Transactional
public class CollectionDeviceServiceImpl extends BaseServiceImpl<CollectionDevice, Integer>
		implements ICollectionDeviceService {

	@Autowired
	private ICollectionDeviceDao collectionDeviceDao;
	
	@Override
	public List<CollectionDevice> getListByStatus(StatusEnum status) {
		// TODO Auto-generated method stub
		return collectionDeviceDao.getListByStatus(status);
	}
	

}
