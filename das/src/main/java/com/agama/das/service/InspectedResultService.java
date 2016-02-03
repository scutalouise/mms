package com.agama.das.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.agama.das.model.entity.InspectedResult;

/**
 * @Description:接口类，提供巡检数据处理的操作方法
 * @Author:佘朝军
 * @Since :2015年11月23日 下午1:54:48
 */
public interface InspectedResultService {

	public void insert(InspectedResult inspectedResult);

	public List<InspectedResult> getAll();

	public void removeOneByObjectId(ObjectId objectId);

	public void removeByList(List<InspectedResult> resultList);

}
