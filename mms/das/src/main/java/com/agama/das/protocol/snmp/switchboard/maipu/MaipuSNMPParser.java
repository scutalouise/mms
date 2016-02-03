package com.agama.das.protocol.snmp.switchboard.maipu;

import java.util.HashMap;
import java.util.Map;

import com.agama.das.protocol.snmp.SNMPUtils;

/**@Description:提供迈普交换机SNMP数据解析的方法
 * @Author:佘朝军
 * @Since :2015年12月28日 下午4:44:03
 */
public class MaipuSNMPParser {
	
	public static Map<String,String> parseMemoryStatus(String total, String free, String usage) {
		Map<String,String> map = new HashMap<String, String>();
		Float totalFloat = Float.parseFloat(total);
		Float used = totalFloat - Float.parseFloat(free);
		map.put("total", SNMPUtils.generateValueStringForUnit(totalFloat));
		map.put("used", SNMPUtils.generateValueStringForUnit(used));
		map.put("usageRate", usage + "%");
		return map;
		
	}

}
