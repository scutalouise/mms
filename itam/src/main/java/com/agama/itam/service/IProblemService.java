package com.agama.itam.service;

import java.io.Serializable;
import java.util.List;

import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.service.IBaseService;
import com.agama.itam.domain.Problem;

/**
 * @Description:问题记录业务接口
 * @Author:佘朝军
 * @Since :2016年1月19日 下午2:58:37
 */
public interface IProblemService extends IBaseService<Problem, Serializable> {

	public List<Problem> getAllList() throws Exception;

	public Page<Problem> searchForPage(Page<Problem> page, List<PropertyFilter> filters);

	public Page<Problem> searchListForHandling(String identifier, Integer problemTypeId, Integer enable, String recordTime, String recordEndTime,
			List<Integer> roleIds, Page<Problem> page) throws Exception;

	public void saveProblem(Problem problem, User user) throws Exception;

	public void deleteProblem(Problem problem, User user) throws Exception;

	public void updateProblem(Problem problem, User user) throws Exception;
}
