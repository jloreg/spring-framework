
package com.imh.spring.aop.springaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect			//AOP
@Configuration	//Configuration
public class AfterAccessAspect {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

//	@AfterReturning(value = "execution(* com.imh.spring.aop.springaop.business.*.*(..))",	//Pointcut: old version
//			returning = "result")															//Pointcut: efficient version
	@AfterReturning(value = "com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution()",
			returning = "result")
	public void afterReturning (JoinPoint joinPoint, Object result){	//take the result and put it into this argument name "result"
		logger.info("{} returned with value {}", joinPoint, result);	//Print the interceptive calls
	}	

//	@AfterThrowing(
//			value = "execution(* com.imh.spring.aop.springaop.business.*.*(..))",			//Pointcut: old version
//			throwing = "exception")
	@AfterThrowing(
	value = "com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution()",
	throwing = "exception")
	public void afterThrowing (JoinPoint joinPoint, Object exception){
		logger.info("{} throw exception {}", joinPoint, exception);	//Print the interceptive calls
	}
	
//	@After(value = "execution(* com.imh.spring.aop.springaop.business.*.*(..))")			//Pointcut: old version
	@After(value = "com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution()")
	public void after(JoinPoint joinPoint){
		logger.info("after execution of {}", joinPoint);
	}

}