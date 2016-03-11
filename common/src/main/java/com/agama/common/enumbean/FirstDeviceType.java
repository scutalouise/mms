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
 * @Description:一级设备类型枚举类
 * @Author:ranjunfeng
 * @Since :2015年12月23日 上午9:47:22
 */
public enum FirstDeviceType implements JsonSerializable {
	COLLECTDEVICE("01", "采集设备"), 
	HOSTDEVICE("02", "主机设备"), 
	NETWORKDEVICE("03", "网络设备"), 
	PEDEVICE("04","动环设备"),
	UNINTELLIGENTDEVICE("05", "非智能设备");

	private String value;
	private String name;

	public String getValue() {
		return value;
	}

	public void setId(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	private FirstDeviceType(String value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public static FirstDeviceType getEnumByValue(String value) {
		for (FirstDeviceType fdt : FirstDeviceType.values()) {
			if (fdt.value.equals(value)) {
				return fdt;
			}
		}
		return UNINTELLIGENTDEVICE;
	}

	public static List<FirstDeviceType> getFirstDeviceType() {
		List<FirstDeviceType> firstDeviceTypeList = new ArrayList<>();
		for (FirstDeviceType firstDeviceType : FirstDeviceType.values()) {
			firstDeviceTypeList.add(firstDeviceType);
		}
		return firstDeviceTypeList;
	}

	
	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("firstDeviceType");
		generator.writeString(toString());
		generator.writeFieldName("value");
		generator.writeString(value);
		generator.writeFieldName("name");
		generator.writeString(name);
		generator.writeEndObject();

	}

	@Override
	public void serializeWithType(JsonGenerator arg0, SerializerProvider arg1, TypeSerializer arg2)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub

	}
}
