package com.imh.spring.basics.springindepth;

import com.imh.spring.basics.springindepth.cdi.SomeCdiBusiness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class CdiApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(CdiApplication.class);

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				CdiApplication.class)) {
			
			SomeCdiBusiness business = applicationContext.getBean(SomeCdiBusiness.class);

			LOGGER.info("{} dao-{}", business, business.getSomeCDIDAO());
		}
	}
}