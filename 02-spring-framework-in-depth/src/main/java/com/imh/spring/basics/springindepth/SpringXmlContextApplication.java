package com.imh.spring.basics.springindepth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.imh.spring.basics.springindepth.xml.XmlPersonDAO;

//@Configuration and @ComponentScan annotations has been removed
public class SpringXmlContextApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringXmlContextApplication.class);
	
	public static void main(String[] args) {
		
		try (ClassPathXmlApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("applicationContext.xml")) {
			
			// [xmlJdbcConnection, xmlPersonDAO]
			LOGGER.info("Beans Loaded -> {}", (Object) applicationContext.getBeanDefinitionNames());
			
			XmlPersonDAO personDao = applicationContext.getBean(XmlPersonDAO.class);
			//To make sure that it's auto-wired properly.
			LOGGER.info("{} {}", personDao, personDao.getXmlJdbcConnection());
		}
	}
}