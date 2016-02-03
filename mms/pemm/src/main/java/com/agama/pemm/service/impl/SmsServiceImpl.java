package com.agama.pemm.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.domain.Sms;
import com.agama.pemm.service.ISmsService;
@Service
@Transactional
public class SmsServiceImpl extends BaseServiceImpl<Sms, Integer> implements
		ISmsService {

	
}
