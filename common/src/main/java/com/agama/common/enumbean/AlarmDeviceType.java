package com.agama.common.enumbean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum AlarmDeviceType implements JsonSerializable{
	PEDEVICE(0,"动环设备"),
	HOSTDEVICE(1,"主机设备"),
	SWITCHBOARD(2,"交换机");
	private int id;
	private String name;
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
	private AlarmDeviceType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("alarmDeviceType");
		generator.writeString(toString());
		generator.writeFieldName("id");
		generator.writeNumber(id);
		generator.writeFieldName("name");
		generator.writeString(name);
		generator.writeEndObject();
		
	}
	@Override
	public void serializeWithType(JsonGenerator generator, SerializerProvider provider,
			TypeSerializer serializer) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
