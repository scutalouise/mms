package com.agama.pemm.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.common.utils.DateUtils;
import com.agama.pemm.dao.IUpsStatusDao;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.protocol.snmp.UpsOidInfo;
import com.agama.pemm.service.IUpsStatusService;
import com.agama.pemm.utils.SNMPUtil;

@Service(value="upsStatusService")
public class UpsStatusServiceImpl extends BaseServiceImpl<UpsStatus, Long>implements IUpsStatusService {

	private Logger log = LoggerFactory.getLogger(UpsStatusServiceImpl.class);
	@Autowired
	@Qualifier("upsStatusDao")
	private IUpsStatusDao upsStatusDao;
	

	public UpsStatus collectUspStatus(String ipAaddress) {
		UpsStatus upsStatus = new UpsStatus();
		try {
			 SNMPUtil snmp=new SNMPUtil();
			Map<String, String> resultMap = snmp.walkByGetNext(ipAaddress,
					"1.3.6.1.4.1.34651.2.1.1");
			upsStatus.setName(resultMap.get(UpsOidInfo.upsName.getOid()));
			upsStatus.setInterfaceType(Integer.parseInt(resultMap
					.get(UpsOidInfo.interfaceType.getOid()).trim()));
			upsStatus.setCommunicationStatus(Integer.parseInt(resultMap
					.get(UpsOidInfo.communicationStatus.getOid()).trim()));
			upsStatus.setDischargePatterns(Integer.parseInt(resultMap
					.get(UpsOidInfo.dischargePatterns.getOid()).trim()));
			upsStatus.setUpsType(Integer.parseInt(resultMap
					.get(UpsOidInfo.upsType.getOid()).trim()));
			upsStatus.setModelNumber(resultMap.get(UpsOidInfo.modelNumber));
			upsStatus.setBrand(resultMap.get(UpsOidInfo.brand));
			upsStatus.setVersionNumber(resultMap.get(UpsOidInfo.versionNumber));
			upsStatus.setRateVoltage(Double.parseDouble(resultMap
					.get(UpsOidInfo.ratedVoltage.getOid()).trim()));
			upsStatus.setRatedCurrent(Double.parseDouble(resultMap
					.get(UpsOidInfo.ratedCurrent.getOid()).trim()));
			upsStatus.setBatteryVoltage(Double.parseDouble(resultMap
					.get(UpsOidInfo.batteryVoltage.getOid()).trim()));
			upsStatus.setPower(Double.parseDouble(resultMap
					.get(UpsOidInfo.power.getOid()).trim()));
			upsStatus.setUpsStatus(Integer.parseInt(resultMap
					.get(UpsOidInfo.upsStatus.getOid()).trim()));
			upsStatus.setFrequency(Double.parseDouble(resultMap
					.get(UpsOidInfo.frequency.getOid()).trim()));
			upsStatus.setInternalTemperature(Double.parseDouble(resultMap
					.get(UpsOidInfo.internalTemperature.getOid()).trim()));
			upsStatus.setBypassVoltage(Double.parseDouble(resultMap
					.get(UpsOidInfo.bypassVoltage.getOid()).trim()));
			upsStatus.setBypassFrequency(Double.parseDouble(resultMap
					.get(UpsOidInfo.bypassFrequency.getOid()).trim()));
			upsStatus.setInputVoltage(Double.parseDouble(resultMap
					.get(UpsOidInfo.inputVoltage.getOid()).trim()));
			upsStatus.setOutputVoltage(Double.parseDouble(resultMap
					.get(UpsOidInfo.outputVoltage.getOid()).trim()));
			upsStatus.setErrorVoltage(Double.parseDouble(resultMap
					.get(UpsOidInfo.errorVoltage.getOid()).trim()));
			upsStatus
					.setLoad(Double.parseDouble(resultMap.get(UpsOidInfo.load.getOid()).trim()));
			upsStatus.setOutputFrenquency(Double.parseDouble(resultMap
					.get(UpsOidInfo.outputFrenquency.getOid()).trim()));
			upsStatus.setSingleVoltage(Double.parseDouble(resultMap
					.get(UpsOidInfo.singleVoltage.getOid()).trim()));
			upsStatus.setTotalVoltage(Double.parseDouble(resultMap
					.get(UpsOidInfo.singleVoltage.getOid()).trim()));
			upsStatus.setElectricQuantity(Double.parseDouble(resultMap
					.get(UpsOidInfo.electricQuantity.getOid()).trim()));
			upsStatus.setPassCurrent(Double.parseDouble(resultMap
					.get(UpsOidInfo.passCurrent.getOid()).trim()));
			upsStatus.setRemainingTime(Integer.parseInt(resultMap
					.get(UpsOidInfo.remainingTime.getOid()).trim()));
			upsStatus.setCollectTime(DateUtils.parseDate(resultMap
					.get("collectTime")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return upsStatus;
	}

}
