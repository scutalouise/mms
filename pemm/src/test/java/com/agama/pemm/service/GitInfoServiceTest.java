package com.agama.pemm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.pemm.domain.GitInfo;
import com.agama.pemm.service.IGitInfoService;
import com.agama.pemm.utils.SNMPUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
public class GitInfoServiceTest {
	private String ipAddress;
	@Autowired
	private IGitInfoService gitInfoService;
	@Test
	public void save(){
		GitInfo gitInfo=new GitInfo();
		gitInfo.setIp(ipAddress);
		gitInfo.setName("test");
		gitInfo.setBrand("test");
		gitInfo.setVendor("test");
		gitInfo.setEnabled(1);
		gitInfo.setStatus(1);
		gitInfo.setBuyTime(new Date());
		gitInfoService.save(gitInfo);
	}
	@Test
	public void search(){
		HttpServletRequest request=null;
		Page<GitInfo> page = new Page<GitInfo>();
		List<PropertyFilter> filters =new ArrayList<PropertyFilter>();
		page = gitInfoService.search(page, filters);
	}
	@Test
	public void get(){
		GitInfo g=new GitInfo();
//		g.setId(1);
		g=gitInfoService.get(1);
		System.out.println(g.getVendor());
	}
	public static void main(String[] args) throws Exception {
		SNMPUtil snmpUtil=new SNMPUtil();
		List<String> oids=new ArrayList<String>();
		oids.add("1.3.6.1.4.1.34651.2.1.1.1.1");
		snmpUtil.walkByGet("192.168.2.22", oids);
	}
	

}
