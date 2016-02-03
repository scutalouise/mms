package com.agama.pemm.sms;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smslib.GatewayException;
import org.smslib.Message.MessageEncodings;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agama.pemm.domain.Sms;

@Component
public class SendMessage {

	private static Logger logger = LoggerFactory.getLogger(SendMessage.class);
	@Autowired
	private SessionFactory sessionFactory;
	private static Service service;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {

		Session session = sessionFactory.openSession();
		List<Sms> smsList = session.createCriteria(Sms.class).list();
		session.close();
		Sms sms = new Sms();
		if (smsList != null && smsList.size() > 0) {
			sms = smsList.get(0);

			SerialModemGateway gateway = new SerialModemGateway(
					sms.getGatewayId(), sms.getComPort(), sms.getBaudRate(),
					sms.getManufacturer(), sms.getModel());
			gateway.setInbound(true); // 设置true，表示该网关可以接收短信
			gateway.setOutbound(true); // 设置true，表示该网关可以发送短信
			// 创建发送短信的服务(它是单例的)
			service = Service.getInstance();
			// 将设备加到服务中
			try {
				service.addGateway(gateway);
			} catch (GatewayException e) {
				logger.error("网关异常:" + e);
			}
		} else {

		}
	}

	public void doIt(String sendPhoneNumber, String smsContent) {

		try {

			service.startService();
			OutboundMessage msg = new OutboundMessage(sendPhoneNumber,
					smsContent);
			msg.setEncoding(MessageEncodings.ENCUCS2);
			service.sendMessage(msg);
			logger.info("短息发送成功！");
			// 关闭服务
			service.stopService();
		} catch (TimeoutException e) {
			logger.error("连接超时:" + e);
		} catch (SMSLibException e) {
			logger.error("发送短息异常:" + e);
		} catch (IOException e) {
			logger.error("短息IO异常:" + e);
		} catch (InterruptedException e) {
			logger.error("线程中断:" + e);
		}

	}

}
