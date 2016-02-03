package com.agama.assets;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/itam-applicationContext.xml" })
public class TestDataSource {

	// @Autowired
	// private DataSource dataSource;

	// @Test
	public void testDB() {
		ApplicationContext cpa = new ClassPathXmlApplicationContext("/itam-applicationContext.xml");
		DataSource dataSource = (DataSource) cpa.getBean("dataSource");
		try {
			System.out.println("fsdfsdfsdfsdfsdf:" + dataSource.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
