package com.agama.das.protocol.snmp.host.linux;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agama.das.protocol.snmp.SNMPUtils;

/**
 * @Description:提供linux系统SNMP监控数据解析方法
 * @Author:佘朝军
 * @Since :2015年11月24日 上午10:41:59
 */
public class LinuxSNMPParser {
	
	public static String parseCPUStatus(int cpuFree){
		int cpuUsed = 100 - cpuFree;
		return cpuUsed + "%";
	}

	public static Map<String, Object> parseMemoryStatus(String availableStr, String totalStr) {
		Map<String, Object> memoryMap = new HashMap<String, Object>();
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		Float total = Float.parseFloat(totalStr);
		Float used = total - Float.parseFloat(availableStr);
		float usageRate = used / total * 100;
		memoryMap.put("total", SNMPUtils.generateValueStringForUnit(total * 1024));
		memoryMap.put("used", SNMPUtils.generateValueStringForUnit(used * 1024));
		memoryMap.put("usageRate", decimalFormat.format(usageRate) + "%");
		return memoryMap;
	}

	public static List<Object> parseDiskStatus(List<String> nameList, List<String> unitsList, List<String> totalSizeList, List<String> usedList) {
		List<Object> diskList = new ArrayList<Object>();
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		for (int i = 0; i < nameList.size(); i++) {
			String readName = nameList.get(i);
			String diskName = ((readName.split(":").length > 2) ? SNMPUtils.getChinese(readName) : readName);
			if (diskName.indexOf("/") == 0) {
				// 总磁盘大小
				Float total = Float.parseFloat(unitsList.get(i)) * Float.parseFloat(totalSizeList.get(i));
				if (total > 0) {
					Map<String, String> map = new HashMap<String, String>();
					Float used = Float.parseFloat(unitsList.get(i)) * Float.parseFloat(usedList.get(i));
					Float usage = used / total * 100;
					map.put("name", diskName);
					map.put("usageRate", decimalFormat.format(usage) + "%");
					map.put("total", SNMPUtils.generateValueStringForUnit(total));
					map.put("used", SNMPUtils.generateValueStringForUnit(used));
					diskList.add(map);
				}
			}
		}
		return diskList;
	}

}
