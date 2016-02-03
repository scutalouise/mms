package com.agama.das.protocol.snmp.mib;

import org.snmp4j.PDU;

/**@Description:提供大部分设备共有MIB库节点信息
 * @Author:佘朝军
 * @Since :2016年1月5日 下午4:25:32
 */
public enum PublicMib implements MibOID{
	
	//网络接口
	ifDescr("1.3.6.1.2.1.2.2.1.2", PDU.GETBULK),						// 接口描述
	ifType("1.3.6.1.2.1.2.2.1.3", PDU.GETBULK),							// 接口类型
	ifMtu("1.3.6.1.2.1.2.2.1.4", PDU.GETBULK),							// 接口最大传输单元
	ifSpeed("1.3.6.1.2.1.2.2.1.5", PDU.GETBULK),						// 接口当前宽带速率（bit/s）
	ifOperStatus("1.3.6.1.2.1.2.2.1.8", PDU.GETBULK),					// 接口当前状态
	ifInOctets("1.3.6.1.2.1.2.2.1.10", PDU.GETBULK),					// 接口接收流量
	ifOutOctets("1.3.6.1.2.1.2.2.1.16", PDU.GETBULK),					// 接口发送流量
	ifInDiscards("1.3.6.1.2.1.2.2.1.13", PDU.GETBULK),					// 接口入方向丢失报文
	ifOutDiscards("1.3.6.1.2.1.2.2.1.19", PDU.GETBULK),					// 接口出方向丢失报文
	;

	private String oid;

	private int mode;

	private String parent = "1.3.6.1.2.1";
	
	private PublicMib(String oid, int mode) {
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
		return PublicMib.values().length;
	}

}
