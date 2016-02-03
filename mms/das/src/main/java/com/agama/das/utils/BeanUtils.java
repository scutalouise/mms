package com.agama.das.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

/**
 * @Description:mongoDB实例对象工具类
 * @Author:佘朝军
 * @Since :2015年11月12日 上午11:39:40
 */
public class BeanUtils {

	public static final String _ID = "_id";

	public static final String _CLASS = "_class";

	public static final String OBJECT_ID = "objectId";

	/**
	 * @Description:根据属性名格式化出方法名
	 * @param methodHeader
	 *            方法头（set, is 或者 get）
	 * @param fieldName
	 *            属性名
	 * @return
	 * @Since :2015年11月27日 下午2:16:51
	 */
	public static String formatMethodNameByFieldName(String methodHeader, String fieldName) {
		byte[] items = fieldName.getBytes();
		items[0] = (byte) ((char) items[0] + 'A' - 'a');
		return methodHeader + new String(items);
	}

	/**
	 * @Description:通过DBObject对象构建一个实体对象
	 * @param e
	 *            实体对象
	 * @param doc
	 *            DBObject对象
	 * @param fields
	 *            属性数组
	 * @return Object实体对象
	 * @throws Exception
	 * @Since :2015年11月27日 下午2:19:09
	 */
	public static Object buildObjectFromDBObject(Object e, DBObject doc, Field[] fields) throws Exception {
		for (Field field : fields) {
			String fieldName = field.getName();
			Object obj = doc.get(fieldName);
			if (obj != null || fieldName.equals("id")) {
				String type = field.getGenericType().toString();
				Method m = (Method) e.getClass().getDeclaredMethod(formatMethodNameByFieldName("set", field.getName()), field.getType());
				if (fieldName.equals("id")) {
					m.invoke(e, (ObjectId) doc.get("_id"));
				} else {
					if (!type.contains("class")) {
						if (type.equals("char")) {
							m.invoke(e, ((String) obj).charAt(0));
						} else if (type.equals("short")) {
							m.invoke(e, (short) ((int) ((Integer) obj)));
						} else if (type.equals("byte")) {
							m.invoke(e, (byte) ((int) ((Integer) obj)));
						} else if (type.equals("float")) {
							m.invoke(e, Float.parseFloat(obj.toString()));
						} else
							m.invoke(e, obj);
					} else {
						m.invoke(e, obj);
					}
				}
			}
		}
		return e;
	}

	/**
	 * @Description:通过实体bean对象构建一个参数更新的散列集合，用于数据库集合的修改操作
	 * @param obj
	 *            实体对象
	 * @param clz
	 *            实体对象类的class
	 * @return Map<String,Object>
	 * @throws Exception
	 * @Since :2015年11月27日 下午2:20:46
	 */
	public static Map<String, Object> buildUpdateMapFromBean(Object obj, Class<?> clz) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			String methodHeader = "";
			if ("boolean".equals(field.getGenericType().toString())) {
				methodHeader = "is";
			} else {
				methodHeader = "get";
			}
			Method m = (Method) clz.getMethod(formatMethodNameByFieldName(methodHeader, field.getName()));
			if (!"id".equals(fieldName)) {
				map.put(fieldName, m.invoke(obj));
			}
		}
		map.put(_CLASS, clz.getName());
		return map;
	}

	/**
	 * @Description:通过实体bean对象构建一个用于进行视图展示或者数据传出的散列集合
	 * @param obj
	 *            实体对象
	 * @param clz
	 *            实体对象类的class
	 * @return Map<String,Object>
	 * @throws Exception
	 * @Since :2015年11月27日 下午2:22:52
	 */
	public static Map<String, Object> buildViewMapFromBean(Object obj, Class<?> clz) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			String methodHeader = "";
			if ("boolean".equals(field.getGenericType().toString())) {
				methodHeader = "is";
			} else {
				methodHeader = "get";
			}
			Method m = (Method) clz.getMethod(formatMethodNameByFieldName(methodHeader, field.getName()));
			if (!"id".equals(fieldName)) {
				map.put(fieldName, m.invoke(obj));
			} else {
				ObjectId oid = (ObjectId) m.invoke(obj);
				map.put("id", oid.toString());
			}
		}
		return map;
	}

}
