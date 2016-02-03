package com.agama.device.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.service.IUserService;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.dao.ITwoDimentionCodeDao;
import com.agama.device.domain.CollectionDevice;
import com.agama.device.domain.DeviceInventory;
import com.agama.device.domain.HostDevice;
import com.agama.device.domain.NetworkDevice;
import com.agama.device.domain.PeDevice;
import com.agama.device.domain.TwoDimentionCode;
import com.agama.device.domain.UnintelligentDevice;
import com.agama.device.service.ICollectionDeviceService;
import com.agama.device.service.IDeviceInventoryService;
import com.agama.device.service.IHostDeviceService;
import com.agama.device.service.INetworkDeviceService;
import com.agama.device.service.IPeDeviceService;
import com.agama.device.service.ITwoDimentionCodeService;
import com.agama.device.service.IUnintelligentDeviceService;
import com.agama.device.utils.TwoDimentionCodeUtil;
import com.agama.tool.utils.date.DateUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
@Transactional
public class TwoDimentionCodeServiceImpl extends BaseServiceImpl<TwoDimentionCode, Integer>implements ITwoDimentionCodeService {

	@Autowired
	private IUserService userService;
	@Autowired
	private ICollectionDeviceService collectionDeviceService;
	@Autowired
	private IHostDeviceService hostDeviceService;
	@Autowired
	private INetworkDeviceService networkDeviceService;
	@Autowired
	private IPeDeviceService peDeviceService;
	@Autowired
	private IUnintelligentDeviceService unintelligentDeviceService;
	@Autowired
	private IDeviceInventoryService deviceInventoryService;
	@Autowired
	private ITwoDimentionCodeDao twoDimentionCodeDao;


	@Override
	public TwoDimentionCode getByidentifier(String identifier) {
		return twoDimentionCodeDao.findUniqueBy("identifier", identifier);
	}

	@SuppressWarnings({ "incomplete-switch" })
	@Override
	public String createQR(String identifier, Integer id, HttpServletRequest request, String pathEdit)
			throws JsonProcessingException {
		String path = "";
		FirstDeviceType firstDeviceType = FirstDeviceType.getEnumByValue(identifier.substring(0, 2));
		switch (firstDeviceType) {
		case COLLECTDEVICE:
			CollectionDevice collectionDevice = collectionDeviceService.get(id);
			DeviceInventory deviceInventory_cd = deviceInventoryService
					.getDeviceInventoryByPurchaseId(collectionDevice.getPurchaseId());
			String phone_cd = userService.getPhoneNumberById(collectionDevice.getManagerId());
			Map<String, Object> map_cd = new HashMap<String, Object>();
			map_cd.put("identifier", collectionDevice.getIdentifier());
			map_cd.put("brand", deviceInventory_cd.getBrandName());
			map_cd.put("model", collectionDevice.getModel());
			map_cd.put("manufactureDate", DateUtils.formatDate(collectionDevice.getManufactureDate()));
			map_cd.put("warrantyDate", DateUtils.formatDate(collectionDevice.getWarrantyDate()));
			path = TwoDimentionCodeUtil.matrixToImage(map_cd, phone_cd, request, pathEdit);
			break;
		case HOSTDEVICE:
			HostDevice hostDevice = hostDeviceService.get(id);
			DeviceInventory deviceInventory_hd = deviceInventoryService
					.getDeviceInventoryByPurchaseId(hostDevice.getPurchaseId());
			String phone_hd = userService.getPhoneNumberById(hostDevice.getManagerId());
			Map<String, Object> map_hd = new HashMap<String, Object>();
			map_hd.put("identifier", hostDevice.getIdentifier());
			map_hd.put("brand", deviceInventory_hd.getBrandName());
			map_hd.put("model", hostDevice.getModel());
			map_hd.put("manufactureDate", DateUtils.formatDate(hostDevice.getManufactureDate()));
			map_hd.put("warrantyDate", DateUtils.formatDate(hostDevice.getWarrantyDate()));
			path = TwoDimentionCodeUtil.matrixToImage(map_hd, phone_hd, request, pathEdit);
			break;
		case NETWORKDEVICE:
			NetworkDevice networkDevice = networkDeviceService.get(id);
			DeviceInventory deviceInventory_nd = deviceInventoryService
					.getDeviceInventoryByPurchaseId(networkDevice.getPurchaseId());
			String phone_nd = userService.getPhoneNumberById(networkDevice.getManagerId());
			Map<String, Object> map_nd = new HashMap<String, Object>();
			map_nd.put("identifier", networkDevice.getIdentifier());
			map_nd.put("brand", deviceInventory_nd.getBrandName());
			map_nd.put("model", networkDevice.getModel());
			map_nd.put("manufactureDate", DateUtils.formatDate(networkDevice.getManufactureDate()));
			map_nd.put("warrantyDate", DateUtils.formatDate(networkDevice.getWarrantyDate()));
			path = TwoDimentionCodeUtil.matrixToImage(map_nd, phone_nd, request, pathEdit);
			break;
		case PEDEVICE:
			PeDevice peDevice = peDeviceService.get(id);
			DeviceInventory deviceInventory_pd = deviceInventoryService
					.getDeviceInventoryByPurchaseId(peDevice.getPurchaseId());
			String phone_pd = userService.getPhoneNumberById(peDevice.getManagerId());
			Map<String, Object> map_pd = new HashMap<String, Object>();
			map_pd.put("identifier", peDevice.getIdentifier());
			map_pd.put("brand", deviceInventory_pd.getBrandName());
			map_pd.put("model", peDevice.getModel());
			map_pd.put("manufactureDate", DateUtils.formatDate(peDevice.getManufactureDate()));
			map_pd.put("warrantyDate", DateUtils.formatDate(peDevice.getWarrantyDate()));
			path = TwoDimentionCodeUtil.matrixToImage(map_pd, phone_pd, request, pathEdit);
			break;
		case UNINTELLIGENTDEVICE:
			UnintelligentDevice unintelligentDevice = unintelligentDeviceService.get(id);
			DeviceInventory deviceInventory_ud = deviceInventoryService
					.getDeviceInventoryByPurchaseId(unintelligentDevice.getPurchaseId());
			String phone_ud = userService.getPhoneNumberById(unintelligentDevice.getManagerId());
			Map<String, Object> map_ud = new HashMap<String, Object>();
			map_ud.put("identifier", unintelligentDevice.getIdentifier());
			map_ud.put("brand", deviceInventory_ud.getBrandName());
			map_ud.put("model", unintelligentDevice.getModel());
			map_ud.put("manufactureDate", DateUtils.formatDate(unintelligentDevice.getManufactureDate()));
			map_ud.put("warrantyDate", DateUtils.formatDate(unintelligentDevice.getWarrantyDate()));
			path = TwoDimentionCodeUtil.matrixToImage(map_ud, phone_ud, request, pathEdit);
			break;
		}
		return path;
	}
}
