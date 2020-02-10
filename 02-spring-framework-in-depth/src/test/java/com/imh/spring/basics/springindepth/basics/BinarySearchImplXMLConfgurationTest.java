package com.imh.spring.basics.springindepth.basics;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

//Load the Spring context
//Runner launch the configuration app SpringApplication.class; SpringRunner.class is part of the spring-test module.
@RunWith(SpringRunner.class)	
//Here we are using locations to specifying the XML configuration file for the the context.
@ContextConfiguration(locations="/testContext.xml")
public class BinarySearchImplXMLConfgurationTest {

	//Get this bean from the context (using autowiring).
	@Autowired
	BinarySearchImpl binarySearch;
	
	@Test
	public void testBasicScenario() {
		
		//call method on binarySearch
		int actualResult = binarySearch.binarySearch(new int[]{}, 5);
		//check if the result is the expected (first argument = expected value)
		assertEquals(3, actualResult);
	}
}