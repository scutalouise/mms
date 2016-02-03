
package com.agama.pemm.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.authority.service.impl.AreaInfoServiceImpl;
import com.agama.common.domain.StateEnum;
import com.agama.pemm.bean.DeviceStateRecord;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.utils.SNMPUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
public class DeviceServiceTest {
	@Autowired
	private AreaInfoServiceImpl areaInfoService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IGitInfoService gitInfoService;
	@Test
	public void recursiveByPid(){
	
		System.out.println(areaInfoService.getAreaInfoIdStrById(1));
	}
	@Test
	public void  getDeviceStateRecordByAreaInfoId(){
		List<DeviceStateRecord> deviceStateRecords=deviceService.getDeviceStateRecordByOrganizationId(null);
		for (DeviceStateRecord deviceStateRecord : deviceStateRecords) {
			System.out.println(deviceStateRecord.getCount());
		}
	}
	@Test
	public void updateCurrentStateById(){
		deviceService.updateCurrentStateById(73, StateEnum.error);
	}
	@Test
	public void scan(){
		List<GitInfo> gitInfoList = gitInfoService.getListByStatus(0);
		SNMPUtil snmp = new SNMPUtil();
		for (GitInfo gitInfo : gitInfoList) {
			try {
				Map<String, String> resultMap = snmp.walkByGetNext(
						gitInfo.getIp(), "1.3.6.1.4.1.34651.2");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
