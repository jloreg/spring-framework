package com.imh.spring.basics.springindepth.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.imh.spring.basics.springindepth.scope.PersonDAO;

@SpringBootApplication
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//By default
public class SpringBootScopeApplication {

	//Logging with SLF4J Logger; because it's static, I would need to rename 'logger' to LOGGER 
	private static Logger LOGGER = 
			LoggerFactory.getLogger(SpringBootScopeApplication.class);

	public static void main(String[] args) {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringBootScopeApplication.class);

		//Create two instances of PersonDAO
		PersonDAO personDao = applicationContext.getBean(PersonDAO.class);
		PersonDAO personDao2 = applicationContext.getBean(PersonDAO.class);

		//"{}": this would replace whatever is in within the second argument (personDao, personDao.getJdbcConnection(), etc) 
		LOGGER.info("{}", personDao);
		LOGGER.info("{}", personDao.getJdbcConnection());	//Print Jdbc connection
		LOGGER.info("{}", personDao2);
		LOGGER.info("{}", personDao2.getJdbcConnection());
	}
}