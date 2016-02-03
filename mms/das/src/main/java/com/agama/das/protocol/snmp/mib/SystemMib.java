package com.agama.das.protocol.snmp.mib;

import org.snmp4j.PDU;

/**@Description:
 * @Author:佘朝军
 * @Since :2015年11月19日 下午6:34:09
 */
public enum SystemMib implements MibOID{
	sysDescr("1.3.6.1.2.1.1.1",PDU.GETNEXT),
	sysObjectID("1.3.6.1.2.1.1.2",PDU.GETNEXT),
	sysUpTime("1.3.6.1.2.1.1.3",PDU.GETNEXT),
	sysContact("1.3.6.1.2.1.1.4",PDU.GETNEXT),
	sysName("1.3.6.1.2.1.1.5",PDU.GETNEXT);

	private String oid;

	private int mode;

	private String parent = "1.3.6.1.2.1";

	private SystemMib(String oid, int mode) {
		this.oid = oid;
		this.mode = mode;
	}

	public int getMode() {
		return this.mode;
	}

	@Override
	public String getOID() {
		return this.oid;
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
