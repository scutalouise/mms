package com.agama.authority.ddl;

import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

public class GenerateDdl {
	LocalSessionFactoryBean sf = null;

	@Before
	public void initial() {
		String[] locations = { "classpath*:applicationContext.xml" };
		ApplicationContext ctx = new ClassPathXmlApplicationContext(locations);
		System.out.println(ctx.getBean("&sessionFactory"));
		sf = (LocalSessionFactoryBean) ctx.getBean("&sessionFactory");
		System.out.println(ctx);
	}

	@Test
	public void generate() {
		SchemaExport dbExport = new SchemaExport(sf.getConfiguration());
		dbExport.setFormat(true);
		// dbExport.setOutputFile("c:\\test.sql");
		dbExport.create(true, false);
	}

}
