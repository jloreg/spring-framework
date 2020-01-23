package com.imh.spring.basics.springin10steps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	
	//What are the beans ?
	//What are the dependencies of a bean ?
	//Where to search for beans ? => No need
	
	public static void main(String[] args) {
		
		//BinarySearchImpl binarySearchImpl = new BinarySearchImpl(new QuickSortAlgorithm());
		
		//Srping Application Context
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
		int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
	}
}