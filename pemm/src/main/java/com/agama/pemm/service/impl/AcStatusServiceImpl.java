package com.agama.pemm.service.impl;

import java.util.Map;

import org.snmp4j.Snmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agama.common.enumbean.AirConditioningRunState;
import com.agama.common.enumbean.AirConditioningUnitState;
import com.agama.common.service.impl.BaseServiceImpl;
import com.agama.pemm.dao.IAcConfigDao;
import com.agama.pemm.dao.IAcStatusDao;
import com.agama.pemm.dao.IDeviceDao;
import com.agama.pemm.domain.AcConfig;
import com.agama.pemm.domain.AcStatus;
import com.agama.pemm.domain.Device;
import com.agama.pemm.protocol.snmp.AcOidInfo;
import com.agama.pemm.service.IAcStatusService;
import com.agama.pemm.utils.SNMPUtil;
import com.agama.tool.utils.ConvertUtils;
import com.agama.tool.utils.date.DateUtils;
@Service
@Transactional
public class AcStatusServiceImpl extends BaseServiceImpl<AcStatus, Integer> implements IAcStatusService{
	private static final Integer MAX_INTERVAL = 20000;
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private IAcStatusDao acStatusDao;
	@Autowired
	private IAcConfigDao acConfigDao;
	@Override
	public void collectAcStatus(String ipAaddress, Integer index,
			Integer deviceId) {
		AcStatus acStatus=new AcStatus();
		SNMPUtil snmpUtil=new SNMPUtil();
		try {
			//获取空调信息
			Map<String,String> resultMap=snmpUtil.walkByGetNext(ipAaddress,
					"1.3.6.1.4.1.34651.2.7.1");
			if(resultMap.size()>0){
				String lastCollectIntervalStr=resultMap.get(AcOidInfo.LASTCOLLECTTNTERVAL.getOid()+"."+index);
				if(lastCollectIntervalStr!=null){
					Long lastCollectInterval = Long
							.parseLong(lastCollectIntervalStr.trim());
					if (lastCollectInterval < MAX_INTERVAL) {
						
						acStatus.setIndoorTemperature(Double.parseDouble(resultMap.get(AcOidInfo.INDOORTEMPERATURE.getOid()+"."+index).trim())/10);
						acStatus.setIndoorHumidity(Double.parseDouble(resultMap.get(AcOidInfo.INDOORHUMIDITY.getOid()+"."+index).trim())/10);
						acStatus.setOutdoorTemperature(Double.parseDouble(resultMap.get(AcOidInfo.OUTDOORTEMPERATURE.getOid()+"."+index).trim())/10);
					
						acStatus.setRunState(AirConditioningRunState.getAirConditioningRunStateByOrdinal(Integer.parseInt(resultMap.get(AcOidInfo.RUNSTATE.getOid()+"."+index).trim())));
						
						acStatus.setUnitState(AirConditioningUnitState.getAirConditioningUnitStateByOrdinal(Integer.parseInt(resultMap.get(AcOidInfo.UNITSTATE.getOid()+"."+index).trim())));
						if(resultMap.get(AcOidInfo.TYPENAME.getOid()+"."+index).trim().equals("house")){
							acStatus.setActionIndex(Integer.parseInt(resultMap.get(AcOidInfo.ACTIONINDEX.getOid()+"."+index).trim()));
	//						acStatus.setAlarmIndex(Integer.parseInt(resultMap.get(AcOidInfo.ALARMINDEX.getOid()+"."+index).trim()));
							System.out.println(ConvertUtils.change2Chinese(resultMap.get(AcOidInfo.ALARMNAME.getOid()+"."+index).trim()));
						}
						Device device = deviceDao.find(deviceId);
						acStatus.setDevice(device);
						acStatus.setCollectTime(DateUtils.parseDate(resultMap
								.get("collectTime")));
						acStatusDao.save(acStatus);
						//
						AcConfig acConfig=acConfigDao.findAcConfigByDeviceId(device.getId());
						if(acConfig!=null){
							//空调室内温度
							Double indoorTemperature=acStatus.getIndoorTemperature();
							//空调室内湿度
							Double indoorHumidity=acStatus.getIndoorHumidity();
							
							//温度没有在设置的范围内并且空调为关机状态控制空调开机 暂时去掉acStatus.getRunState()==AirConditioningRunState.SHUTDOWN条件
							if((indoorTemperature>acConfig.getMaxTemperature()||indoorTemperature<acConfig.getMinTemperature())){
								
								this.closeOrOpenOfAc(device.getGitInfo().getIp(), index,2);
							}
						
						}
						
						
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public boolean closeOrOpenOfAc(String ip,Integer index,Integer command){
		SNMPUtil snmpUtil=new SNMPUtil();
		try {
			snmpUtil.sendSetCommand(ip, AcOidInfo.RUNSTATE.getOid()+"."+index, command);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	
	public void saveS(Integer id){
		baseDao.delete(id);
		throw new RuntimeException("出现异常");
	}
	

}
