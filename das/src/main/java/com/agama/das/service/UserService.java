package com.agama.das.service;

import org.bson.types.ObjectId;

import com.agama.das.model.entity.User;

/**@Description:接口类，提供用户操作的方法
 * @Author:佘朝军
 * @Since :2015年11月11日 下午4:27:40
 */
public interface UserService {
	
	public void insert(User user);

	public void removeByObjectId(ObjectId objectId);
	
	public void update(User user);
	
	public User findOneByObjectId(ObjectId objectId);
	
	public User findOneByUserNameAndPassword(String userName,String password);
	
	public User findOneByUserName(String userName);

}
