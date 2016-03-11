package com.agama.common.enumbean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum DeviceAlarmOptionEnum implements JsonSerializable{
	INVENTORYNUM("库存数量"),MAINTENANCETIME("维修时间"),SCRAPPEDTIME("报废时间");
	private String value;
	
	private DeviceAlarmOptionEnum(String value) {
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
		generator.writeFieldName("deviceAlarmOption");
		generator.writeString(toString());
	
		generator.writeFieldName("value");
		generator.writeString(value);
		generator.writeEndObject();
		
	}

	@Override
	public void serializeWithType(JsonGenerator generator, SerializerProvider provider,
			TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}
	
	
}
