package com.agama.device.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.device.dao.IBrandDao;
import com.agama.device.domain.Brand;

@Repository
public class BrandDaoImpl extends HibernateDaoImpl<Brand, Integer>implements IBrandDao {

}
