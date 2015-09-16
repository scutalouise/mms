package com.agama.authority.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.authority.system.dao.IAreaInfoDao;
import com.agama.authority.system.entity.AreaInfo;
import com.agama.authority.system.service.IAreaInfoService;
import com.agama.common.domain.TreeBean;
import com.agama.common.service.impl.BaseServiceImpl;

@Service("areaInfoService")
@Transactional
public class AreaInfoServiceImpl extends BaseServiceImpl<AreaInfo, Integer>
		implements IAreaInfoService {

	@Autowired
	private IAreaInfoDao areaInfoDao;

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

}
