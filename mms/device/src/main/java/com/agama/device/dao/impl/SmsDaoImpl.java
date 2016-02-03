package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.device.dao.ISmsDao;
import com.agama.device.domain.Sms;
@Repository
public class SmsDaoImpl extends HibernateDaoImpl<Sms, Integer> implements
		ISmsDao {

	
}
