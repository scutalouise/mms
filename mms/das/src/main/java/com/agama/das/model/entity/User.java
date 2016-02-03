package com.agama.das.model.entity;

import java.util.Date;

import lombok.Data;

import org.bson.types.ObjectId;

/**@Description:用户对象
 * @Author:佘朝军
 * @Since :2015年11月11日 下午4:04:43
 */
@Data
public class User {
	
	private ObjectId id;
	private String userName;
	private String password;
	private Date lastLoginTime;
	private int totalLogin;
	private String lastLoginIp;

}
