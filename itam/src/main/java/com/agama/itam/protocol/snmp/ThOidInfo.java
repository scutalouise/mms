package com.agama.itam.protocol.snmp;

import org.snmp4j.PDU;

public enum ThOidInfo {
	//温湿度设备名称
	thName("1.3.6.1.4.1.34651.2.2.1.1",PDU.GET),
	//设备型号
	modelNumber("1.3.6.1.4.1.34651.2.2.1.2",PDU.GET),
	//接口类型
	interfaceType("1.3.6.1.4.1.34651.2.2.1.3",PDU.GET),
	//温度
	temperature("1.3.6.1.4.1.34651.2.2.1.10",PDU.GET),
	//湿度
	humidity("1.3.6.1.4.1.34651.2.2.1.11",PDU.GET),
	//上次获取时间间隔
  lastCollectInterval("1.3.6.1.4.1.34651.2.2.1.99",PDU.GET); 
	private String oid;

	private int mode;
	private ThOidInfo(String oid, int mode) {
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
	
	
}
