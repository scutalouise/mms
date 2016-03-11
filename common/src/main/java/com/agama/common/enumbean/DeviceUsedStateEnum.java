package com.agama.common.enumbean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum DeviceUsedStateEnum implements JsonSerializable{
	PUTINSTORAGE("入库"),AUDIT("待审核"),TURNOVER("周转"),USED("使用"),WAITREPAIR("待修"),BADPARTS("坏件"),WAITEXTERNALMAINTENANCE("待外修"),EXTERNALMAINTENANCE("外修"),SCRAP("报废");
	private String value;
	private DeviceUsedStateEnum(String value) {
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("deviceUsedState");
		generator.writeString(toString());
		generator.writeFieldName("value");
		generator.writeString(value);
		generator.writeEndObject();
		
	}
	@Override
	public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider provider,
			TypeSerializer serializer) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
