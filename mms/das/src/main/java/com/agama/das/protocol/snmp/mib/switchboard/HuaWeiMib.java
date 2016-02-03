package com.agama.das.protocol.snmp.mib.switchboard;

import org.snmp4j.PDU;

import com.agama.das.protocol.snmp.mib.MibOID;
import com.agama.das.protocol.snmp.mib.SystemMib;

/**
 * @Description:用于配置华为交换机MIB的OID信息
 * @Author:佘朝军
 * @Since :2015年12月14日 下午3:22:44
 */
public enum HuaWeiMib implements MibOID {

	hwEntitySystemModel("1.3.6.1.4.1.2011.5.25.31.6.5.0", PDU.GET), 			// 设备型号

	hwEntityBomId("1.3.6.1.4.1.2011.5.25.31.1.1.2.1.1", PDU.GETBULK), 			// 获取实体标识
	hwEntityBomEnDesc("1.3.6.1.4.1.2011.5.25.31.1.1.2.1.2", PDU.GETBULK),		// 获取实体描述信息

	hwCpuDevDuty("1.3.6.1.4.1.2011.6.3.4.1.2", PDU.GET), 						// CPU在5秒内平均使用率

	hwEntityCpuUsage("1.3.6.1.4.1.2011.5.25.31.1.1.1.1.5", PDU.GETBULK), 		// CPU使用率
	hwEntityMemUsage("1.3.6.1.4.1.2011.5.25.31.1.1.1.1.7", PDU.GETBULK), 		// 内存使用率
	hwEntityMemSize("1.3.6.1.4.1.2011.5.25.31.1.1.1.1.9", PDU.GETBULK), 		// 内存容量（单位：byte）

	hwEntityTemperature("1.3.6.1.4.1.2011.5.25.31.1.1.1.1.11", PDU.GETBULK), 	// 实体温度

	hwSystemPowerTotalPower("1.3.6.1.4.1.2011.5.25.31.1.1.14.1.2.0", PDU.GET), 	// 系统总功率（单位：w）
	hwSystemPowerUsedPower("1.3.6.1.4.1.2011.5.25.31.1.1.14.1.3.0", PDU.GET); 	// 系统已使用功率（单位：w）

	private String oid;

	private int mode;

	private String parent = "1.3.6.1.4.1.2011.5.25.31.1.1";

	private HuaWeiMib(String oid, int mode) {
		this.oid = oid;
		this.mode = mode;
	}

	@Override
	public String getOID() {
		return this.oid;
	}

	@Override
	public int getMode() {
		return this.mode;
	}

	@Override
	public String getParent() {
		return this.parent;
	}

	@Override
	public int size() {
		return SystemMib.values().length;
	}

}
