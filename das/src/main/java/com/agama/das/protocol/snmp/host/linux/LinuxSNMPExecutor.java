package com.agama.das.protocol.snmp.host.linux;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agama.das.model.SNMPTarget;
import com.agama.das.protocol.snmp.SNMPExecutor;
import com.agama.das.protocol.snmp.SNMPUtils;
import com.agama.das.protocol.snmp.mib.host.LinuxMib;

/**
 * @Description:执行linux相关snmp参数的处理
 * @Author:佘朝军
 * @Since :2015年11月2日 下午2:40:21
 */
public class LinuxSNMPExecutor extends SNMPExecutor {

	public LinuxSNMPExecutor(SNMPTarget snmpTarget) throws IOException {
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
		 Integer cpuFree = Integer.parseInt(snmpGet(LinuxMib.ssCpuIdle));
		 return LinuxSNMPParser.parseCPUStatus(cpuFree);
	}

	public Map<String, Object> getMemoryStatus() throws IOException {
		String availableStr = snmpGet(LinuxMib.memAvailReal);
		String totalStr = snmpGet(LinuxMib.memTotalReal);
		return LinuxSNMPParser.parseMemoryStatus(availableStr, totalStr);
	}

	public List<Object> getDiskStatus() throws IOException {
		Map<String, List<String>> diskMap = getDiskList();
		return LinuxSNMPParser.parseDiskStatus(diskMap.get("nameList"), diskMap.get("unitsList"), diskMap.get("totalSizeList"), diskMap.get("usedList"));
	}

	private Map<String, List<String>> getDiskList() throws IOException {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("nameList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(LinuxMib.hrStorageDescr)));
		map.put("unitsList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(LinuxMib.hrStorageAllocationUnits)));
		map.put("totalSizeList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(LinuxMib.hrStorageSize)));
		map.put("usedList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(LinuxMib.hrStorageUsed)));
		return map;
	}

}
