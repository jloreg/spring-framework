package com.imh.spring.aop.springaop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service	//@Service	is for Business layer
public class Business2 {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Dao2 dao2;

	public String calculateSomething(){
		//Business Logic
		String value = dao2.retrieveSomething();
		logger.info("In Business - {}", value);
		return value;
	}
}
