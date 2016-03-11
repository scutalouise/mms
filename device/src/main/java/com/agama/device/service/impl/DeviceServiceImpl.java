package com.agama.device.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.authority.service.IOrganizationService;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.DeviceUsedStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
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

/**
 * @Description:总体设备操作处理实现类
 * @Author:佘朝军
 * @Since :2016年1月15日 下午1:49:20
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
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
	@Autowired
	private IOrganizationService orgService;

	@Override
	public Map<String, Object> getDeviceByIdentifierForHandset(String identifier) throws Exception {
		FirstDeviceType type = getFirstDeviceTypeByIdentifier(identifier);
		switch (type) {
		case COLLECTDEVICE:
			return cds.getDetailByIdentifierForHandset(identifier);
		case HOSTDEVICE:
			return hds.getDetailByIdentifierForHandset(identifier);
		case NETWORKDEVICE:
			return nds.getDetailByIdentifierForHandset(identifier);
		case PEDEVICE:
			return peds.getDetailByIdentifierForHandset(identifier);
		case UNINTELLIGENTDEVICE:
			return uds.getDetailByIdentifierForHandset(identifier);
		default:
			break;
		}
		return null;
	}

	@Override
	public Object getDeviceByIdentifier(String identifier) throws Exception {
		FirstDeviceType type = getFirstDeviceTypeByIdentifier(identifier);
		switch (type) {
		case COLLECTDEVICE:
	 		return cds.getCollectionDeviceByIdentifier(identifier);
		case HOSTDEVICE:
			return hds.getHostDeviceByIdentifier(identifier);
		case NETWORKDEVICE:
			return nds.getNetworkDeviceByIdentifier(identifier);
		case PEDEVICE:
			return peds.getPeDeviceByIdentifier(identifier);
		case UNINTELLIGENTDEVICE:
			return uds.getUnintelligentDeviceByIdentifier(identifier);
		default:
			break;
		}
		return null;
	}

	@Override
	public List<Object> getDeviceListByOrgId(int orgId) throws Exception {
		List<Object> list = new ArrayList<Object>();
		
		list.addAll(hds.getNameAndIdentifierByOrgId(orgId));
		list.addAll(cds.getNameAndIdentifierByOrgId(orgId));
		list.addAll(nds.getNameAndIdentifierByOrgId(orgId));
		list.addAll(peds.getNameAndIdentifierByOrgId(orgId));
		list.addAll(uds.getNameAndIdentifierByOrgId(orgId));
		return list;
	}

	@Override
	public FirstDeviceType getFirstDeviceTypeByIdentifier(String identifier) throws Exception {
		String value = identifier.substring(0, 2);
		return FirstDeviceType.getEnumByValue(value);
	}

	@Override
	public List<Object> getAllDeviceList() throws Exception {
		List<Object> list = new ArrayList<Object>();

		list.addAll(hds.getAllList());
		list.addAll(cds.getAllList());
		list.addAll(nds.getAllList());
		list.addAll(peds.getAllList());
		list.addAll(uds.getAllList());
		return list;
	}

	@Override
	public Map<String, String> getDeviceMapByIdentifier(String identifier) throws Exception {
		return BeanUtils.describe(getDeviceByIdentifier(identifier));
	}

	@Override
	public Page<Object> getDeviceListByDeviceUsedStateEnum(Page<Object> page, DeviceUsedStateEnum dusEnum) throws Exception {
		List<Object> list = new ArrayList<Object>();
		list.addAll(hds.getListByDeviceUsedStateEnum(dusEnum));
		list.addAll(cds.getListByDeviceUsedStateEnum(dusEnum));
		list.addAll(nds.getListByDeviceUsedStateEnum(dusEnum));
		list.addAll(peds.getListByDeviceUsedStateEnum(dusEnum));
		list.addAll(uds.getListByDeviceUsedStateEnum(dusEnum));
		page.setTotalCount(list.size());
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		List<Object> deviceList = new ArrayList<Object>();
		for (int i = (pageNo - 1) * pageSize; i < pageNo * pageSize; i++) {
			if (list.size() > i) {
				deviceList.add(list.get(i));
			} else {
				break;
			}
		}
		page.setResult(deviceList);
		return page;
	}

	@Override
	public List<Object> getDeviceListByObtainUser(Integer userId) {
		List<Object> list = new ArrayList<Object>();
		list.addAll(hds.getListByObtainUser(userId));
		list.addAll(cds.getListByObtainUser(userId));
		list.addAll(nds.getListByObtainUser(userId));
		list.addAll(peds.getListByObtainUser(userId));
		list.addAll(uds.getListByObtainUser(userId));
		return list;
	}

	@Override
	public Page<Object> getPageListByQueryMap(Page<Object> page, Map<String, Object> map) {
		List<Object> list = new ArrayList<Object>();
		list.addAll(hds.getListByQueryMap(map));
		list.addAll(cds.getListByQueryMap(map));
		list.addAll(nds.getListByQueryMap(map));
		list.addAll(peds.getListByQueryMap(map));
		list.addAll(uds.getListByQueryMap(map));
		page.setTotalCount(list.size());
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		List<Object> deviceList = new ArrayList<Object>();
		for (int i = (pageNo - 1) * pageSize; i < pageNo * pageSize; i++) {
			if (list.size() > i) {
				deviceList.add(list.get(i));
			} else {
				break;
			}
		}
		page.setResult(deviceList);
		return page;
	}

	@Override
	public void updateDeviceUsedStateByIdentifierList(List<String> identifierList, DeviceUsedStateEnum dusEnum) {
		for (String identifier : identifierList) {
			try {
				FirstDeviceType type = getFirstDeviceTypeByIdentifier(identifier);
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
					UnintelligentDevice ud = uds.getUnintelligentDeviceByIdentifier(identifier);
					ud.setDeviceUsedState(dusEnum);
					uds.update(ud);
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	


}
