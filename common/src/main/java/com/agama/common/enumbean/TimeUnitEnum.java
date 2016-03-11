package com.agama.common.enumbean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum TimeUnitEnum implements JsonSerializable{
	YEAR("年"),MONTH("月"),DAY("日"),HOURS("小时");
	private String value;
	private TimeUnitEnum(String value) {
		// TODO Auto-generated constructor stub
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("timeUnit");
		generator.writeString(toString());
		generator.writeFieldName("value");
		generator.writeString(value);
		generator.writeEndObject();

	}
	@Override
	public void serializeWithType(JsonGenerator arg0, SerializerProvider arg1,
			TypeSerializer arg2) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}
	
	

}
