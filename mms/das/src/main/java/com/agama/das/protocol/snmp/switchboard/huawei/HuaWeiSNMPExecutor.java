package com.agama.das.protocol.snmp.switchboard.huawei;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agama.das.model.SNMPTarget;
import com.agama.das.protocol.snmp.SNMPExecutor;
import com.agama.das.protocol.snmp.mib.switchboard.HuaWeiMib;

/**
 * @Description:执行华为交换机相关snmp参数的抓取
 * @Author:佘朝军
 * @Since :2015年12月15日 下午2:21:39
 */
public class HuaWeiSNMPExecutor extends SNMPExecutor {

	public HuaWeiSNMPExecutor(SNMPTarget snmpTarget) throws IOException {
		super(snmpTarget);
	}

	@Override
	public Map<String, Object> getSNMPInfo(SNMPTarget snmpTarget) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("model", getSystemModel());
		map.put("systemPower", getSystemPower());
		map.put("entityInfo", getEntityInfo());
		return map;
	}

	public String getSystemModel() throws IOException {
		return snmpGet(HuaWeiMib.hwEntitySystemModel);
	}

	public Map<String, String> getSystemPower() throws IOException {
		String totalPower = snmpGet(HuaWeiMib.hwSystemPowerTotalPower);
		String usedPower = snmpGet(HuaWeiMib.hwSystemPowerUsedPower);
		return HuaWeiSNMPParser.parseSystemPower(usedPower, totalPower);
	}

	public List<Map<String, Object>> getEntityInfo() throws IOException {
		List<Map<String, Object>> entityList = new ArrayList<Map<String, Object>>();
		List<String> entityCodeList = getEntityCodeList();
		if (!entityCodeList.isEmpty()) {
			Map<String, String> entityDescMap = snmpWalkGroup(HuaWeiMib.hwEntityBomEnDesc);
			Map<String, String> cpuMap = snmpWalkGroup(HuaWeiMib.hwEntityCpuUsage);
			Map<String, String> memoryUsageMap = snmpWalkGroup(HuaWeiMib.hwEntityMemUsage);
			Map<String, String> memorySizeMap = snmpWalkGroup(HuaWeiMib.hwEntityMemSize);
			Map<String, String> temperatureMap = snmpWalkGroup(HuaWeiMib.hwEntityTemperature);
			for (String entityCode : entityCodeList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("entityCode", entityCode);
				map.put("cpuUsage", HuaWeiSNMPParser.parseSingleResultByEntityCode(entityCode, cpuMap) + "%");
				map.put("memoryUsage", HuaWeiSNMPParser.parseSingleResultByEntityCode(entityCode, memoryUsageMap) + "%");
				map.put("memorySize", HuaWeiSNMPParser.parseMemorySize(entityCode, memorySizeMap));
				map.put("description", HuaWeiSNMPParser.parseSingleResultByEntityCode(entityCode, entityDescMap));
				map.put("temperature", HuaWeiSNMPParser.parseSingleResultByEntityCode(entityCode, temperatureMap) + " °C");
				entityList.add(map);
			}
		}
		return entityList;
	}

	public List<String> getEntityCodeList() throws IOException {
		Map<String, String> entityMap = snmpWalkGroup(HuaWeiMib.hwEntityBomId);
		return HuaWeiSNMPParser.parseEntityCodeList(entityMap);
	}

}
