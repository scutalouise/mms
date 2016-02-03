package com.agama.pemm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.bean.SoundBean;
import com.agama.pemm.dao.ISendConfigDao;
import com.agama.pemm.domain.SendConfig;
import com.agama.pemm.service.ISendConfigService;
import com.agama.pemm.utils.SNMPUtil;
import com.alibaba.fastjson.JSON;
/**
 * @Description:发送消息参数配置业务逻辑层
 * @Author:ranjunfeng
 * @Since :2015年10月21日 下午2:34:33
 */
@Service
public class SendConfigServiceImpl extends BaseServiceImpl<SendConfig, Integer>
		implements ISendConfigService {
	@Autowired
	private ISendConfigDao sendConfigDao;
	@Override
	public boolean closeAlarm(String ip) {
		List<SendConfig> sendConfigs = sendConfigDao.findAll();
		SendConfig sendConfig;
		if (sendConfigs != null && sendConfigs.size() > 0) {
			sendConfig = sendConfigs.get(0);

			try {
				SNMPUtil snmpUtil=new SNMPUtil();
				snmpUtil.sendSetCommand(
						ip,
						"1.3.6.1.4.1.34651.3.1."
								+ JSON.parseObject(sendConfig.getContent(),
										SoundBean.class).getInterfaceType()
								+ ".0", 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
		
	}

	
}
