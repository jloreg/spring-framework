package com.imh.spring.aop.springaop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect			//AOP
@Configuration	//Configuration
public class CommonJoinPointConfig {
	
	//Be careful about the closing bracket when defining pointcuts: (){};
	
	@Pointcut("execution(* com.imh.spring.aop.springaop.data.*.*(..))")
	public void dataLayerExecution() {};

	@Pointcut("execution(* com.imh.spring.aop.springaop.business.*.*(..))")
	public void businessLayerExecution() {};
	
//	@Pointcut("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution")
//	public void allLayerExecution(){};
	
	@Pointcut("dataLayerExecution() && businessLayerExecution()")
	public void allLayerExecution(){};
	
	@Pointcut("bean(*Dao*)")
	public void beanContainingDao(){};
	
	@Pointcut("within(com.in28minutes.spring.aop.springaop.data..*)")
	public void dataLayerExecutionWithWithin(){};
}