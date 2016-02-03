package com.agama.das.protocol.snmp.host.windows;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agama.das.model.SNMPTarget;
import com.agama.das.protocol.snmp.SNMPExecutor;
import com.agama.das.protocol.snmp.SNMPUtils;
import com.agama.das.protocol.snmp.mib.host.WindowsMib;

/**
 * @Description:执行windows相关snmp参数的处理
 * @Author:佘朝军
 * @Since :2015年11月2日 下午2:31:31
 */
public class WindowsSNMPExecutor extends SNMPExecutor {

	public WindowsSNMPExecutor(SNMPTarget snmpTarget) throws IOException {
		super(snmpTarget);
	}

	public Map<String, Object> getSNMPInfo(SNMPTarget snmpTarget) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, List<String>> damMap = getDiskAndMemoryList();
		map.put("cpu", getCPUStatus());
		map.put("memory", getMemoryStatus(damMap));
		map.put("disk", getDiskStatus(damMap));
		return map;
	}

	public String getCPUStatus() throws IOException {
		Map<String, String> cpuMap = snmpWalkGroup(WindowsMib.hrProcessorLoad);
		return WindowsSNMPParser.parseCPUStatus(cpuMap);
	}

	public Map<String, Object> getMemoryStatus(Map<String, List<String>> damMap) {
		return WindowsSNMPParser.parseMemoryStatus(damMap.get("nameList"), damMap.get("unitsList"), damMap.get("totalSizeList"), damMap.get("usedList"));
	}

	public List<Object> getDiskStatus(Map<String, List<String>> damMap) {
		return WindowsSNMPParser.parseDiskStatus(damMap.get("nameList"), damMap.get("unitsList"), damMap.get("totalSizeList"), damMap.get("usedList"));
	}

	private Map<String, List<String>> getDiskAndMemoryList() throws IOException {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("nameList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(WindowsMib.hrStorageDescr)));
		map.put("unitsList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(WindowsMib.hrStorageAllocationUnits)));
		map.put("totalSizeList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(WindowsMib.hrStorageSize)));
		map.put("usedList", SNMPUtils.buildListByLinkedMap(snmpWalkGroup(WindowsMib.hrStorageUsed)));
		return map;
	}

}
