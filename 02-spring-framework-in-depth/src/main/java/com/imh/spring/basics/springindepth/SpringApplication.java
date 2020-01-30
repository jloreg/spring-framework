package com.imh.spring.basics.springindepth;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.imh.spring.basics.springindepth.basics.BinarySearchImpl;

@Configuration
@ComponentScan("com.imh.spring.basics.springindepth")		//by default it's equivalent to @ComponentScan ("com.imh.spring.basics.springindepth")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class SpringApplication {

	public static void main(String[] args) {
		
		try (AnnotationConfigApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext(SpringApplication.class)) {
			
			BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
			BinarySearchImpl binarySearch1 = applicationContext.getBean(BinarySearchImpl.class);
			System.out.println(binarySearch);
			System.out.println(binarySearch1);

			int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
			System.out.println(result);
		}
	}
}