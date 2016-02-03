package com.agama.pemm.protocol.snmp;

public enum AcOidInfo {
	//名称
	NAME("1.3.6.1.4.1.34651.2.7.1.1"),
	//设备类型
	DEVICETYPE("1.3.6.1.4.1.34651.2.7.1.2"),
	//类型名称
	TYPENAME("1.3.6.1.4.1.34651.2.7.1.3"),
	//接口类型
	INTERFACETYPE("1.3.6.1.4.1.34651.2.7.1.4"),
	//电表编号
	ELECTRICNUMBER("1.3.6.1.4.1.34651.2.7.1.5"),
	//设备编号
	DEVICENUMBER("1.3.6.1.4.1.34651.2.7.1.6"),
	//厂商
	VENDOR("1.3.6.1.4.1.34651.2.7.1.7"),
	//软件版本
	SOFTWAREVERSION("1.3.6.1.4.1.34651.2.7.1.8"),
	//报警名称
	ALARMNAME("1.3.6.1.4.1.34651.2.7.1.9"),
	//通讯状态
	COMMUNICATIONSTATUS("1.3.6.1.4.1.34651.2.7.1.10"),
	//设定温度
	SETTEMPERATURE("1.3.6.1.4.1.34651.2.7.1.20"),
	//设定湿度
	SETHUMIDITY("1.3.6.1.4.1.34651.2.7.1.21"),
	//室内温度
	INDOORTEMPERATURE("1.3.6.1.4.1.34651.2.7.1.30"),
	//室内湿度
	INDOORHUMIDITY("1.3.6.1.4.1.34651.2.7.1.31"),
	//室外温度
	OUTDOORTEMPERATURE("1.3.6.1.4.1.34651.2.7.1.32"),
	//运行状态
	RUNSTATE("1.3.6.1.4.1.34651.2.7.1.33"),
	//机组状态
	UNITSTATE("1.3.6.1.4.1.34651.2.7.1.34"),
	//动作
	ACTIONINDEX("1.3.6.1.4.1.34651.2.7.1.35"),
	ALARMINDEX("1.3.6.1.4.1.34651.2.7.1.36"),
	//上次获取时间间隔
	LASTCOLLECTTNTERVAL("1.3.6.1.4.1.34651.2.7.1.99");
	private String oid;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	private AcOidInfo(String oid) {
		// TODO Auto-generated constructor stub
		this.oid=oid;
	}
	
}
