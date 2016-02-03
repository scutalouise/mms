package com.agama.common.enumbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

/**
 * @Description:操作系统枚举类
 * @Author:杨远高
 * @Since :2016年1月8日 上午11:46:14
 */
public enum OSEnum implements JsonSerializable{
	LINUX(0, "linux"), WINDOWS(1, "windows"), AIX(2, "aix");

	private int id;
	private String name;

	private OSEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @Description:返回前台操作系统的枚举
	 * @return
	 * @Since :2016年1月8日 上午11:40:51
	 */
	public static List<OSEnum> getOSEnum(){
		List<OSEnum> list = new ArrayList<OSEnum>();
		for(OSEnum os : OSEnum.values()){
			list.add(os);
		}
		return list;
	}
	
	
	@Override
	public void serialize(JsonGenerator generator, SerializerProvider povider) throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("osEnum");
		generator.writeString(toString());
		generator.writeFieldName("id");
		generator.writeNumber(id);
		generator.writeFieldName("name");
		generator.writeString(name);
		generator.writeEndObject();
	}

	@Override
	public void serializeWithType(JsonGenerator arg0, SerializerProvider arg1, TypeSerializer arg2) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}
}
