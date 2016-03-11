package com.agama.common.enumbean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

/**
 * @Description:数量单位枚举
 * @Author:ranjunfeng
 * @Since :2016年2月29日 上午10:27:47
 */
public enum CountUnitEnum implements JsonSerializable{

	NUMBER(""), PERCENTAGE("%");
	private String value;

	private CountUnitEnum(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
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
		generator.writeFieldName("countUnit");
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
