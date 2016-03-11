package com.agama.authority;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.authority.entity.Organization;
import com.agama.authority.entity.User;
import com.agama.authority.service.IOrganizationService;
import com.agama.authority.service.IUserService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml"})
public class OrganizationServiceTest {
	@Autowired
	private IUserService organizationService;
	@Test
	public void test(){ 
		User o=organizationService.get(1);
		System.out.println(o);
	}

}
