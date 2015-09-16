package com.agama.pemm.dao;


import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.pemm.domain.UpsStatus;

public interface IUpsStatusDao extends IBaseDao<UpsStatus,Integer> {

	List<UpsStatus> findLatestDataByGitInfoId(Integer gitInfoId);

	void updateStatusByIds(String ids);
	

}
