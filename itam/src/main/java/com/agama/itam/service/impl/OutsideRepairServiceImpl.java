package com.agama.itam.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.entity.User;
import com.agama.authority.service.IUserService;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.domain.CollectionDevice;
import com.agama.device.domain.HostDevice;
import com.agama.device.domain.NetworkDevice;
import com.agama.device.domain.PeDevice;
import com.agama.device.domain.UnintelligentDevice;
import com.agama.device.service.ICollectionDeviceService;
import com.agama.device.service.IDeviceService;
import com.agama.device.service.IHostDeviceService;
import com.agama.device.service.INetworkDeviceService;
import com.agama.device.service.IPeDeviceService;
import com.agama.device.service.IUnintelligentDeviceService;
import com.agama.itam.dao.IOutsideRepairDao;
import com.agama.itam.domain.OutsideRepair;
import com.agama.itam.service.IOutsideRepairService;

/**@Description:外修记录业务层实现类
 * @Author:佘朝军
 * @Since :2016年3月2日 上午11:56:24
 */
@Service
@Transactional(readOnly = true)
public class OutsideRepairServiceImpl extends BaseServiceImpl<OutsideRepair, Serializable> implements IOutsideRepairService{

	@Autowired
	private IOutsideRepairDao orDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDeviceService deviceService;
	
	@Autowired
	private IHostDeviceService hds;
	@Autowired
	private ICollectionDeviceService cds;
	@Autowired
	private INetworkDeviceService nds;
	@Autowired
	private IPeDeviceService peds;
	@Autowired
	private IUnintelligentDeviceService uds;
	
	@Override
	public Page<OutsideRepair> getListForPage(Page<OutsideRepair> page, String startTime, String endTime) throws Exception {
		page = orDao.getListForPage(page, startTime, endTime);
		List<OutsideRepair> list = page.getResult();
		for (OutsideRepair or : list) {
			Map<String, String> deviceMap = deviceService.getDeviceMapByIdentifier(or.getIdentifier());
			or.setDeviceName(deviceMap.get("name"));
			if (or.getReturnReceiver() != null) {
				User user = userService.get(or.getReturnReceiver());
				or.setReturnReceiverName(user.getName());
			} else {
				or.setReturnReceiverName("");
			}
		}
		return page;
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteOutsideRepair(OutsideRepair or) {
		or.setStatus(StatusEnum.DELETED);
		orDao.update(or);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveByIdentifiers(OutsideRepair or, List<String> identifierList) throws Exception {
		for (String identifier : identifierList) {
			OutsideRepair obj = new OutsideRepair();
			obj.setFirm(or.getFirm());
			obj.setIdentifier(identifier);
			obj.setRepairOpertor(or.getRepairOpertor());
			obj.setRepairReceiver(or.getRepairReceiver());
			obj.setRepairRemark(or.getRepairRemark());
			obj.setRepairTime(new Date());
			obj.setStatus(StatusEnum.NORMAL);
			orDao.save(obj);
			changeDeviceStatus(identifier, DeviceUsedStateEnum.EXTERNALMAINTENANCE);
		}
		
	}
	
	private void changeDeviceStatus(String identifier, DeviceUsedStateEnum dusEnum) throws Exception{
		FirstDeviceType type = deviceService.getFirstDeviceTypeByIdentifier(identifier);
		switch (type) {
		case COLLECTDEVICE:
			CollectionDevice cd = cds.getCollectionDeviceByIdentifier(identifier);
			cd.setDeviceUsedState(dusEnum);
			cds.update(cd);
			break;
		case HOSTDEVICE:
			HostDevice hd = hds.getHostDeviceByIdentifier(identifier);
			hd.setDeviceUsedState(dusEnum);
			hds.update(hd);
			break;
		case NETWORKDEVICE:
			NetworkDevice nd = nds.getNetworkDeviceByIdentifier(identifier);
			nd.setDeviceUsedState(dusEnum);
			nds.update(nd);
			break;
		case PEDEVICE:
			PeDevice ped = peds.getPeDeviceByIdentifier(identifier);
			ped.setDeviceUsedState(dusEnum);
			peds.update(ped);
			break;
		case UNINTELLIGENTDEVICE:
			UnintelligentDevice ud =  uds.getUnintelligentDeviceByIdentifier(identifier);
			ud.setDeviceUsedState(dusEnum);
			uds.update(ud);
			break;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void updateRepairRecord(OutsideRepair or) throws Exception {
		orDao.update(or);
		String result = or.getRepairResult();
		if ("true".equals(result)) {
			changeDeviceStatus(or.getIdentifier(), DeviceUsedStateEnum.PUTINSTORAGE);
		} else if ("false".equals(result)) {
			changeDeviceStatus(or.getIdentifier(), DeviceUsedStateEnum.SCRAP);
		}
	}

}
