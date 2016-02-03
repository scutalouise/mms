package com.agama.das.protocol.snmp.mib.host;

import org.snmp4j.PDU;

import com.agama.das.protocol.snmp.mib.MibOID;
import com.agama.das.protocol.snmp.mib.SystemMib;

/**@Description:WINDOWS系统MIB库中监控的oid枚举
 * @Author:佘朝军
 * @Since :2015年11月19日 下午6:05:52
 */
public enum WindowsMib implements MibOID{
	
	hrProcessorLoad("1.3.6.1.2.1.25.3.3.1.2",PDU.GETBULK),	//CPU负载
	
	hrStorageDescr("1.3.6.1.2.1.25.2.3.1.3",PDU.GETBULK),	// 存储设备描述
	hrStorageAllocationUnits("1.3.6.1.2.1.25.2.3.1.4",PDU.GETBULK),	// 簇的大小
	hrStorageSize("1.3.6.1.2.1.25.2.3.1.5",PDU.GETBULK),	// 簇的的数目
	hrStorageUsed("1.3.6.1.2.1.25.2.3.1.6",PDU.GETBULK); // 使用多少，跟总容量相除就是占用率
	

	private String oid;

	private int mode;

	private String parent = "1.3.6.1.2.1.25";
	
	private WindowsMib(String oid, int mode) {
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
