package com.agama.itam.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.agama.itam.mongo.domain.ProblemHandle;

/**
 * @Description:问题处理过程记录接口
 * @Author:佘朝军
 * @Since :2016年1月27日 下午4:04:47
 */
public interface IProblemHandleService {

	public ProblemHandle get(ObjectId objectId) throws Exception;

	public List<ProblemHandle> findListByProblemId(Long problemId) throws Exception;
	
	public ProblemHandle getLatestByProblemId(Long problemId) throws Exception;

	public void saveProblemHandle(ProblemHandle problemHandle) throws Exception;

}
