package com.agama.das.protocol.snmp.mib.switchboard;

import org.snmp4j.PDU;

import com.agama.das.protocol.snmp.mib.MibOID;
import com.agama.das.protocol.snmp.mib.SystemMib;

/**@Description:
 * @Author:佘朝军
 * @Since :2015年12月16日 下午4:25:25
 */
public enum MaipuMib implements MibOID{
	numBytesFree("1.3.6.1.4.1.5651.3.20.1.1.1.1.0", PDU.GET),			// 空闲内存
	memoryTotalBytes("1.3.6.1.4.1.5651.3.20.1.1.1.8.0", PDU.GET), 		// 内存总量	
	memoryUsage("1.3.6.1.4.1.5651.3.600.10.1.1.10", PDU.GET),		// 内存使用率
	
	cpuUsage("1.3.6.1.4.1.5651.3.600.9.1.1.3", PDU.GET),			// CPU使用率
	
	temperature("1.3.6.1.4.1.5651.3.600.9.2.1.2", PDU.GET);			// 设备温度

	private String oid;

	private int mode;

	private String parent = "1.3.6.1.4.1.5651.3";

	private MaipuMib(String oid, int mode) {
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
