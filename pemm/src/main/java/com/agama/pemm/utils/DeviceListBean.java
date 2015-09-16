package com.agama.pemm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeviceListBean {
	private List<Map<String,Object>> deviceIndexList=new ArrayList<Map<String,Object>>();

	public List<Map<String, Object>> getDeviceIndexList() {
		return deviceIndexList;
	}

	public void setDeviceIndexList(List<Map<String, Object>> deviceIndexList) {
		this.deviceIndexList = deviceIndexList;
	}
	

}
