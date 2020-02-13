package com.imh.springboot.basics.springbootin10steps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

//For web application
@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = SpringApplication.run(WebApplication.class, args);

		/*
		//Loop around the applicationContext and prints the name of all the beans which are configured
		for (String name: applicationContext.getBeanDefinitionNames()) {
			System.out.println(name);
		}*/
	}

}