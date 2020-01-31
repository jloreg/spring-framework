package com.imh.spring.basics.springindepth;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.imh.spring.basics.springindepth.xml.XmlPersonDAO;

public class SpringXmlContextApplication {

	public static void main(String[] args) {
		
		try (ClassPathXmlApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("applicationContext.xml")) {
			
			XmlPersonDAO personDao = applicationContext.getBean(XmlPersonDAO.class);
			System.out.println(personDao);
			//To make sure that it's auto-wired properly.
			System.out.println(personDao.getXmlJdbcConnection());
			
		}
	}
}