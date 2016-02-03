package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.device.dao.ITwoDimentionCodeDao;
import com.agama.device.domain.TwoDimentionCode;


@Repository
public class TwoDimentionCodeDaoImpl extends HibernateDaoImpl<TwoDimentionCode, Integer>implements ITwoDimentionCodeDao {

}
