package com.agama.common.enumbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum MaintainWayEnum implements JsonSerializable{
	INNER(0,"内部运维"), OUTER(1,"外部运维");
	private int id;
	private String name;
	
	private MaintainWayEnum(int id, String name) {
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
	
	public static List<MaintainWayEnum> getMaintainWay() {
		List<MaintainWayEnum> maintainWayList = new ArrayList<>();
		for (MaintainWayEnum maintainWayType : MaintainWayEnum.values()) {
			maintainWayList.add(maintainWayType);
		}
		return maintainWayList;
	}
	
	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("maintainWay");
		generator.writeString(toString());
		generator.writeFieldName("value");
		generator.writeNumber(id);
		generator.writeFieldName("name");
		generator.writeString(name);
		generator.writeEndObject();

	}

	@Override
	public void serializeWithType(JsonGenerator arg0, SerializerProvider arg1, TypeSerializer arg2)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub

	}
}
