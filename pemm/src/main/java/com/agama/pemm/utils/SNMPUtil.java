package com.agama.pemm.utils;



import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.Counter64;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.agama.tool.utils.date.DateUtils;



/**
 * SNMP Demo <br>
 * 通过SNMP对AEM进行数据采集与控制
 * <li>目前AEM的采集使用的版本为snmp v1
 * <li>snmp4j的jar包必须使用本实例项目中自带的，否则可能会出现部分数据无法采集的问题。
 * @author YangQi
 *
 */
public class SNMPUtil {
	private static Snmp snmp = null ;
	
	public SNMPUtil(){
		init();
	}
	
	/**
	 * 初始化SNMP监听服务
	 */
	public void init(){
		try {
			TransportMapping<?> transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			transport.listen();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过GET方式获取AEM数据
	 * @param ipAddress AEM设备IP地址
	 * @param oids 待查询的OID列表
	 */
	public  Map<String,String> walkByGet(String ipAddress , List<String> oids) throws Exception{
		//创建target
		Address targetAddress = GenericAddress.parse("udp:"+ipAddress+"/161");
		Target target = new CommunityTarget();
		target.setVersion(SnmpConstants.version1);
		((CommunityTarget) target).setCommunity(new OctetString("public"));
		//设置targetIP地址
		target.setAddress(targetAddress);
		//设置超时重试次数
		target.setRetries(3);
		//设置超时
		target.setTimeout(500);
		//发送SNMP消息
		ResponseEvent event = snmp.send(createGetPdu(oids), target);
		Map<String,String> result = new HashMap<String,String>();
		//处理响应
		if (null != event && null != event.getResponse() ) { 
			Vector<? extends VariableBinding> recVBs = event.getResponse().getVariableBindings();
			//处理所有OID对应的信息
			for (int i = 0; i < recVBs.size(); i++) {  
				VariableBinding recVB = (VariableBinding)recVBs.elementAt(i);
				String value = recVB.getVariable().toString() ;
				result.put(recVB.getOid().toString(), " "+recVB.getVariable().toString());
			} 
		}else{
			System.out.println("未查询到数据或查询超时！");
		}
		snmp.close();
		return result;
	}
	
	/**
	 * 创建GET方式的PDU
	 * @param oids OID列表
	 * @return
	 */
	private  PDU createGetPdu(List<String> oids){
		PDU pdu = new PDU();
		pdu.setType(PDU.GET);
		for(String str : oids){
			OID oid = new OID(str);
			pdu.add(new VariableBinding(oid));
		}
		return pdu ;
	}
	
	/**
	 * 通过GET NEXT方式获取数据
	 * @param address 设备IP地址
	 * @param startOID 起始OID
	 * @throws Exception
	 */
	public  Map<String,String> walkByGetNext(String address ,String startOID) throws Exception{
		PDU pdu = new PDU();
		pdu.setType(PDU.GETNEXT);
		OID oid = new OID(startOID);
		pdu.add(new VariableBinding(oid));
		Address targetAddress = GenericAddress.parse("udp:"+address+"/161");
		Target target = new CommunityTarget();
		target.setVersion(SnmpConstants.version1);
		((CommunityTarget) target).setCommunity(new OctetString("public"));
		//设置targetIP地址
		target.setAddress(targetAddress);
		//设置超时重试次数
		target.setRetries(3);
		//设置超时
		target.setTimeout(1000);
		//处理响应
		ResponseEvent event = null ;
		Map<String,String> result = new HashMap<String,String>();
		while(( event = snmp.send(pdu, target)) != null  && null != event.getResponse()){
			Vector<? extends VariableBinding> recVBs = event.getResponse().getVariableBindings();
				//处理所有OID对应的信息
				VariableBinding recVB = (VariableBinding)recVBs.elementAt(0);
				VariableBinding va = pdu.get(0);
				if(recVB.getOid().compareTo(va.getOid()) == 0){
					result.put("collectTime",DateUtils.dateFormat(new Date()));
					System.out.println("Snmp Walk End !" );
					return result;
				}
				if(recVB.getVariable() instanceof Counter64){
					Counter64 val = (Counter64)recVB.getVariable() ;
					result.put(recVB.getOid().toString(), " "+val.toLong());
				}else{
					result.put(recVB.getOid().toString(), " "+recVB.getVariable().toString());
				}
				pdu.remove(0);
				pdu.add(new VariableBinding(recVB.getOid()));
		}
		snmp.close();
		return result ;
	}
	
	/**
	 * 发送SNMP SET命令
	 * @param address 设备IP地址
	 * @param oid 需要设定值得OID
	 * @param num 设定值（也可以是其他类型）
	 * @throws Exception
	 */
	public  void sendSetCommand(String address, String oid, int num) throws Exception{
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(oid), new Integer32(num)));
		pdu.setType(PDU.SET);
		Address targetAddress = GenericAddress.parse("udp:"+address+"/161");
		Target target = new CommunityTarget();
		target.setVersion(SnmpConstants.version1);
		((CommunityTarget) target).setCommunity(new OctetString("public"));
		//设置targetIP地址
		target.setAddress(targetAddress);
		//设置超时重试次数
		target.setRetries(3);
		//设置超时
		target.setTimeout(500);
		//处理响应
		ResponseEvent event = snmp.send(pdu, target);
		if (null != event && null != event.getResponse()) { 
			Vector<? extends VariableBinding> recVBs = event.getResponse().getVariableBindings();
			//处理所有OID对应的信息
			for (int i = 0; i < recVBs.size(); i++) {  
				VariableBinding recVB = (VariableBinding)recVBs.elementAt(i);
				String value = recVB.getVariable().toString() ;
			} 
		}else{
			System.out.println("SET命令失败！");
		}
		snmp.close();
	}

}
