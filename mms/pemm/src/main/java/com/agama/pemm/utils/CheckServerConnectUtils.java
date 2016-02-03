package com.agama.pemm.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CheckServerConnectUtils {
	private static final Logger logger = LoggerFactory.getLogger(CheckServerConnectUtils.class);

	private static final Long TIMEOUT=300L;
	public static boolean pingByServer(String ip) {
			 // 超时应该在3钞以上
		boolean canconnect = false;
		
		BufferedReader in = null;
		Runtime r = Runtime.getRuntime();
		String pingCommand = "";
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			pingCommand = "ping " + ip + " -n 1 -w " + TIMEOUT;
		} else {
			pingCommand = "ping -c 1 " + ip;
		}
		Process p = null;
		try {
			p = r.exec(pingCommand);
			if (p != null) {
				in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					if (line.toUpperCase().contains("TTL")) {
						canconnect = true;
						break;
					}
				}
			}

		} catch (Exception ex) {
			
			logger.error("存在网络问题,请检查系统,原因：" + ex.toString());
			canconnect = false;
		} finally {
			try {
				p.destroy();// 增加对Process的关闭
				in.close();
			} catch (IOException e) {
				logger.error("PING进程关闭时出现错误，原因：" + e.toString());
			}

		}
		return canconnect;
	}
	
}
