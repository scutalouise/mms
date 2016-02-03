package com.agama.pemm.dao;

import java.util.List;

import com.agama.common.dao.IBaseDao;
import com.agama.common.enumbean.DeviceInterfaceType;
import com.agama.pemm.domain.AcStatus;

public interface IAcStatusDao extends IBaseDao<AcStatus,Integer>{

	public List<AcStatus> findLastestDataByGitInfoId(Integer gitInfoId);

}
