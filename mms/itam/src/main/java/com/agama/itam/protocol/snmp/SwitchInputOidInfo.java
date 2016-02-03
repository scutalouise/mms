package com.agama.itam.protocol.snmp;

import org.snmp4j.PDU;

public enum SwitchInputOidInfo {

	NAME("1.3.6.1.4.1.34651.2.3.1.1", PDU.GET), SIGNAL(
			"1.3.6.1.4.1.34651.2.3.1.10", PDU.GET),
	// 上次获取时间间隔
	LASTCOLLECTINTERVAL("1.3.6.1.4.1.34651.2.3.1.99", PDU.GET);

	private String oid;
	private int mode;

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

	private SwitchInputOidInfo(String oid, int mode) {
		this.oid = oid;
		this.mode = mode;
	}

}
