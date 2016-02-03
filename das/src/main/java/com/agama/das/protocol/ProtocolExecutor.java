package com.agama.das.protocol;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agama.das.model.entity.Asset;
import com.agama.das.model.entity.InspectedResult;
import com.agama.das.protocol.snmp.SNMPPerformance;
import com.agama.das.service.InspectedResultService;

/**
 * @Description:
 * @Author:佘朝军
 * @Since :2015年11月23日 上午10:56:14
 */
@Log4j
@Component
public class ProtocolExecutor {

	@Autowired
	private InspectedResultService inspectedResultService;

	public void executeSNMP(Asset asset) {
		InspectedResult ir = new InspectedResult();
		ir.setCheckTime(new Date());
		ir.setIdentifier(asset.getIdentifier());
		ir.setDeviceType(asset.getDeviceType());
		SNMPPerformance snmp = null;
		try {
			snmp = new SNMPPerformance(asset);
			Map<String, Object> result = snmp.getResult();
			ir.setDataStatus(true);
			ir.setDataInfo(result);
		} catch (IOException e) {
			log.error("获取SNMP数据失败！", e);
			ir.setDataStatus(false);
			ir.setErrorMessage("获取SNMP数据失败！" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.error("SNMP访问失败！可能由于SNMP连接信息不正确。" + e);
			ir.setDataStatus(false);
			ir.setErrorMessage("SNMP访问失败！可能由于SNMP连接信息不正确。");
			e.printStackTrace();
		}
		inspectedResultService.insert(ir);
	}

	public void executeSSH(Asset asset) {
		// TODO
	}

}
