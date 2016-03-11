package com.agama.itam.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.agama.common.enumbean.AlarmRuleType;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.service.IBaseService;
import com.agama.itam.domain.ProblemType;

/**
 * @Description:问题类型业务接口
 * @Author:佘朝军
 * @Since :2016年1月19日 上午9:53:20
 */
public interface IProblemTypeService extends IBaseService<ProblemType, Serializable> {

	public List<ProblemType> getListByIdentifier(String identifier) throws Exception;

	public List<Map<String, Object>> getListByIdentifierForHandset(String identifier) throws Exception;

	public List<ProblemType> getAllList() throws Exception;
	
	public ProblemType getUniqueByAlarmType(AlarmRuleType art);
	
	public List<ProblemType> getListByDeviceType(FirstDeviceType deviceType) throws Exception;

}
