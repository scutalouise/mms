package com.agama.common.enumbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum DeviceInterfaceType implements JsonSerializable{
	UPS("UPS",DeviceType.UPS),
	TH("温湿度",DeviceType.TH),
	WATER("水浸",DeviceType.SWITCHINPUT),
	SMOKE("烟感",DeviceType.SWITCHINPUT),
	AC("空调",DeviceType.AC);
	private String name;
	private DeviceType deviceType;
	private DeviceInterfaceType(String name,DeviceType deviceType){
		this.name=name;
		this.deviceType=deviceType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	public static List<DeviceInterfaceType> getInterfaceTypeByDeviceType(DeviceType deviceType){
		List<DeviceInterfaceType> deviceInterfaceTypeList=new ArrayList<DeviceInterfaceType>();
		for (DeviceInterfaceType deviceInterfaceType : DeviceInterfaceType.values()) {
			if(deviceInterfaceType.getDeviceType()==deviceType){
				deviceInterfaceTypeList.add(deviceInterfaceType);
			}
		}
		return deviceInterfaceTypeList;
	}
	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("deviceInterfaceType");
		generator.writeString(toString());
		generator.writeFieldName("deviceType");
		generator.writeString(deviceType.toString());
		generator.writeFieldName("name");
		generator.writeString(name);
		generator.writeEndObject();
	}
	@Override
	public void serializeWithType(JsonGenerator arg0, SerializerProvider arg1, TypeSerializer arg2) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}
	

}
