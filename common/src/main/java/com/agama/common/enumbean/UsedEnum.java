package com.agama.common.enumbean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum UsedEnum implements JsonSerializable {
	USED(0, "已领用"),AUDIT(1,"审核中"),UNUSED(2, "未领用"), BACK(3, "退回");
	private int id;
	private String name;

	private UsedEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

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

	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("usedEnum");
		generator.writeString(toString());
		generator.writeFieldName("id");
		generator.writeNumber(id);
		generator.writeFieldName("name");
		generator.writeString(name);
		generator.writeEndObject();

	}

	@Override
	public void serializeWithType(JsonGenerator generator, SerializerProvider provider, TypeSerializer serializer)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub

	}

}
