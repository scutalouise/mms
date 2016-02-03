package com.agama.authority.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.authority.dao.IAreaInfoDao;
import com.agama.authority.dao.IOrganizationDao;
import com.agama.authority.entity.AreaInfo;
import com.agama.authority.service.IAreaInfoService;
import com.agama.authority.service.IOrganizationService;
import com.agama.common.domain.StateEnum;
import com.agama.common.domain.TreeBean;
import com.agama.common.service.impl.BaseServiceImpl;

@Service("areaInfoService")
@Transactional
public class AreaInfoServiceImpl extends BaseServiceImpl<AreaInfo, Integer>
		implements IAreaInfoService {

	@Autowired
	private IAreaInfoDao areaInfoDao;
	@Autowired
	private IOrganizationDao organizationDao;

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

}
