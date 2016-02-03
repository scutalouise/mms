package com.agama.das.protocol.snmp.host.windows;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agama.das.protocol.snmp.SNMPUtils;

/**
 * @Description:提供windows系统SNMP监控数据解析方法
 * @Author:佘朝军
 * @Since :2015年11月20日 上午9:40:57
 */
public class WindowsSNMPParser {

	public static String parseCPUStatus(Map<String, String> cpuMap) {
		float sum = 0;
		for (Map.Entry<String, String> map : cpuMap.entrySet()) {
			sum = sum + Integer.parseInt(map.getValue());
		}
		String cpuUsage = "";
		if (cpuMap.size() > 0) {
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			cpuUsage = decimalFormat.format((float) (sum / cpuMap.size())) + "%";
		} else {
			throw new RuntimeException("未获取到cpu信息！");
		}
		return cpuUsage;
	}

	public static Map<String, Object> parseMemoryStatus(List<String> nameList, List<String> unitsList, List<String> totalSizeList, List<String> usedList) {
		Map<String, Object> memoryMap = new HashMap<String, Object>();
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		for (int i = 0; i < nameList.size(); i++) {
			if (nameList.get(i).contains("Physical Memory")) {
				Float total = (Float.parseFloat(unitsList.get(i)) * Float.parseFloat(totalSizeList.get(i)));
				Float used = (Float.parseFloat(unitsList.get(i)) * Float.parseFloat(usedList.get(i)));
				Float usageRate = used / total * 100;
				memoryMap.put("total", SNMPUtils.generateValueStringForUnit(total));
				memoryMap.put("used", SNMPUtils.generateValueStringForUnit(used));
				memoryMap.put("usageRate", decimalFormat.format(usageRate) + "%");
				break;
			}
		}
		return memoryMap;
	}

	public static List<Object> parseDiskStatus(List<String> nameList, List<String> unitsList, List<String> totalSizeList, List<String> usedList) {
		List<Object> diskList = new ArrayList<Object>();
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		for (int i = 0; i < nameList.size(); i++) {
			String readName = nameList.get(i);
			String diskName = ((readName.split(":").length > 2) ? SNMPUtils.getChinese(readName) : readName);
			if (diskName.contains("Label")) {
				// 总磁盘大小
				Float total = Float.parseFloat(unitsList.get(i)) * Float.parseFloat(totalSizeList.get(i));
				if (total > 0) {
					int index = diskName.indexOf(":");
					diskName = index == -1 ? diskName : diskName.substring(0, diskName.indexOf(":") + 1);
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
