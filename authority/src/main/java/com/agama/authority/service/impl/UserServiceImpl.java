package com.agama.authority.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IUserDao;
import com.agama.authority.entity.User;
import com.agama.authority.service.IUserService;
import com.agama.common.dao.IRecycleDao;
import com.agama.common.dao.utils.EntityUtils;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.entity.Recycle;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.InternalEnum;
import com.agama.common.enumbean.RecycleEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.tool.utils.date.DateUtils;
import com.agama.tool.utils.security.Digests;
import com.agama.tool.utils.security.Encodes;

/**
 * @Description:用户service实现
 * @Author:scuta
 * @Since :2015年8月27日 上午10:49:16
 */
@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements IUserService{

	@Autowired	
	private IUserDao userDao;
	
	@Autowired
	private IRecycleDao recycleDao;
	
	@Autowired
	private EntityUtils entityUtils;

	/**
	 * 保存用户
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void save(User user) {
		try {
			entryptPassword(user);
			user.setCreateDate(DateUtils.getSysTimestamp());
			userDao.save(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改密码
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void updatePwd(User user) {
		entryptPassword(user);
		userDao.update(user);
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void delete(Integer id){
		if(!isSupervisor(id))
			userDao.delete(id);
	}
	
	/**
	 * 按登录名查询用户
	 * @param loginName
	 * @return 用户对象
	 */
	public User getUser(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 判断是否超级管理员
	 * @param id
	 * @return boolean
	 */
	public boolean isSupervisor(Integer id) {
		return id == 1;
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	@Transactional(readOnly=false)
	public void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 验证原密码是否正确
	 * @param user
	 * @param oldPwd
	 * @return
	 */
	public boolean checkPassword(User user,String oldPassword){
		byte[] salt =Encodes.decodeHex(user.getSalt()) ;
		byte[] hashPassword = Digests.sha1(oldPassword.getBytes(),salt, HASH_INTERATIONS);
		if(user.getPassword().equals(Encodes.encodeHex(hashPassword))){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改用户登录
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void updateUserLogin(User user){
		user.setLoginCount((user.getLoginCount()==null?0:user.getLoginCount())+1);
		user.setPreviousVisit(user.getLastVisit());
		user.setLastVisit(DateUtils.getSysTimestamp());
		update(user);
	}

	
	@Override
	public Page<User> getUsersByOrganizationId(Page<User> page, Integer organizationId, InternalEnum internal) {
		return userDao.getUsersByOrganizationId(page, organizationId, internal);
	}
	
	@Override
	public Page<User> getUsersByOrganizationId(Page<User> page, Integer organizationId) {
		return userDao.getUsersByOrganizationId(page, organizationId);
	}

	@Override
	public String getPhoneNumberById(Integer id) {
		User user = userDao.findUniqueBy("id", id);
		return user == null ? null : user.getPhone();
		
	}

	/**
	 * 提供带回收站功能的逻辑删除操作；
	 */
	@Transactional(readOnly = false)
	@Override
	public void delete(Integer id, Integer opUserId) {
		/**
		 * 删除操作的步骤：
		 * 一.组合要删除记录的记录信息，进行保存，即保存删除记录到Recycle
		 * 二.修改当前实体删除的逻辑状态
		 * 三.修改列表查询中筛选的条件，将逻辑删除的记录排除掉；
		 */
		User user = userDao.find(id);

		Recycle recycle = new Recycle();
		recycle.setContent(user.toString());
		recycle.setIsRecovery(RecycleEnum.NO);
		recycle.setOpTime(new Date());
		recycle.setOpUserId(opUserId);
		recycle.setTableName(entityUtils.getTableNameByEntity(User.class.getName()));
		recycle.setTableRecordId(entityUtils.getIdNameByEntityName(User.class.getName()));
		recycleDao.save(recycle);

		user.setStatus(StatusEnum.DELETED);//此处要确认是使用Id还是name，还是使用string本身
		userDao.update(user);
	}

	@Override
	public Page<User> findPage(Page<User> page, InternalEnum internal,List<PropertyFilter> filters) {
		StringBuffer hql =new StringBuffer( "select u from User u ");
		hql.append(" where enable='" +EnabledStateEnum.ENABLED +"' and status='" + StatusEnum.NORMAL + "' ");
		hql.append(" and belong='" + internal + "'");
		return userDao.findPage(page, hql.toString(), filters);
	}
	
	public List<User> getAllList() {
		return userDao.getAllList();
	}
	
}
