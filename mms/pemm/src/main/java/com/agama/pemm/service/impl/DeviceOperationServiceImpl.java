package com.agama.pemm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IDeviceOperationService;
import com.agama.pemm.utils.SNMPUtil;

@Service
public class DeviceOperationServiceImpl implements IDeviceOperationService {
	@Autowired
	private IGitInfoDao gitInfoDao;

	@Override
	public void upsOperation(Integer gitInfoId, String upsOid,
			Integer instruction) {
		GitInfo gitInfo=gitInfoDao.find(gitInfoId);
		SNMPUtil snmpUtil=new SNMPUtil();
		try {
			snmpUtil.sendSetCommand(gitInfo.getIp(), upsOid, instruction);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
