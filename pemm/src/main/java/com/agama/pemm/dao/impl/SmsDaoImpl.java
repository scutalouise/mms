package com.agama.pemm.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.ISmsDao;
import com.agama.pemm.domain.Sms;
@Repository
public class SmsDaoImpl extends HibernateDaoImpl<Sms, Integer> implements
		ISmsDao {

	
}
