package com.agama.itam.service;

import java.io.Serializable;

import com.agama.common.service.IBaseService;
import com.agama.itam.domain.Arrange;

/**
 * @Description:运维排程业务接口
 * @Author:佘朝军
 * @Since :2016年2月1日 下午3:49:56
 */
public interface IArrangeService extends IBaseService<Arrange, Serializable> {

	public Arrange findByUserId(Integer userId);
	
	public Arrange findEnableArrangeByUserId(Integer userId);
	
}
