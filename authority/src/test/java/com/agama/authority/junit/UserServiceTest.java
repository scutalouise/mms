package com.agama.authority.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agama.authority.entity.User;
import com.agama.authority.service.IUserService;
import com.agama.common.dao.utils.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
public class UserServiceTest {

	@Autowired
	private IUserService userService;
	
	@Test
	public void testGetUsersByAeraInfoId() {
		int areaInfoId = 7;
		int pageNo=1;	//当前页码
		int pageSize=20;	//每页行数
		String orderBy="id";	//排序字段
		String order="asc";	//排序顺序
		Page<User> page = new Page<User>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrderBy(orderBy);
		page.setOrder(order);
		
//		page =  userService.getUsersByAreaInfoId(page, areaInfoId);
//		System.out.println(userService);
	}

}
