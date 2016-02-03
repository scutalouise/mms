package com.agama.das.protocol.snmp.switchboard.maipu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.agama.das.model.SNMPTarget;
import com.agama.das.protocol.snmp.SNMPExecutor;
import com.agama.das.protocol.snmp.mib.SystemMib;
import com.agama.das.protocol.snmp.mib.switchboard.MaipuMib;

/**@Description:执行迈普交换机相关snmp参数的抓取
 * @Author:佘朝军
 * @Since :2015年12月28日 下午4:39:49
 */
public class MaipuSNMPExecutor extends SNMPExecutor {

	public MaipuSNMPExecutor(SNMPTarget snmpTarget) throws IOException {
		super(snmpTarget);
	}

	@Override
	public Map<String, Object> getSNMPInfo(SNMPTarget snmpTarget) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("systemName", snmpGetNext(SystemMib.sysName));
		map.put("description", snmpGetNext(SystemMib.sysDescr));
		map.put("temperature", snmpGetNext(MaipuMib.temperature) + " °C");
		map.put("cpu", snmpGetNext(MaipuMib.cpuUsage) + "%");
		map.put("memory", getMemoryStatus());
		return map;
	}
	
	public Map<String,String> getMemoryStatus() throws IOException{
		String total = snmpGet(MaipuMib.memoryTotalBytes);
		String free = snmpGet(MaipuMib.numBytesFree);
		String usage = snmpGetNext(MaipuMib.memoryUsage);
		return MaipuSNMPParser.parseMemoryStatus(total, free, usage);
	}

}
