
package com.imh.spring.aop.springaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect			//AOP
@Configuration	//Configuration
public class MethodExecutionCalculationAspect {
		
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Around("execution(* com.imh.spring.aop.springaop.business.*.*(..))")							//Pointcut: old version
	@Around("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution()")	//Pointcut: efficient version
	public void around(ProceedingJoinPoint joinPoint) throws Throwable{
		
		//Before the invocation: startTime = x
		long startTime = System.currentTimeMillis();
		
		//I continue the execution: allow execution of method
		joinPoint.proceed();
		
		//After the invocation: end Time = y
		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Time taken by {} is {}", joinPoint, timeTaken);	//Print the interceptive calls
	}
}