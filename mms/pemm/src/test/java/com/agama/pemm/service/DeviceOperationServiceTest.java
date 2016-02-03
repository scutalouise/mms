package com.agama.pemm.service;

import java.util.BitSet;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.pemm.utils.SNMPUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DeviceOperationServiceTest {
	@Autowired
	private IDeviceOperationService deviceOperationService;

	@Test
	public void upsDischarge() {
		deviceOperationService.upsOperation(5, "1.3.6.1.4.1.34651.2.1.1.60.1",
				1);

	}

	@Test
	public void lastCollectTime() {

		SNMPUtil snmpUtil = new SNMPUtil();
		try {
			Map<String, String> map = snmpUtil.walkByGetNext("192.168.2.22",
					"1.3.6.1.4.1.34651.2.1.1.99.1");
			for (Entry<String, String> entry : map.entrySet()) {
				System.out.println(entry.getKey() + "==" + entry.getValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private byte[] intToByteArray (final int integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros (integer < 0 ? ~integer : integer)) / 8;
		byte[] byteArray = new byte[4];

		for (int n = 0; n < byteNum; n++)
		byteArray[3 - n] = (byte) (integer >>> (n * 8));

		return (byteArray);
		}
	public static void main(String[] args) {
		int res=9;
		byte[] targets = new byte[4];
		targets[0] = (byte) (res & 0xff);// 最低位 
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位 
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位 
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。 
		System.out.println(byteToBit(targets[0]));
//		System.out.println(byteToBit(targets[0]).substring(7,8));
//		System.out.println(intToBit(40));
//		SNMPUtil snmpUtil = new SNMPUtil();
//		try {
//			Map<String, String> map = snmpUtil.walkByGetNext("192.168.2.22",
//					"1.3.6.1.4.1.34651.2.1.1");
//			System.out.println(map.get("1.3.6.1.4.1.34651.2.1.1.99.1"));
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


	}
	public static String intToBit(Integer  res){
		byte[] targets = new byte[4];
		targets[0] = (byte) (res & 0xff);// 最低位 	
		return ""+(byte)((targets[0] >> 7) & 0x1) + 
				 (byte)((targets[0] >> 6) & 0x1) + 
				 (byte)((targets[0] >> 5) & 0x1) + 
				 (byte)((targets[0] >> 4) & 0x1) + 
				 (byte)((targets[0] >> 3) & 0x1) + 
				 (byte)((targets[0] >> 2) & 0x1) + 
				 (byte)((targets[0] >> 1) & 0x1) + 
				 (byte)((targets[0] >> 0) & 0x1);
	}

	public static String byteToBit(byte b) {
		 return "" +(byte)((b >> 7) & 0x1) + 
		 (byte)((b >> 6) & 0x1) + 
		 (byte)((b >> 5) & 0x1) + 
		 (byte)((b >> 4) & 0x1) + 
		 (byte)((b >> 3) & 0x1) + 
		 (byte)((b >> 2) & 0x1) + 
		 (byte)((b >> 1) & 0x1) + 
		 (byte)((b >> 0) & 0x1);
		}

}
