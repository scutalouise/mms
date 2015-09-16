package com.agama.pemm.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.bean.DeviceType;
import com.agama.pemm.dao.IGitInfoDao;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IGitInfoService;

@Service("gitInfoService")
@Transactional
public class GitInfoServiceImpl extends BaseServiceImpl<GitInfo, Integer>
		implements IGitInfoService {

	@Autowired
	private IGitInfoDao gitInfoDao;

	@Override
	public void updateStatusByIds(String ids) {
		gitInfoDao.updateStatusByIds(ids);

	}

	public List<GitInfo> getListByStatus(Integer status) {
		return gitInfoDao.find(Restrictions.eq("status", status));

	}

	@Override
	public List<GitInfo> getListByAreaInfoIdStr(String areaInfoIdStr) {
		StringBuffer hql = new StringBuffer("from GitInfo where status=0");
		if (areaInfoIdStr != null && !areaInfoIdStr.trim().equals("")) {
			hql.append(" and areaInfoId in (").append(areaInfoIdStr).append(")");
		}
		return gitInfoDao.find(hql.toString());
	}

	@Override
	public Page<GitInfo> searchListByAreaInfoId(Integer areaInfoId,Page<GitInfo> page, List<PropertyFilter> filters) {
	StringBuffer hql=new StringBuffer("from GitInfo where status=0");
	if(areaInfoId!=null){
		hql.append(" and areaInfoId=?0");
	}
		return areaInfoId!=null?gitInfoDao.findPage(page,hql.toString(),areaInfoId):gitInfoDao.findPage(page,hql.toString()) ;

	}

	@Override
	public GitInfo getGitInfoByIpAndId(String ip, Integer gitInfoId) {
		StringBuffer hql=new StringBuffer("from GitInfo where status=0 and ip='").append(ip).append("'");
		if(gitInfoId!=null){
			hql.append(" and id!=").append(gitInfoId);
		}
		return gitInfoDao.findUnique(hql.toString());
	}

	@Override
	public List<GitInfo> getListByAreaInfoIdStrAndDeviceType(
			String areaInfoIdStr, DeviceType deviceType) {
		StringBuffer hql=new StringBuffer("from GitInfo where status=0");
		if (areaInfoIdStr != null && !areaInfoIdStr.trim().equals("")) {
			hql.append(" and areaInfoId in (").append(areaInfoIdStr).append(")");
		}
		
		hql.append(" and id in (select gitInfo.id from Device where status=0 and deviceType=").append(DeviceType.UPS.ordinal()).append(") ");
		return gitInfoDao.find(hql.toString());
	
	}
	

}
