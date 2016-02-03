package com.agama.pemm.utils;
import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.UnsignedInteger32;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpTrapSendDemo {
	public static final int DEFAULT_VERSION = SnmpConstants.version2c;
    public static final long DEFAULT_TIMEOUT = 3 * 1000L;
    public static final int DEFAULT_RETRY = 3;
  
    private Snmp snmp = null;
    private CommunityTarget target = null;
  
    public void init() throws IOException {
        System.out.println("---->初始 Trap 的IP和端口<----");
        target = createTarget4Trap("udp:192.168.2.127/162");
        TransportMapping<?> transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
    }
  
    /**
     * 向接收器发送Trap 信息
     *
     * @throws IOException
     */
    public void sendPDU() throws IOException {
    	PDU pdu = new PDU();
    	pdu.add(new VariableBinding(new OID(".1.3.6.1.2.12343.101.1"),new OctetString("MySnmpTrap")));  
        pdu.add(new VariableBinding(new OID(".1.3.6.1.2.12343.101.2"),new OctetString("OMG!")));  

  
        // 向Agent发送PDU
        pdu.setType(PDU.TRAP);
        snmp.send(pdu, target);

        System.out.println("----<Trap Send END>----");
    }
  
    /**
     * 创建对象communityTarget
     *
     * @param targetAddress
     * @param community
     * @param version
     * @param timeOut
     * @param retry
     * @return CommunityTarget
     */
    public static CommunityTarget createTarget4Trap(String address) {
        CommunityTarget target = new CommunityTarget();
        target.setAddress(GenericAddress.parse(address));
        target.setVersion(DEFAULT_VERSION);
        target.setTimeout(DEFAULT_TIMEOUT); // milliseconds
        target.setRetries(DEFAULT_RETRY);
        return target;
    }
  
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            SnmpTrapSendDemo demo = new SnmpTrapSendDemo();
            demo.init();
            demo.sendPDU();
        } catch (IOException e) {
            e.printStackTrace();
        }
  
    }

}
