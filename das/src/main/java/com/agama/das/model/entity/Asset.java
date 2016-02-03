package com.agama.das.model.entity;

import java.util.Map;

import lombok.Data;

import org.bson.types.ObjectId;

/**@Description:资产设备信息对象
 * @Author:佘朝军
 * @Since :2015年11月12日 下午4:44:16
 */
@Data
public class Asset {
	
	private ObjectId id;
	private String identifier;
	private String deviceType;
	private String secondType;
	private String address;
	private String protocol;
	private String protocolVersion;
	private String userName;
	private String password;
	private int port;
	private Map<String,Object> metaData;
	
}