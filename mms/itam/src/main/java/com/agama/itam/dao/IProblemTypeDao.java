package com.agama.itam.dao;

import java.io.Serializable;
import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.itam.domain.ProblemType;

/**
 * @Description:问题类型DAO接口
 * @Author:佘朝军
 * @Since :2016年1月19日 上午9:46:25
 */
public interface IProblemTypeDao extends IBaseDao<ProblemType, Serializable> {

	public List<ProblemType> getListByDeviceType(FirstDeviceType deviceType) throws Exception;

	public List<ProblemType> getAllList() throws Exception;

}
