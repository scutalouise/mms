package com.agama.tool.utils;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
	
	/**
	 * @Description:根据文件路径获取配置文件对象
	 * @param filePath
	 * @return properties
	 * @throws Exception
	 * @Author:佘朝军
	 * @Since :2015年11月13日 下午4:28:06
	 */
	public static Properties getProperties(String filePath) throws Exception {
		Properties prop = new Properties();
		String path = PropertiesUtils.class.getResource(filePath).getPath()
				.replaceAll("%20", " ");

		FileInputStream fis = new FileInputStream(new File(path));
		prop.load(fis);

		return prop;
	}

	/**
	 * @Description:根据键获取指定文件的值
	 * @param key
	 * @param filePath
	 * @return string
	 * @Author:佘朝军
	 * @Since :2015年11月13日 下午4:31:25
	 */
	public static String getPropertiesValue(String key, String filePath) {
		String value = "";
		try {
			value = getProperties(filePath).getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 增加或者修改
	 * 
	 * @param key
	 * @param value
	 * @param filePath
	 * @throws Exception
	 * @Author:佘朝军
	 */
	public static void saveOrUpdateProperties(String key, String value,
			String filePath) throws Exception {

		Properties prop = getProperties(filePath);
		prop.setProperty(key, value);
		String path = PropertiesUtils.class.getResource(filePath).getPath()
				.replaceAll("%20", " ");
		FileOutputStream outputFile = new FileOutputStream(path);
		prop.store(outputFile, "modify");
		outputFile.close();
		outputFile.flush();

	}

	/**
	 * 移除指定key的值
	 * 
	 * @param key
	 * @param filePath
	 * @throws Exception
	 * @Author:佘朝军
	 */
	public static void remove(String key, String filePath) throws Exception {

		Properties prop = getProperties(filePath);
		prop.remove(key);
		String path = PropertiesUtils.class.getResource(filePath).getPath()
				.replaceAll("%20", " ");
		FileOutputStream outputFile = new FileOutputStream(path);
		prop.store(outputFile, "modify");
		outputFile.close();
		outputFile.flush();

	}

	/**
	 * 获取全部
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 * @Author:佘朝军
	 */
	public static Map<String, String> getAll(String filePath) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Iterator<Entry<Object, Object>> itr = getProperties(filePath)
				.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Object, Object> e = (Entry<Object, Object>) itr.next();
			map.put(e.getKey().toString(), e.getValue().toString());
		}
		return map;
	}

}
