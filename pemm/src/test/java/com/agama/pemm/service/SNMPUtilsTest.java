package com.agama.pemm.service;

import org.junit.Test;

import com.agama.pemm.utils.SNMPUtil;

public class SNMPUtilsTest {
	@Test
	public void test(){
		SNMPUtil snmpUtil=new SNMPUtil();
		try {
			long start=System.currentTimeMillis();
			snmpUtil.sendSetCommand("192.168.2.22", "1.3.6.1.4.1.34651.2.3.1.10.1", 1);
			long end = System.currentTimeMillis();
			System.out.println("执行时间："+ (end - start) + "ms");
		} catch (Exception e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
