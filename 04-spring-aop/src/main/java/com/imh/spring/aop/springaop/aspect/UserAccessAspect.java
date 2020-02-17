package com.imh.spring.aop.springaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect			//AOP
@Configuration	//Configuration
public class UserAccessAspect {
		
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//What kind of method calls I would intercept
	//execution(* PACKAGE.*.*(..)) --> the type of return to be processed + darkspace + the name of the package + the name of the class .* + the name of the method .* + the argument of the method (..) 
	//Weaving & Weaver
	@Before("execution(* com.imh.spring.aop.springaop..*.*(..))")	//Pointcut
	public void before(JoinPoint joinPoint){
		//Advice
		//#1 - Implement the check for user access here. 
		logger.info(" Check for user access ");
		//#2 - What to do if everything is successful ?
		logger.info(" Allowed execution for {}", joinPoint);	//Print the interceptive calls
	}
}