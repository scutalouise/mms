package com.agama.tool.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**@Description:工具类，通过PING指令测试网络连通性
 * @Author:佘朝军
 * @Since :2015年12月10日 上午11:52:42
 */
public class PingUtils {
	
	/**
	 * @Description:通过ip地址执行ping命令，测试网络连通性
	 * @param ip
	 * @return boolean
	 * @Since :2015年12月10日 下午1:21:45
	 */
	public static boolean pingByIp(String ip){
		int timeOut = 3000; // 超时应该在3钞以上
		boolean canconnect = false;
		BufferedReader in = null;
		Runtime r = Runtime.getRuntime();
		String pingCommand = "";
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			pingCommand = "ping " + ip + " -n 1 -w " + timeOut;
		} else {
			pingCommand = "ping -c 1 " + ip;
		}
		Process p = null;
		try {
			p = r.exec(pingCommand);
			if (p == null) {
			} else {
				in = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					if (line.toUpperCase().contains("TTL")) {
						canconnect = true;
						break;
					}
				}
			}
		} catch (Exception ex) {
			canconnect = false;
			ex.printStackTrace();
		} finally {
			try { 
				p.destroy();// 增加对Process的关闭
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return canconnect;
	}

}
