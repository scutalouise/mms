package com.agama.das.quartz;

import java.util.List;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agama.das.helper.DataInteractHelper;
import com.agama.das.model.entity.Asset;
import com.agama.das.protocol.ProtocolExecutor;
import com.agama.das.service.AssetService;
import com.agama.tool.utils.PropertiesUtils;
import com.agama.tool.utils.date.DateUtils;

/**
 * @Description:用于制定任务调度器具体任务操作
 * @Author:佘朝军
 * @Since :2015年11月2日 下午2:21:02
 */
@Log4j
@Component
public class ExecuteTask {

	@Autowired
	private AssetService assetService;
	@Autowired
	private ProtocolExecutor protocolExecutor;
	@Autowired
	private DataInteractHelper dataInteractHelper;

	/**
	 * @Description:主机巡检的任务调度
	 * 
	 * @Since :2015年11月24日 下午3:05:26
	 */
	public void checkRunStatusForHost() {
		log.info("开始巡检主机。。。");
		List<Asset> assetsList = assetService.getAll();
		for (Asset asset : assetsList) {
			String protocol = asset.getProtocol().toString();
			switch (protocol) {
			case "SNMP":
				protocolExecutor.executeSNMP(asset);
				break;
			case "SSH":
				protocolExecutor.executeSSH(asset);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * @Description:用于定时与IT运维管理中心进行数据交互的任务
	 * 
	 * @Since :2015年11月26日 上午11:21:15
	 */
	public void dataInteractTask() {
		log.info("执行一次数据交互定时器。。。执行时间为：" + DateUtils.getDateTime());
		boolean independence = Boolean.parseBoolean(PropertiesUtils.getPropertiesValue("independence", "/local.properties"));
		dataInteractHelper.controlTask(independence);
	}


}
