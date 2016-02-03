package com.agama.common.enumbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum AlarmOptionType implements JsonSerializable{
	UPS("UPS","UPS",AlarmDeviceType.PEDEVICE),
	TH("TH","温湿度",AlarmDeviceType.PEDEVICE),
	WATER("WATER","水浸",AlarmDeviceType.PEDEVICE),
	SMOKE("SMOKE","烟感",AlarmDeviceType.PEDEVICE),
	
	WINDOWS("WINDOWS","WINDOWS",AlarmDeviceType.HOSTDEVICE),
	LINUX("LINUX","LINUX",AlarmDeviceType.HOSTDEVICE),
	AIX("AIX","AIX",AlarmDeviceType.HOSTDEVICE),
	
	SWITCHBOARD_HW("SWITCHBOARD_HW","华为",AlarmDeviceType.SWITCHBOARD),
	SWITCHBOARD_CISCO("SWITCHBOARD_CISCO","思科",AlarmDeviceType.SWITCHBOARD);
	
	private String value;
	private String name;
	private AlarmDeviceType alarmDeviceType;
	
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AlarmDeviceType getAlarmDeviceType() {
		return alarmDeviceType;
	}
	public void setAlarmDeviceType(AlarmDeviceType alarmDeviceType) {
		this.alarmDeviceType = alarmDeviceType;
	}
	private AlarmOptionType(String value, String name, AlarmDeviceType alarmDeviceType) {
		this.value = value;
		this.name = name;
		this.alarmDeviceType = alarmDeviceType;
	}
	
	public static  List<Map<String, Object>> getAlarmOptionTypeListByName(String name){
		List<Map<String, Object>> alarmOptionTypes=new ArrayList<Map<String, Object>>();
		for (AlarmOptionType alarmOptionType : AlarmOptionType.values()) {
			if((alarmOptionType.toString()).contains(name)){
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("value", alarmOptionType.getValue());
				map.put("name", alarmOptionType.getName());
				alarmOptionTypes.add(map);
			}
		}
		return alarmOptionTypes;
	}
	public static List<Map<String, Object>> getAlarmOptionTypeListByAlarmDeviceType(AlarmDeviceType alarmDeviceType){
		List<Map<String, Object>> alarmOptionTypes=new ArrayList<Map<String, Object>>();
		for (AlarmOptionType alarmOptionType : AlarmOptionType.values()) {
			if(alarmOptionType.getAlarmDeviceType()==alarmDeviceType){
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("value", alarmOptionType.getValue());
				map.put("name", alarmOptionType.getName());
				alarmOptionTypes.add(map);
			}
		}
		return alarmOptionTypes;
	}
	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("alarmOptionType");
		generator.writeString(toString());
		generator.writeFieldName("value");
		generator.writeString(value);
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
