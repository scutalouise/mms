package com.agama.das.model.entity;

import lombok.Data;

import org.bson.types.ObjectId;

/**
 * @Description:系统配置对象
 * @Author:佘朝军
 * @Since :2015年11月12日 下午5:51:36
 */
@Data
public class SystemConfig {

	private ObjectId id;
	private String systemCode;
	private boolean connectCenter;
	private String centerAddress;
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private long lastDataGetTime;

}
