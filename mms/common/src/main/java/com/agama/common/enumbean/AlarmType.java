package com.agama.common.enumbean;

import java.util.ArrayList;
import java.util.List;

public enum AlarmType {
	COMMUNICATIONSTATUS("通讯状态", DeviceInterfaceType.UPS),
	BYPASSVOLTAGE("UPS旁路电压",DeviceInterfaceType.UPS),
	INPUTVOLTAGE("输入电压",DeviceInterfaceType.UPS),
	OUTPUTVOLTAGE("输出电压",DeviceInterfaceType.UPS),
	INTERNALTEMPERATURE("机内温度",DeviceInterfaceType.UPS),
	BATTERYVOLTAGE("电池电压",DeviceInterfaceType.UPS),
	ELECTRICQUANTITY("电池电量",DeviceInterfaceType.UPS),
	LOAD("负载",DeviceInterfaceType.UPS),
	
	TEMPERATURE("温度",
			DeviceInterfaceType.TH),
	HUMIDITY("湿度",DeviceInterfaceType.TH),
	WATERIMMERSIONSTATE("水浸状态",DeviceInterfaceType.WATER),
	SMOKESTATE("烟感状态",DeviceInterfaceType.SMOKE);
	
	private String name; // 名称

	private DeviceInterfaceType deviceInterfaceType; // 设备类型

	// 构造方法
	private AlarmType(String name, DeviceInterfaceType deviceInterfaceType) {
		this.name = name;
		
		this.deviceInterfaceType = deviceInterfaceType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public DeviceInterfaceType getDeviceInterfaceType() {
		return deviceInterfaceType;
	}

	public void setDeviceInterfaceType(DeviceInterfaceType deviceInterfaceType) {
		this.deviceInterfaceType = deviceInterfaceType;
	}

	/**
	 * @Description:通过设备类型获取设备告警类型
	 * @param deviceType 设备类型
	 * @return 报警类型集合
	 * @Since :2015年9月28日 下午3:19:40
	 */
	public static List<AlarmType> getAlarmTypeByDeviceInterfaceType(DeviceInterfaceType deviceInterfaceType){
		List<AlarmType> alarmTypes = new ArrayList<AlarmType>();
		for (AlarmType a : AlarmType.values()) {
			if (a.getDeviceInterfaceType() == deviceInterfaceType) {
				alarmTypes.add(a);
			}
		}
		return alarmTypes;
	}

}
