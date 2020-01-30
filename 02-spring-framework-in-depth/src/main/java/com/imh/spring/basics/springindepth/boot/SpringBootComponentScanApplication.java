package com.imh.spring.basics.springindepth.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import componentscan.ComponentDAO;

@SpringBootApplication
@ComponentScan("componentscan")
public class SpringBootComponentScanApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringBootComponentScanApplication.class);

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringBootComponentScanApplication.class)) {
			ComponentDAO componentDAO = applicationContext.getBean(ComponentDAO.class);

			LOGGER.info("{}", componentDAO);
		}
	}
}