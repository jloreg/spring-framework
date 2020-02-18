package com.imh.spring.aop.springaop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect			//AOP
@Configuration	//Configuration
public class CommonJoinPointConfig {
	
	@Pointcut("execution(* com.imh.spring.aop.springaop.data.*.*(..))")
	public void dataLayerExecution() {};

	@Pointcut("execution(* com.imh.spring.aop.springaop.business.*.*(..))")
	public void businessLayerExecution() {};
}