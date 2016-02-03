package com.agama.device.ddl;

import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:applicationContext.xml"})
public class GenerateDdl {
	

	

	//	@Before
//	public void initial() {
//		String[] locations = { "classpath*:applicationContext.xml" };
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(locations);
//		System.out.println(ctx.getBean("&sessionFactory"));
//		sf = (LocalSessionFactoryBean) ctx.getBean("&sessionFactory");
//		System.out.println(ctx);
//	}

	@Test
	public void generate() {
//		LocalSessionFactoryBean sf = (LocalSessionFactoryBean)sessionFactory;
//		SchemaExport dbExport = new SchemaExport(sf.getConfiguration());
//		dbExport.setFormat(true);
//		// dbExport.setOutputFile("c:\\test.sql");
//		dbExport.create(true, false);
	}

}
