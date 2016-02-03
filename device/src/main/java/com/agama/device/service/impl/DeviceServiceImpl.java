package com.agama.device.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.authority.service.IOrganizationService;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.device.domain.HostDevice;
import com.agama.device.service.IDeviceService;
import com.agama.device.service.IHostDeviceService;

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
	private IOrganizationService orgService;

	@Override
	public Map<String, Object> getDeviceByIdentifierForHandset(String identifier) throws Exception {
		FirstDeviceType type = getFirstDeviceTypeByIdentifier(identifier);
		switch (type) {
		case COLLECTDEVICE:
			// TODO
			break;
		case HOSTDEVICE:
			return hds.getDetailByIdentifierForHandset(identifier);
		case NETWORKDEVICE:
			// TODO
			break;
		case PEDEVICE:
			// TODO
			break;
		case UNINTELLIGENTDEVICE:
			// TODO
			break;
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
			// TODO
			break;
		case HOSTDEVICE:
			return hds.getHostDeviceByIdentifier(identifier);
		case NETWORKDEVICE:
			// TODO
			break;
		case PEDEVICE:
			// TODO
			break;
		case UNINTELLIGENTDEVICE:
			// TODO
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	public List<Object> getDeviceListByOrgId(int orgId) throws Exception {
		List<Object> list = new ArrayList<Object>();

		List<Object> hdList = hds.getNameAndIdentifierByOrgId(orgId);
		// TODO
		list.addAll(hdList);
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

		List<HostDevice> hdList = hds.getAllList();
		// TODO
		list.addAll(hdList);
		return list;
	}

	@Override
	public Map<String, String> getDeviceMapByIdentifier(String identifier) throws Exception {
		return BeanUtils.describe(getDeviceByIdentifier(identifier));
	}

}
