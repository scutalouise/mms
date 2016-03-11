package com.agama.authority.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.authority.dao.IAreaInfoDao;
import com.agama.authority.dao.IOrganizationDao;
import com.agama.authority.entity.AreaInfo;
import com.agama.authority.service.IAreaInfoService;
import com.agama.common.dao.IRecycleDao;
import com.agama.common.dao.utils.EntityUtils;
import com.agama.common.domain.StateEnum;
import com.agama.common.domain.TreeBean;
import com.agama.common.entity.Recycle;
import com.agama.common.enumbean.RecycleEnum;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.service.impl.BaseServiceImpl;

@Service("areaInfoService")
@Transactional
public class AreaInfoServiceImpl extends BaseServiceImpl<AreaInfo, Integer>
		implements IAreaInfoService {

	@Autowired
	private IAreaInfoDao areaInfoDao;
	@Autowired
	private IOrganizationDao organizationDao;
	@Autowired
	private IRecycleDao recycleDao;
	@Autowired
	private EntityUtils entityUtils;
	
	@Override
	public List<TreeBean> getTreeByPid(Integer pid) {
		// TODO Auto-generated method stub
		List<AreaInfo> areaInfos = areaInfoDao.findListByPid(pid);
		List<TreeBean> treeBeans = new ArrayList<TreeBean>();
		for (AreaInfo areaInfo : areaInfos) {
			TreeBean treeBean = new TreeBean();
			treeBean.setId(areaInfo.getId());
			treeBean.setText(areaInfo.getAreaName());
			if (areaInfoDao.find(Restrictions.eq("pid", areaInfo.getId()))
					.size() > 0) {
				treeBean.setState("closed");
			} else {
				treeBean.setState("open");
			}
			treeBeans.add(treeBean);
		}
		return treeBeans;
	}

	private String recursiveByPid(Integer pid) {
		StringBuffer pidStr = new StringBuffer();
		List<AreaInfo> areaInfoList = areaInfoDao.findListByPid(pid);
		for (AreaInfo areaInfo : areaInfoList) {
			pidStr.append(",").append(areaInfo.getId());
			pidStr.append(recursiveByPid(areaInfo.getId()));

		}

		return pidStr.toString();
	}

	@Override
	public String getAreaInfoIdStrById(Integer id) {
		String idStr = null;
		if (id != null) {
			idStr = id + recursiveByPid(id);
		}
		return idStr;
	}

	@Override
	public List<AreaInfo> getListRelevancyOrganization(StateEnum stateEnum,String searchValue) {
		List<AreaInfo> areaInfos=areaInfoDao.getListRelevancyOrganization(stateEnum,searchValue);
		List<AreaInfo> areaInfoList=new ArrayList<AreaInfo>();
		for (AreaInfo areaInfo : areaInfos) { 
			AreaInfo a=new AreaInfo();
			a.setId(areaInfo.getId());
			a.setAreaName(areaInfo.getAreaName());
			if(stateEnum!=null){
				if(stateEnum==StateEnum.good){
					a.setGoodOrganizations(organizationDao.findListByAreaIdAndStatus(areaInfo.getId(), StateEnum.good,searchValue));

				}else if(stateEnum==StateEnum.warning){
					a.setWarningOrganizations(organizationDao.findListByAreaIdAndStatus(areaInfo.getId(), StateEnum.warning,searchValue));

				}else if(stateEnum==StateEnum.error){
					a.setErrorOrganizations(organizationDao.findListByAreaIdAndStatus(areaInfo.getId(), StateEnum.error,searchValue));
					
				}
			}else{
				a.setGoodOrganizations(organizationDao.findListByAreaIdAndStatus(areaInfo.getId(), StateEnum.good,searchValue));
				a.setWarningOrganizations(organizationDao.findListByAreaIdAndStatus(areaInfo.getId(), StateEnum.warning,searchValue));

				a.setErrorOrganizations(organizationDao.findListByAreaIdAndStatus(areaInfo.getId(), StateEnum.error,searchValue));
			}
			
			if((a.getGoodOrganizations()!=null&&a.getGoodOrganizations().size()>0)||(a.getWarningOrganizations()!=null&&a.getWarningOrganizations().size()>0)||(a.getErrorOrganizations()!=null&&a.getErrorOrganizations().size()>0)){
				areaInfoList.add(a);
			}
		}
		
		return areaInfoList;
	}

	/**
	 * 提供支持回收站的删除操作；
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id, Integer opUserId) {
		/**
		 * 删除操作的步骤：
		 * 一.组合要删除记录的记录信息，进行保存，即保存删除记录到Recycle
		 * 二.修改当前实体删除的逻辑状态
		 * 三.修改列表查询中筛选的条件，将逻辑删除的记录排除掉；
		 */
		List<AreaInfo> list = recursiveAreaInfosByPid(id);
		list.add(0, areaInfoDao.find(id));//需要包含当前级别；
		List<Integer>  ids = new ArrayList<Integer>();
		if(list.size() > 0){
			Recycle recycle = new Recycle();
			for(AreaInfo areaInfo : list){
				ids.add(areaInfo.getId());
				areaInfo.setStatus(StatusEnum.DELETED);//此处要确认是使用Id还是name，还是使用string本身
				areaInfoDao.update(areaInfo);
			}
			
			recycle.setContent(ids.toString());
			recycle.setIsRecovery(RecycleEnum.NO);
			recycle.setOpTime(new Date());
			recycle.setOpUserId(opUserId);
			recycle.setTableName(entityUtils.getTableNameByEntity(AreaInfo.class.getName()));
			recycle.setTableRecordId(entityUtils.getIdNameByEntityName(AreaInfo.class.getName()));
			recycleDao.save(recycle);
		}
	}
	
	/**
	 * @Description:根据pid查找所有的AreaInfo区域信息；
	 * @param pid
	 * @return
	 * @Since :2016年2月26日 上午10:21:58
	 */
	private List<AreaInfo> recursiveAreaInfosByPid(Integer pid) {
		List<AreaInfo> list = new ArrayList<AreaInfo>();
		List<AreaInfo> areaInfoList = areaInfoDao.findListByPid(pid);
		for (AreaInfo areaInfo : areaInfoList) {
			list.add(areaInfo);
			list.addAll(recursiveAreaInfosByPid(areaInfo.getId()));
		}
		return list;
	}

}
