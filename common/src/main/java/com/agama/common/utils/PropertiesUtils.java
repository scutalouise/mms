package com.agama.common.utils;



import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class PropertiesUtils {
	
	/**
	 * @Field map: 数据
	 */
	private static Map<Object, Object> map = new HashMap<Object, Object>();
	
	/**
	 * 初始化工具类
	 */
	public void loadProperties(String path){
		Properties properties = new Properties();
		InputStream inStream = PropertiesUtils.class.getResourceAsStream(path);
		try {
			properties.load(inStream);
			for (Object key : properties.keySet()) {
				map.put(key, properties.get(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	
	/**
	 * @Title: get
	 * @Description: 根据键获取值 
	 * @param key
	 * @return Object
	 * @throws 
	 */
	public static Object get(String key) {
		return map.get(key);
	}
	
	/**
	 * @Title: getString
	 * @Description: 根据键获取值,返回String类型
	 * @param key
	 * @return String
	 * @throws 
	 */
	public static String getString(String key) {
		return (String) map.get(key);
	}

}
