package com.agama.authority.service;

import com.agama.authority.entity.AuthorityTransmit;
import com.agama.common.service.IBaseService;

/**
 * @Description:权限移交Service
 * @Author:scuta
 * @Since :2015年12月30日 上午11:09:47
 */
public interface IAuthorityTransmitService extends IBaseService<AuthorityTransmit, Integer> {
	
	/**
	 * @Description:按照属性查找，是否存在；
	 * @param propertyName
	 * @param value
	 * @return
	 * @Since :2015年12月30日 下午3:31:35
	 */
	public boolean isExistsByProperty(String propertyName, Object value);
	
	
	
	/**
	 * @Description:判断是否为移交权限中的接收人
	 * @param acceptUserId
	 * @return
	 * @Since :2015年12月31日 上午11:04:46
	 */
	public boolean isTransmited(Integer acceptUserId);

}
