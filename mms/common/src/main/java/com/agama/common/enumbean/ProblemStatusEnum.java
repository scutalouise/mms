package com.agama.common.enumbean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

/**
 * @Description:问题状态枚举
 * @Author:佘朝军
 * @Since :2016年1月22日 下午2:20:35
 */
public enum ProblemStatusEnum implements JsonSerializable {
	NEW(0, "新问题"),
	ASSIGNED(1, "已分配"),
	RESPONSED(2, "已响应"),
	HANDLING(3, "处理中"),
	RESOLVED(4, "已解决"),
	CLOSED(5, "已关闭");

	private int value;
	private String text;

	private ProblemStatusEnum(int value, String text) {
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
		generator.writeFieldName("problemStatus");
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
