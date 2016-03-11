package com.agama.itam.dao;

import java.io.Serializable;

import com.agama.common.dao.IBaseDao;
import com.agama.itam.domain.Arrange;

/**
 * @Description:运维排程Dao接口
 * @Author:佘朝军
 * @Since :2016年2月1日 下午3:43:52
 */
public interface IArrangeDao extends IBaseDao<Arrange, Serializable> {

	public Arrange findByUserId(Integer userId);
	
	public Arrange findEnableArrangeByUserId(Integer userId);

}
