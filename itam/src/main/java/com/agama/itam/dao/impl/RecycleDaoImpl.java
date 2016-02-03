package com.agama.itam.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.itam.dao.IRecycleDao;
import com.agama.itam.domain.Recycle;

/**
 * @Description:处理回收站的Dao实现
 * @Author:杨远高
 * @Since :2016年1月29日 上午10:37:54
 */
@Repository
public class RecycleDaoImpl extends HibernateDaoImpl<Recycle, Serializable> implements IRecycleDao {

}
