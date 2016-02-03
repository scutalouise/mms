package com.agama.das.protocol.snmp.router;

import java.io.IOException;
import java.util.Map;

import com.agama.das.model.SNMPTarget;
import com.agama.das.protocol.snmp.SNMPExecutor;

/**@Description:执行路由器相关snmp参数的处理
 * @Author:佘朝军
 * @Since :2015年12月10日 上午11:31:42
 */
public class RouterSNMPExecutor extends SNMPExecutor{

	public RouterSNMPExecutor(SNMPTarget snmpTarget) throws IOException {
		super(snmpTarget);
	}

	@Override
	public Map<String, Object> getSNMPInfo(SNMPTarget snmpTarget) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
