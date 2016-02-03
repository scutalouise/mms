package com.agama.common.enumbean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

/**
 * @Description:问题上报途径枚举
 * @Author:佘朝军
 * @Since :2016年1月22日 下午2:41:33
 */
public enum ReportWayEnum implements JsonSerializable {
	PHONE(1, "电话"),
	DICTATION(2, "口头报备"), 
	KNOWLEDGE_BASE(3, "知识库新增"), 
	HANDSET(4, "手持机");

	private int value;
	private String text;

	private ReportWayEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("reportWay");
		generator.writeString(toString());
		generator.writeFieldName("value");
		generator.writeNumber(value);
		generator.writeFieldName("name");
		generator.writeString(text);
		generator.writeEndObject();

	}

	@Override
	public void serializeWithType(JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub

	}

}
