package com.imh.spring.basics.springindepth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.imh.spring.basics.springindepth.basics.BinarySearchImpl;

@SpringBootApplication
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class Application {
	
	public static void main(String[] args) {
		
		//Spring Application Context
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
		BinarySearchImpl binarySearch1 = applicationContext.getBean(BinarySearchImpl.class);
		System.out.println(binarySearch);
		System.out.println(binarySearch1);

		int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
	}
}