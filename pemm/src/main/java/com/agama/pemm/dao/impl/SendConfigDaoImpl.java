package com.agama.pemm.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.pemm.dao.ISendConfigDao;
import com.agama.pemm.domain.SendConfig;
/**
 * @Description:发送消息的参数配置数据访问层
 * @Author:ranjunfeng
 * @Since :2015年10月21日 下午2:32:01
 */
@Repository
public class SendConfigDaoImpl extends HibernateDaoImpl<SendConfig, Integer	> implements
		ISendConfigDao {


}
