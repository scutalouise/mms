package com.agama.das.protocol.snmp.switchboard.huawei;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.agama.das.protocol.snmp.SNMPUtils;

/**
 * @Description:提供华为交换机SNMP数据解析的方法
 * @Author:佘朝军
 * @Since :2015年12月15日 下午2:24:44
 */
public class HuaWeiSNMPParser {

	public static Map<String, String> parseSystemPower(String usedPower, String totalPower) {
		Map<String, String> map = new HashMap<String, String>();
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		Float total = Float.parseFloat(totalPower);
		Float used = Float.parseFloat(usedPower);
		Float usageRate = used / total * 100;
		map.put("used", usedPower + " W");
		map.put("total", totalPower + " W");
		map.put("usageRate", decimalFormat.format(usageRate) + "%");
		return map;
	}

	public static List<String> parseEntityCodeList(Map<String, String> map) {
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> obj : map.entrySet()) {
			if (!StringUtils.isBlank(obj.getValue())) {
				String entityCode = subSnmpKeyForEntityCode(obj.getKey());
				list.add(entityCode);
			}
		}
		return list;
	}
	
	public static String parseMemorySize(String entityCode, Map<String,String> memorySizeMap) {
		String memorySizeStr = parseSingleResultByEntityCode(entityCode, memorySizeMap);
		Float momorySize = Float.parseFloat(memorySizeStr);
		return SNMPUtils.generateValueStringForUnit(momorySize);
	}
	
	public static String parseSingleResultByEntityCode(String entityCode, Map<String,String> map) {
		String result = "";
		for (Map.Entry<String, String> obj : map.entrySet()) {
			if (entityCode.equals(subSnmpKeyForEntityCode(obj.getKey()))) {
				result = obj.getValue();
				break;
			}
		}
		return result;
	}

	private static String subSnmpKeyForEntityCode(String keyStr) {
		return keyStr.substring(keyStr.lastIndexOf(".") + 1);
	}

}
