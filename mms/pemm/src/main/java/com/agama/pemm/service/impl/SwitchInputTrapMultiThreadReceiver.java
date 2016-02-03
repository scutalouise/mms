package com.agama.pemm.service.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agama.common.enumbean.DeviceType;
import com.agama.pemm.domain.Device;
import com.agama.pemm.protocol.snmp.SwitchInputOidInfo;
import com.agama.pemm.service.IDeviceService;
import com.agama.pemm.task.DataCollectionService;
@Component
public class SwitchInputTrapMultiThreadReceiver implements CommandResponder {
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private DataCollectionService dataCollectionService;
	private MultiThreadedMessageDispatcher dispatcher;
	private Snmp snmp = null;
	private Address listenAddress;
	private ThreadPool threadPool;

	public SwitchInputTrapMultiThreadReceiver() {
	}

	private void init() throws UnknownHostException, IOException {

		threadPool = ThreadPool.create("Trap", 2);
		dispatcher = new MultiThreadedMessageDispatcher(threadPool,
				new MessageDispatcherImpl());
		listenAddress = GenericAddress.parse(System.getProperty(
				"snmp4j.listenAddress", "udp:0.0.0.0/162"));
		TransportMapping<?> transport;
		if (listenAddress instanceof UdpAddress) {
			transport = new DefaultUdpTransportMapping(
					(UdpAddress) listenAddress);
		} else {
			transport = new DefaultTcpTransportMapping(
					(TcpAddress) listenAddress);
		}
		snmp = new Snmp(dispatcher, transport);
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
				MPv3.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);
		snmp.listen();
	}
	@PostConstruct
	public void run() {

		try {
			
			init();
			snmp.addCommandResponder(this);
			System.out.println("---->开始监听端口，等待Trap message<----");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processPdu(CommandResponderEvent event) {
		// 取设备的IP地址
		String peerAddress = event.getPeerAddress().toString();
		String ip=peerAddress.substring(0,peerAddress.indexOf("/"));
		

		PDU command = event.getPDU();
		if (command != null) {
			Vector<VariableBinding> recVBs = (Vector<VariableBinding>) command
					.getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.elementAt(i);
				String oid=recVB.getOid().toString();
				//trap的oid如果包含了输出开关量状态oid则进行数据获取
				if(oid.contains(SwitchInputOidInfo.SIGNAL.getOid())){
				Integer index=Integer.parseInt(oid.substring(oid.lastIndexOf(".")+1));
				
					List<Device> deviceList = deviceService
							.getDeviceByIpAndDeviceTypeAndIndex(ip,
									DeviceType.SWITCHINPUT, index);
					for (Device device : deviceList) {
						//String status=recVB.getVariable().toString();
						dataCollectionService.collectSwitchInputStatus(ip, index, device.getId());
						
					}
				}
				System.out.println(recVB.getOid() + " : " + recVB.getVariable());
			}
			
			java.text.SimpleDateFormat date = new java.text.SimpleDateFormat(
					"yyyy/MM/dd, hh:mm:ss");
			String receivedTime = date.format(new Date(System
					.currentTimeMillis()));
			System.out.println("trap接受时间: " + receivedTime);
		}

	}



//	public static void main(String[] args) {
//		SnmpTrapMultiThreadReceiver trapReceiver = new SnmpTrapMultiThreadReceiver();
//		trapReceiver.run();
//	}

}
