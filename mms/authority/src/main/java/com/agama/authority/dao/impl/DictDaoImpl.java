package com.agama.authority.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.authority.dao.IDictDao;
import com.agama.authority.entity.Dict;
import com.agama.common.dao.impl.HibernateDaoImpl;

/**
 * @Description:字典DAO实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:22:32
 */
@Repository("dictDao")
public class DictDaoImpl extends HibernateDaoImpl<Dict, Integer> implements IDictDao{

}
