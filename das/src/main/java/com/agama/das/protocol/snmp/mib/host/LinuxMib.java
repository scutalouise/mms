package com.agama.das.protocol.snmp.mib.host;

import org.snmp4j.PDU;

import com.agama.das.protocol.snmp.mib.MibOID;
import com.agama.das.protocol.snmp.mib.SystemMib;

/**@Description:LINUX系统MIB库中监控的oid枚举
 * @Author:佘朝军
 * @Since :2015年11月19日 下午2:51:53
 */
public enum LinuxMib implements MibOID{
	
	ssCpuUser("1.3.6.1.4.1.2021.11.9.0",PDU.GET),		// 用户CPU百分比
	ssCpuSystem("1.3.6.1.4.1.2021.11.10.0",PDU.GET), 	// 系统CPU百分比
	ssCpuIdle("1.3.6.1.4.1.2021.11.11.0",PDU.GET),	// 空闲CPU百分比
	
	memTotalReal("1.3.6.1.4.1.2021.4.5.0",PDU.GET),		// Total RAM in machine
	memAvailReal("1.3.6.1.4.1.2021.4.6.0",PDU.GET), 	// Total RAM used
	
	hrStorageDescr("1.3.6.1.2.1.25.2.3.1.3",PDU.GETBULK),	// 存储设备描述
	hrStorageAllocationUnits("1.3.6.1.2.1.25.2.3.1.4",PDU.GETBULK),	// 簇的大小
	hrStorageSize("1.3.6.1.2.1.25.2.3.1.5",PDU.GETBULK),	// 簇的的数目
	hrStorageUsed("1.3.6.1.2.1.25.2.3.1.6",PDU.GETBULK); // 使用多少，跟总容量相除就是占用率
	
	private String oid;

	private int mode;

	private String parent = "1.3.6.1.2.1.25";
	
	private LinuxMib(String oid, int mode) {
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
