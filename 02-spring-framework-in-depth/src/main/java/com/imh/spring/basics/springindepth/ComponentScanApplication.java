package com.imh.spring.basics.springindepth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import componentscan.ComponentDAO;

@SpringBootApplication
@ComponentScan("componentscan")
public class ComponentScanApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(ComponentScanApplication.class);

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				ComponentScanApplication.class)) {
			ComponentDAO componentDAO = applicationContext.getBean(ComponentDAO.class);

			LOGGER.info("{}", componentDAO);
		}
	}
}