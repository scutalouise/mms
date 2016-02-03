package com.agama.device.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.device.domain.Sms;
import com.agama.device.service.ISmsService;
@Service
@Transactional
public class SmsServiceImpl extends BaseServiceImpl<Sms, Integer> implements
		ISmsService {

	
}
