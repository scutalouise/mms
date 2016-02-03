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
	UPS, TH, SWITCHINPUT,AC; 

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
		generator.writeEndObject();
	}

	@Override
	public void serializeWithType(JsonGenerator arg0, SerializerProvider arg1, TypeSerializer arg2) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}
}
