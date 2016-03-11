package com.agama.common.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.IRecycleDao;
import com.agama.common.entity.Recycle;

/**
 * @Description:处理回收站的Dao实现
 * @Author:杨远高
 * @Since :2016年1月29日 上午10:37:54
 */
@Repository("recycleDao")
public class RecycleDaoImpl extends HibernateDaoImpl<Recycle, Serializable> implements IRecycleDao {

}
