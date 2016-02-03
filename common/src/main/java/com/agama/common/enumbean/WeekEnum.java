package com.agama.common.enumbean;

import java.io.IOException;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

/**
 * @Description:周枚举
 * @Author:佘朝军
 * @Since :2016年2月2日 上午11:40:35
 */
public enum WeekEnum implements JsonSerializable {
	MONDAY(Calendar.MONDAY, "周一"),
	TUESDAY(Calendar.TUESDAY, "周二"),
	WEDNESDAY(Calendar.WEDNESDAY, "周三"),
	THURSDAY(Calendar.THURSDAY, "周四"),
	FRIDAY(Calendar.FRIDAY, "周五"),
	SATURDAY(Calendar.SATURDAY, "周六"),
	SUNDAY(Calendar.SUNDAY, "周日");

	private int value;
	private String text;

	private WeekEnum(int value, String text) {
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
	
	public static String getTextByValue(int value) {
		String s = "";
		for (WeekEnum day : WeekEnum.values()) {
			if (day.getValue() == value) {
				s = day.getText();
				break;
			}
		}
		return s;
	}
	
	@Override
	public void serialize(JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeFieldName("week");
		jgen.writeString(toString());
		jgen.writeFieldName("value");
		jgen.writeNumber(value);
		jgen.writeFieldName("name");
		jgen.writeString(text);
		jgen.writeEndObject();
		
	}

	@Override
	public void serializeWithType(JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}

}
