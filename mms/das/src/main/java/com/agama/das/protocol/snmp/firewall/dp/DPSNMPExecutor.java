package com.agama.das.protocol.snmp.firewall.dp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agama.das.model.SNMPTarget;
import com.agama.das.protocol.snmp.SNMPExecutor;
import com.agama.das.protocol.snmp.SNMPUtils;
import com.agama.das.protocol.snmp.mib.firewall.DPMib;

/**@Description:执行迪普防火墙相关snmp参数的抓取
 * @Author:佘朝军
 * @Since :2015年12月31日 下午4:49:13
 */
public class DPSNMPExecutor extends SNMPExecutor{

	public DPSNMPExecutor(SNMPTarget snmpTarget) throws IOException {
		super(snmpTarget);
	}

	@Override
	public Map<String, Object> getSNMPInfo(SNMPTarget snmpTarget) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cpu", getCPUStatus());
		map.put("memory", getMemoryStatus());
		map.put("disk", getDiskStatus());
		return map;
	}
	
	public String getCPUStatus() throws IOException {
		 Integer cpuFree = Integer.parseInt(snmpGet(DPMib.ssCpuIdle));
		 return DPSNMPParser.parseCPUStatus(cpuFree);
	}

	public Map<String, Object> getMemoryStatus() throws IOException {
		String availableStr = snmpGet(DPMib.memAvailReal);
		String totalStr = snmpGet(DPMib.memTotalReal);
		return DPSNMPParser.parseMemoryStatus(availableStr, totalStr);
	}

	public List<Object> getDiskStatus() throws IOException {
		Map<String, List<String>> diskMap = getDiskList();
		return DPSNMPParser.parseDiskStatus(diskMap.get("nameList"), diskMap.get("unitsList"), diskMap.get("totalSizeList"), diskMap.get("usedList"));
	}
	
	private Map<String, List<String>> getDiskList() throws IOException {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("nameList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(DPMib.hrStorageDescr)));
		map.put("unitsList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(DPMib.hrStorageAllocationUnits)));
		map.put("totalSizeList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(DPMib.hrStorageSize)));
		map.put("usedList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(DPMib.hrStorageUsed)));
		return map;
	}

}
