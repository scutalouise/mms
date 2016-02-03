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
 * @Description:二级设备枚举类
 * @Author:ranjunfeng
 * @Since :2015年12月23日 上午10:16:59
 */
public enum SecondDeviceType implements JsonSerializable {
	//主机设备
	TERMINAL(0,"终端",FirstDeviceType.HOSTDEVICE),
	INTEGRATEDMACHINE(1,"一体机",FirstDeviceType.HOSTDEVICE),
	SERVER(2,"服务器",FirstDeviceType.HOSTDEVICE),
	//采集设备
	GIT(3,"GIT网关",FirstDeviceType.COLLECTDEVICE),
	DATACOLLECT(4,"数据采集设备",FirstDeviceType.COLLECTDEVICE),
	HANDDEVICE(5,"手持机",FirstDeviceType.COLLECTDEVICE),
	//动环设备
	UPS(6,"UPS设备",FirstDeviceType.PEDEVICE),
	TH(7,"温湿度",FirstDeviceType.PEDEVICE),
	WATER(8,"水浸",FirstDeviceType.PEDEVICE),
	SMOKE(9,"烟感",FirstDeviceType.PEDEVICE),
	//网络设备
	SWITCHBOARD(10,"交换机",FirstDeviceType.NETWORKDEVICE),
	ROUTER(11,"路由器",FirstDeviceType.NETWORKDEVICE),
	FIREWALL(12,"防火墙",FirstDeviceType.NETWORKDEVICE),
	//非智能设备
	PRINTER(13,"打印机",FirstDeviceType.UNINTELLIGENTDEVICE),
	SCANNER(14,"扫描仪",FirstDeviceType.UNINTELLIGENTDEVICE),
	FAX(15,"传真机",FirstDeviceType.UNINTELLIGENTDEVICE),
	LAMINATOR(16,"塑封机",FirstDeviceType.UNINTELLIGENTDEVICE),
	COUNTER(17,"点钞机",FirstDeviceType.UNINTELLIGENTDEVICE),
	SORTER(18,"清分机",FirstDeviceType.UNINTELLIGENTDEVICE),
	POS(19,"POS机",FirstDeviceType.UNINTELLIGENTDEVICE),
	FINGERPRINT(20,"指纹仪",FirstDeviceType.UNINTELLIGENTDEVICE),
	CARDREADER(21,"读卡器",FirstDeviceType.UNINTELLIGENTDEVICE),
	PASSWORDKEYBOARD(22,"密码键盘",FirstDeviceType.UNINTELLIGENTDEVICE),
	HIGHSHOTMETER(23,"高拍仪",FirstDeviceType.UNINTELLIGENTDEVICE),
	IDCARDREADER(24,"身份证阅读器",FirstDeviceType.UNINTELLIGENTDEVICE),
	BINDING(25,"扎把机",FirstDeviceType.UNINTELLIGENTDEVICE),
	CARDSENDER(26,"发卡机",FirstDeviceType.UNINTELLIGENTDEVICE);

	;
	/**
	 * id标识
	 */
	private int id; 
	/**
	 * 名称
	 */
	private String name; 
	/**
	 * 一级设备枚举
	 */
	private FirstDeviceType firstDeviceType; 
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
	public FirstDeviceType getFirstDeviceType() {
		return firstDeviceType;
	}
	public void setFirstDeviceType(FirstDeviceType firstDeviceType) {
		this.firstDeviceType = firstDeviceType;
	}
	private SecondDeviceType(int id,String name,FirstDeviceType firstDeviceType) {
		this.id=id;
		this.name=name;
		this.firstDeviceType=firstDeviceType;
	}
	public static List<SecondDeviceType> getSecondDeviceTypeByFirstDeviceType(FirstDeviceType firstDeviceType){
		List<SecondDeviceType> secondDeviceTypes=new ArrayList<SecondDeviceType>();
		for (SecondDeviceType secondDeviceType : SecondDeviceType.values()) {
			if(secondDeviceType.getFirstDeviceType()==firstDeviceType){
				secondDeviceTypes.add(secondDeviceType);
			}
		}
		
		return secondDeviceTypes;
	}

	@Override
	public void serialize(JsonGenerator generator, SerializerProvider arg1) throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("secondDeviceType");
		generator.writeString(toString());
		generator.writeFieldName("id");
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
