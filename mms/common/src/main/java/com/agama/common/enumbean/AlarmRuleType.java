package com.agama.common.enumbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

/**
 * @Description:告警规则类型
 * @Author:ranjunfeng
 * @Since :2016年1月4日 下午5:00:21
 */
public enum AlarmRuleType implements JsonSerializable{
	COMMUNICATIONSTATUS("通讯状态", AlarmOptionType.UPS),
	BYPASSVOLTAGE("UPS旁路电压",AlarmOptionType.UPS),
	INPUTVOLTAGE("输入电压",AlarmOptionType.UPS),
	OUTPUTVOLTAGE("输出电压",AlarmOptionType.UPS),
	INTERNALTEMPERATURE("机内温度",AlarmOptionType.UPS),
	BATTERYVOLTAGE("电池电压",AlarmOptionType.UPS),
	ELECTRICQUANTITY("电池电量",AlarmOptionType.UPS),
	LOAD("负载",AlarmOptionType.UPS),
	
	TEMPERATURE("温度",
			AlarmOptionType.TH),
	HUMIDITY("湿度",AlarmOptionType.TH),
	WATERIMMERSIONSTATE("水浸状态",AlarmOptionType.WATER),
	SMOKESTATE("烟感状态",AlarmOptionType.SMOKE),
	
	
	CPU("CPU",AlarmOptionType.WINDOWS),
	MEMORY("内存",AlarmOptionType.WINDOWS),
	DISK("硬盘",AlarmOptionType.WINDOWS),
	
	SWITCHBOARD_HW_CPU("CPU",AlarmOptionType.SWITCHBOARD_HW),
	SWITCHBOARD_HW_TEMPERATURE("温度",AlarmOptionType.SWITCHBOARD_HW),
	SWITCHBOARD_HW_MEMORY("内存",AlarmOptionType.SWITCHBOARD_HW);
	private String name;
	private AlarmOptionType alarmOptionType;
	private AlarmRuleType(String name,AlarmOptionType alarmOptionType) {
		this.name=name;
		this.alarmOptionType=alarmOptionType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AlarmOptionType getAlarmOptionType() {
		return alarmOptionType;
	}
	public void setAlarmOptionType(AlarmOptionType alarmOptionType) {
		this.alarmOptionType = alarmOptionType;
	}

	  
	public static List<AlarmRuleType> getAlarmRuleTypeByAlarmOptionType(AlarmOptionType alarmOptionType){
		List<AlarmRuleType> alarmRuleTypes = new ArrayList<AlarmRuleType>();
		for (AlarmRuleType a : AlarmRuleType.values()) {
			if (a.getAlarmOptionType() == alarmOptionType) {
				alarmRuleTypes.add(a);
			}
		}
		return alarmRuleTypes;
	}
	@Override
	public void serialize(JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("alarmRuleType");
		generator.writeString(toString());
		generator.writeFieldName("name");
		generator.writeString(name);
		generator.writeEndObject();
		
	}
	@Override
	public void serializeWithType(JsonGenerator generator, SerializerProvider provider,
			TypeSerializer serializer) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}
	public static List<String> getAlarmRuleTypeForStringByAlarmOptionType(
			AlarmOptionType alarmOptionType) {
		List<String> ordinalList = new ArrayList<String>();
		for (AlarmRuleType a : AlarmRuleType.values()) {
			if (a.getAlarmOptionType() == alarmOptionType) {
				ordinalList.add("'"+a.toString()+"'");
			}
		}
		return ordinalList;
	}

	
	

}
