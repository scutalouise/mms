package com.agama.das.model.entity;

import java.util.Date;
import java.util.Map;

import lombok.Data;

import org.bson.types.ObjectId;

/**@Description:设备巡检结果记录
 * @Author:佘朝军
 * @Since :2015年11月12日 下午5:32:08
 */
@Data
public class InspectedResult {
	
	private ObjectId id;
	private String identifier;
	private String deviceType;
	private boolean dataStatus;
	private String errorMessage;
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date checkTime;
	private Map<String,Object> dataInfo;

}
