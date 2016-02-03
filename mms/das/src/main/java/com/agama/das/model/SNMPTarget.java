package com.agama.das.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description:用于封装访问SNMP协议的参数
 * @Author:佘朝军
 * @Since :2015年11月2日 下午2:44:13
 */
@Getter
@Setter
public class SNMPTarget {
	private String targetIp;
	private String readCommunity;
	private String writeCommunity;
	private int port = 161;
	private int snmpVersion;
	private String user;
	private String password;
	private String contextEngineId;

}
