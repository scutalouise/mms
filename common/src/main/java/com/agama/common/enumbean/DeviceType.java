package com.agama.common.enumbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum DeviceType implements JsonSerializable{
	UPS("UPS"), TH("温湿度"), SWITCHINPUT("开关输出量"),AC("空调"); 
	private String value;

	private DeviceType(String value) {
		// TODO Auto-generated constructor stub
		this.value=value;
	}
	
	
	
	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public static List<DeviceType> geDeviceType() {
		List<DeviceType> deviceTypeList = new ArrayList<>();
		for (DeviceType deviceType : DeviceType.values()) {
			deviceTypeList.add(deviceType);
		}
		return deviceTypeList;
	}

	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("deviceType");
		generator.writeString(toString());
		generator.writeFieldName("value");
		generator.writeString(value);
		generator.writeEndObject();
	}

	@Override
	public void serializeWithType(JsonGenerator arg0, SerializerProvider arg1, TypeSerializer arg2) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}
}
