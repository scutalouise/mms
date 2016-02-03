package com.agama.itam.protocol.snmp;

import java.util.ArrayList;
import java.util.List;

import org.snmp4j.PDU;

public enum UpsOidInfo {
	// UPS名称
	upsName("1.3.6.1.4.1.34651.2.1.1.1", PDU.GET),
	// 接口类型
	interfaceType("1.3.6.1.4.1.34651.2.1.1.2", PDU.GET),
	// 通讯状态
	communicationStatus("1.3.6.1.4.1.34651.2.1.1.4", PDU.GET),
	// 放电模式
	dischargePatterns("1.3.6.1.4.1.34651.2.1.1.5", PDU.GET),
	// UPS类型
	upsType("1.3.6.1.4.1.34651.2.1.1.6", PDU.GET),
	// 型号
	modelNumber("1.3.6.1.4.1.34651.2.1.1.10", PDU.GET),
	// 厂商品牌
	brand("1.3.6.1.4.1.34651.2.1.1.11", PDU.GET),
	// 版本号
	versionNumber("1.3.6.1.4.1.34651.2.1.1.12", PDU.GET),
	// 额定电压
	ratedVoltage("1.3.6.1.4.1.34651.2.1.1.13", PDU.GET),
	// 额定电流
	ratedCurrent("1.3.6.1.4.1.34651.2.1.1.14", PDU.GET),
	// 额定频率
	ratedFrequency("1.3.6.1.4.1.34651.2.1.1.16", PDU.GET),
	// 电池电压
	batteryVoltage("1.3.6.1.4.1.34651.2.1.1.15", PDU.GET),
	// 功率
	power("1.3.6.1.4.1.34651.2.1.1.17", PDU.GET),
	// UPS状态
	upsStatus("1.3.6.1.4.1.34651.2.1.1.20", PDU.GET),
	// *频率
	frequency("1.3.6.1.4.1.34651.2.1.1.30", PDU.GET),
	// 机内温度
	internalTemperature("1.3.6.1.4.1.34651.2.1.1.21", PDU.GET),
	// *旁路电压
	bypassVoltage("1.3.6.1.4.1.34651.2.1.1.22", PDU.GET),
	// 旁路频率
	bypassFrequency("1.3.6.1.4.1.34651.2.1.1.23", PDU.GET),
	// 输入电压
	inputVoltage("1.3.6.1.4.1.34651.2.1.1.31", PDU.GET),
	// 输出电压
	outputVoltage("1.3.6.1.4.1.34651.2.1.1.40", PDU.GET),
	// 故障电压
	errorVoltage("1.3.6.1.4.1.34651.2.1.1.32", PDU.GET),
	// 负载
	load("1.3.6.1.4.1.34651.2.1.1.41", PDU.GET),
	// *输出频率
	outputFrenquency("1.3.6.1.4.1.34651.2.1.1.42", PDU.GET),
	// 单节电压
	singleVoltage("1.3.6.1.4.1.34651.2.1.1.50", PDU.GET),
	// *总电压
	totalVoltage("1.3.6.1.4.1.34651.2.1.1.51", PDU.GET),
	// 充电量
	electricQuantity("1.3.6.1.4.1.34651.2.1.1.52", PDU.GET),
	// *充/放电电流
	passCurrent("1.3.6.1.4.1.34651.2.1.1.53", PDU.GET),
	// *剩余时间
	remainingTime("1.3.6.1.4.1.34651.2.1.1.54", PDU.GET),
	//放电测试
	dischargeTest("1.3.6.1.4.1.34651.2.1.1.60",PDU.SET),
	//UPS关机
	shutdown("1.3.6.1.4.1.34651.2.1.1.62",PDU.SET),
	//蜂鸣器控制
	buzzerControl("1.3.6.1.4.1.34651.2.1.1.61",PDU.SET),
	
	//上次获取时间间隔
	lastCollectTnterval("1.3.6.1.4.1.34651.2.1.1.99",PDU.SET); 


	private String oid;

	private int mode;

	private UpsOidInfo(String oid, int mode) {
		this.oid = oid;
		this.mode = mode;

	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
//	public static List<String> getUpsBasicOidList() {
//		List<String> list = new ArrayList<String>();
//		// UPS名称
//		list.add("1.3.6.1.4.1.34651.2.1.1.1.1");
//		// 接口类型
//		list.add("1.3.6.1.4.1.34651.2.1.1.2.1");
//
//		// 通讯状态
//		list.add("1.3.6.1.4.1.34651.2.1.1.4.1");
//		// 放电模式
//		list.add("1.3.6.1.4.1.34651.2.1.1.5.1");
//		// UPS类型
//		list.add("1.3.6.1.4.1.34651.2.1.1.6.1");
//		// 型号
//		list.add("1.3.6.1.4.1.34651.2.1.1.10.1");
//		// 厂商品牌
//		list.add("1.3.6.1.4.1.34651.2.1.1.11.1");
//		// 版本号
//		list.add("1.3.6.1.4.1.34651.2.1.1.12.1");
//		// 额定电压
//		list.add("1.3.6.1.4.1.34651.2.1.1.13.1");
//		// 额定电流
//		list.add("1.3.6.1.4.1.34651.2.1.1.14.1");
//		// 额定频率
//		list.add("1.3.6.1.4.1.34651.2.1.1.16.1");
//		// 电池电压
//		list.add("1.3.6.1.4.1.34651.2.1.1.15.1");
//		// 功率
//		list.add("1.3.6.1.4.1.34651.2.1.1.17.1");
//		// UPS状态
//		list.add("1.3.6.1.4.1.34651.2.1.1.20.1");
//		// *频率
//		list.add("1.3.6.1.4.1.34651.2.1.1.30.1");
//		// 机内温度
//		list.add("1.3.6.1.4.1.34651.2.1.1.21.1");
//		
//		// 旁路频率
//		list.add("1.3.6.1.4.1.34651.2.1.1.23.1");
//		// 输入电压
//		list.add("1.3.6.1.4.1.34651.2.1.1.31.1");
//		// 输出电压
//		list.add("1.3.6.1.4.1.34651.2.1.1.40.1");
//		// 故障电压
//		list.add("1.3.6.1.4.1.34651.2.1.1.32.1");
//		// 负载
//		list.add("1.3.6.1.4.1.34651.2.1.1.41.1");
//		// *输出频率
//		list.add("1.3.6.1.4.1.34651.2.1.1.42.1");
//		// 单节电压
//		list.add("1.3.6.1.4.1.34651.2.1.1.50.1");
//		// *总电压
//		list.add("1.3.6.1.4.1.34651.2.1.1.51.1");
//		// 充电量
//		list.add("1.3.6.1.4.1.34651.2.1.1.52.1");
//		// *充/放电电流
//		list.add("1.3.6.1.4.1.34651.2.1.1.53.1");
//		// *剩余时间
//		list.add("1.3.6.1.4.1.34651.2.1.1.54.1");
//
//		return list;
//
//	}

}
