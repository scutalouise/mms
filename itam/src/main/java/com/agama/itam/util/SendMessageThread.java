package com.agama.itam.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agama.device.sms.SendMessage;

/**
 * @Description:发送短信的线程类
 * @Author:佘朝军
 * @Since :2016年2月19日 上午10:43:50
 */
@Component
public class SendMessageThread implements Runnable {
	@Autowired
	private SendMessage sendMessage;
	private String phoneNumber;
	private String smsContent;

	@Override
	public void run() {
		synchronized (sendMessage) {
			sendMessage.doIt(phoneNumber, smsContent);
		}
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

}
