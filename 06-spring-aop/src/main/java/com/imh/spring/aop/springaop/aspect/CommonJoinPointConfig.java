package com.imh.spring.aop.springaop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

//https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop
//Be careful about the closing bracket when defining pointcuts: (){};

@Aspect			//AOP
@Configuration	//Configuration
public class CommonJoinPointConfig {
	
	@Pointcut("execution(* com.imh.spring.aop.springaop.data.*.*(..))")
	public void dataLayerExecution(){};

	@Pointcut("execution(* com.imh.spring.aop.springaop.business.*.*(..))")
	public void businessLayerExecution(){};
	
	//Works
	@Pointcut("execution(* com.imh.spring.aop.springaop.*.*.*(..))")
	public void allLayerExecution(){};
	
	/* Combine the joinPoints doesn't work with @Around annotation, see MethodExecutionCalculationAspect class
	@Pointcut("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.dataLayerExecution() && com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution()")	
	public void allLayerExecution(){};
	
	@Pointcut("dataLayerExecution() && businessLayerExecution()")
	public void allLayerExecution(){};
	*/

	@Pointcut("bean(*dao*)")
	public void beanContainingDao(){};
	
	@Pointcut("within(com.imh.spring.aop.springaop.data..*)")
	public void dataLayerExecutionWithWithin(){};
	
	@Pointcut("@annotation(com.imh.spring.aop.springaop.aspect.TrackTime)")
	public void trackTimeAnnotation(){}

}