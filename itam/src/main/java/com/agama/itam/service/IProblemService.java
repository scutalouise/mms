package com.agama.itam.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.agama.authority.entity.User;
import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.service.IBaseService;
import com.agama.itam.bean.DeviceFaultTopNBean;
import com.agama.itam.bean.MaintenanceTopNBean;
import com.agama.itam.bean.SupplyTopNBean;
import com.agama.itam.domain.Problem;

/**
 * @Description:问题记录业务接口
 * @Author:佘朝军
 * @Since :2016年1月19日 下午2:58:37
 */
public interface IProblemService extends IBaseService<Problem, Serializable> {

	public Page<Problem> searchForPage(Page<Problem> page, List<PropertyFilter> filters) throws Exception;

	public Page<Problem> searchListForHandling(String problemCode, Integer problemTypeId, Integer resolveUserId, String enable, String recordTime, String recordEndTime,
			Page<Problem> page, String model) throws Exception;

	/**
	 * @Description:根据问题信息和操作人信息保存问题记录和问题处理过程记录
	 * @param problem 问题记录信息
	 * @param user 操作用户信息
	 * @throws Exception
	 * @Since :2016年2月17日 上午9:06:03
	 */
	public void saveProblem(Problem problem, User user) throws Exception;

	public void deleteProblem(Problem problem, User user) throws Exception;

	public void updateProblem(Problem problem, User user) throws Exception;
	
	/**
	 * @Description:执行将已解决的问题进行关闭时的操作
	 * @param problem 问题记录信息对象
	 * @param user 操作人
	 * @throws Exception
	 * @Since :2016年2月18日 上午11:26:04
	 */
	public void shutdown(Problem problem, User user, Integer classificationId) throws Exception;
	
	/**
	 * @Description:向运维人员发送设备报修短信
	 * @param problem 问题记录信息
	 * @param user 信息接收用户信息
	 * @Since :2016年2月18日 下午5:19:01
	 */
	public void sendPhoneMessage(Problem problem, User receiveUser);
	
	public void updateChangeDeviceRepair(Problem problem, User user, String changeDeviceIdentifier) throws Exception;
	
	public void saveAlarmProblem(List<Map<String, String>> list) throws Exception;
	
    public List<SupplyTopNBean> findDataBySQL(HttpServletRequest request);
	
	public List<MaintenanceTopNBean> findMaintenanceDataBySQL(HttpServletRequest request);
	
	public List<DeviceFaultTopNBean> findDeviceFaultDataBySQL(HttpServletRequest request);
	
	public Problem getDetailsById(Long id) throws Exception;
}
