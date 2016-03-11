package com.agama.common.enumbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public enum AlarmOptionUnitEnum implements JsonSerializable{
	//库存数量单位
	NUMBER("数量","",DeviceAlarmOptionEnum.INVENTORYNUM),
	PERCENTAGE("百分比","%",DeviceAlarmOptionEnum.INVENTORYNUM),
	//维修时间单位
	MAINTENANCETIME_OF_YEAR("年","年",DeviceAlarmOptionEnum.MAINTENANCETIME),
	MAINTENANCETIME_OF_MONTH("月","月",DeviceAlarmOptionEnum.MAINTENANCETIME),
	MAINTENANCETIME_OF_DAY("日","日",DeviceAlarmOptionEnum.MAINTENANCETIME),
	MAINTENANCETIME_OF_HOURS("时","时",DeviceAlarmOptionEnum.MAINTENANCETIME),
	//报废时间
	SCRAPPEDTIME_OF_YEAR("年","年",DeviceAlarmOptionEnum.SCRAPPEDTIME),
	SCRAPPEDTIME_OF_MONTH("月","月",DeviceAlarmOptionEnum.SCRAPPEDTIME),
	SCRAPPEDTIME_OF_DAY("日","日",DeviceAlarmOptionEnum.SCRAPPEDTIME),
	SCRAPPEDTIME_OF_HOURS("时","时",DeviceAlarmOptionEnum.SCRAPPEDTIME)
	;
	private String value;
	private String unitValue;
	private DeviceAlarmOptionEnum deviceAlarmOptionEnum;
	private AlarmOptionUnitEnum(String value,String unitValue,DeviceAlarmOptionEnum deviceAlarmOptionEnum) {
		this.value=value;
		this.unitValue=unitValue;
		this.deviceAlarmOptionEnum=deviceAlarmOptionEnum;

	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	public String getUnitValue() {
		return unitValue;
	}
	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}
	public DeviceAlarmOptionEnum getDeviceAlarmOptionEnum() {
		return deviceAlarmOptionEnum;
	}
	public void setDeviceAlarmOptionEnum(DeviceAlarmOptionEnum deviceAlarmOptionEnum) {
		this.deviceAlarmOptionEnum = deviceAlarmOptionEnum;
	}
	/**
	 * @Description:根据报警的内容获取报警内容的单位泛型
	 * @return
	 * @Since :2016年3月9日 下午1:53:23
	 */
	public static List<AlarmOptionUnitEnum> getAlarmOptionUnitEnumByDeviceAlarmOptionEnum(DeviceAlarmOptionEnum deviceAlarmOptionEnum){
		List<AlarmOptionUnitEnum> alarmOptionUnitEnums=new ArrayList<AlarmOptionUnitEnum>();
		for (AlarmOptionUnitEnum alarmOptionUnitEnum : AlarmOptionUnitEnum.values()) {
			if(alarmOptionUnitEnum.getDeviceAlarmOptionEnum()==deviceAlarmOptionEnum){
				alarmOptionUnitEnums.add(alarmOptionUnitEnum);
			}
		}
		
		return alarmOptionUnitEnums;
	}
	@Override
	public void serialize(JsonGenerator generator, SerializerProvider arg1)
			throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("alarmOptionUnit");
		generator.writeString(toString());
		generator.writeFieldName("unitValue");
		generator.writeString(unitValue);
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
