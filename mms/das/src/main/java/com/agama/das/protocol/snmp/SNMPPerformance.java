package com.agama.das.protocol.snmp;

import java.io.IOException;
import java.util.Map;

import lombok.Getter;

import org.snmp4j.mp.SnmpConstants;

import com.agama.das.model.SNMPTarget;
import com.agama.das.model.entity.Asset;
import com.agama.das.protocol.snmp.firewall.dp.DPSNMPExecutor;
import com.agama.das.protocol.snmp.host.linux.LinuxSNMPExecutor;
import com.agama.das.protocol.snmp.host.windows.WindowsSNMPExecutor;
import com.agama.das.protocol.snmp.switchboard.huawei.HuaWeiSNMPExecutor;
import com.agama.das.protocol.snmp.switchboard.maipu.MaipuSNMPExecutor;

/**
 * @Description:统一入口，按类型分散处理各类snmp通信
 * @Author:佘朝军
 * @Since :2015年11月2日 下午3:42:16
 */
@Getter
public class SNMPPerformance {

	private Map<String, Object> result;
	private SNMPExecutor snmpExecutor;

	/**
	 * 构造器，用于构造snmp展示类
	 * @param asset 资产设备对象
	 * @throws IOException IO异常
	 * @throws RuntimeException 运行异常
	 */
	public SNMPPerformance(Asset asset) throws IOException, RuntimeException {
		SNMPTarget snmpTarget = getSNMPTargetByAsset(asset);
		String assetsType = asset.getDeviceType();
		String secondType = asset.getSecondType();
		try {
			switch (assetsType) {
			case "HOSTDEVICE":
				getHostSNMP(snmpTarget, secondType);
				break;
			case "MIDDLEWARE":
				getMiddleWareSNMP(snmpTarget);
				break;
			case "ROUTER":
				getRouterSNMP(snmpTarget);
				break;
			case "SWITCHBOARD":
				getSwitchboardSNMP(snmpTarget, secondType);
				break;
			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			snmpExecutor.close();
		}
	}

	/**
	 * @Description:根据设备参数封装SNMP访问参数对象
	 * @param asset 资产设备对象
	 * @return SNMP访问参数封装对象
	 * @Since :2015年11月23日 上午11:34:32
	 */
	private SNMPTarget getSNMPTargetByAsset(Asset asset) {
		SNMPTarget snmpTarget = new SNMPTarget();
		snmpTarget.setPort(asset.getPort());
		snmpTarget.setTargetIp(asset.getAddress());
		int version = Integer.parseInt(asset.getProtocolVersion());
		snmpTarget.setSnmpVersion(version);
		if (SnmpConstants.version3 == version) {
			snmpTarget.setUser(asset.getUserName());
			snmpTarget.setPassword(asset.getPassword());
			snmpTarget.setContextEngineId(asset.getMetaData().get("contextEngineId").toString());
			snmpTarget.setReadCommunity("noAuthNoPriv");
		} else {
			snmpTarget.setReadCommunity(asset.getMetaData().get("community").toString());
		}
		return snmpTarget;
	}

	/**
	 * @Description:执行通过snmp获取主机数据的操作
	 * @param snmpTarget SNMP访问参数封装对象
	 * @param osType 操作系统类型
	 * @throws IOException IO异常
	 * @Since :2015年11月2日 下午5:27:04
	 */
	public void getHostSNMP(SNMPTarget snmpTarget, String osType) throws IOException {
		switch (osType) {
		case "LINUX":
			snmpExecutor = new LinuxSNMPExecutor(snmpTarget);
			result = snmpExecutor.getSNMPInfo(snmpTarget);
			break;
		case "WINDOWS":
			snmpExecutor = new WindowsSNMPExecutor(snmpTarget);
			result = snmpExecutor.getSNMPInfo(snmpTarget);
			break;
		case "AIX":
			// TODO
			break;
		default:
			break;
		}
	}

	/**
	 * @Description:执行通过snmp获取路由器数据的操作
	 * @param snmpTarget
	 * @Since :2015年11月2日 下午5:30:55
	 */
	public void getRouterSNMP(SNMPTarget snmpTarget) {
		// TODO
	}

	/**
	 * @Description:执行通过snmp获取交换机数据的操作
	 * @param snmpTarget 封装SNMP的访问参数
	 * @param secondType 设备第二级划分
	 * @throws IOException IO异常
	 * @Since :2015年11月2日 下午5:30:55
	 */
	public void getSwitchboardSNMP(SNMPTarget snmpTarget, String secondType) throws IOException {
		switch (secondType) {
		case "HUAWEI":
			snmpExecutor = new HuaWeiSNMPExecutor(snmpTarget);
			result = snmpExecutor.getSNMPInfo(snmpTarget);
			break;
		case "MAIPU":
			snmpExecutor = new MaipuSNMPExecutor(snmpTarget);
			result = snmpExecutor.getSNMPInfo(snmpTarget);
			break;
		default:
			break;
		}
	}

	/**
	 * @Description:执行通过snmp获取防火墙数据的操作
	 * @param snmpTarget 封装SNMP的访问参数
	 * @param secondType 设备第二级划分
	 * @throws IOException IO异常
	 * @Since :2016年1月4日 上午11:11:38
	 */
	public void getFirewallSNMP(SNMPTarget snmpTarget, String secondType) throws IOException {
		switch (secondType) {
		case "DP":
			snmpExecutor = new DPSNMPExecutor(snmpTarget);
			result = snmpExecutor.getSNMPInfo(snmpTarget);
			break;
		default:
			break;
		}
	}

	/**
	 * @Description:执行通过snmp获取中间件数据的操作
	 * @param snmpTarget
	 * @Since :2015年11月2日 下午5:30:55
	 */
	public void getMiddleWareSNMP(SNMPTarget snmpTarget) {
		// TODO
	}
}
