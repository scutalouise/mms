package com.agama.das.protocol.snmp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:工具类，提供SNMP操作的工具方法
 * @Author:佘朝军
 * @Since :2015年11月24日 上午11:27:38
 */
public class SNMPUtils {

	/**
	 * @Description:通过遍历LinkedHashMap的值而组成新的ArrayList
	 * @param linkedHashMap
	 * @return ArrayList
	 * @Since :2015年11月20日 下午2:31:20
	 */
	public static List<String> buildListByLinkedMap(Map<String, String> linkedHashMap) {
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> obj : linkedHashMap.entrySet()) {
			list.add(obj.getValue());
		}
		return list;
	}

	/**
	 * @Description:将带有中文的磁盘符进行编码转换
	 * @param octetString
	 * @return
	 * @Since :2015年11月20日 下午4:47:44
	 */
	public static String getChinese(String octetString) {
		try {
			String[] temps = octetString.split(":");
			byte[] bs = new byte[temps.length];
			for (int i = 0; i < temps.length; i++)
				bs[i] = (byte) Integer.parseInt(temps[i], 16);
			return new String(bs, "GB2312");
		} catch (Exception e) {
			return octetString;
		}
	}

	/**
	 * @Description:以byte为最小迭代单位，对浮点型数据进行最大单位格式的转换，并输出字符串
	 * @param floatInBytes
	 * @return
	 * @Since :2015年11月24日 上午11:37:52
	 */
	public static String generateValueStringForUnit(float floatInBytes) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		if (floatInBytes >= 1024) {
			float floatInKBytes = floatInBytes / 1024;
			if (floatInKBytes > 1024) {
				float floatInMBytes = floatInKBytes / 1024;
				if (floatInMBytes > 1024) {
					float floatInGBytes = floatInMBytes / 1024;
					if (floatInGBytes > 1024) {
						float floatInTBytes = floatInGBytes / 1024;
						return decimalFormat.format(floatInTBytes) + " TB";
					} else {
						return decimalFormat.format(floatInGBytes) + " GB";
					}
				} else {
					return decimalFormat.format(floatInMBytes) + " MB";
				}
			} else {
				return decimalFormat.format(floatInKBytes) + " KB";
			}
		} else {
			return decimalFormat.format(floatInBytes) + " B";
		}
	}

}
