package com.agama.pemm.dao.impl;

import org.springframework.stereotype.Repository;



import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.IUpsStatusDao;
import com.agama.pemm.domain.UpsStatus;
@Repository(value="upsStatusDao")
public class UpsStatusDaoImpl extends HibernateDaoImpl<UpsStatus,Long> implements IUpsStatusDao {

	
	
}
