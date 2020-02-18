## Step 06 - Using @Around advice to implement performance tracing

In the previous step we looked @Before and @After annotations to do the intersections. In this step, we look at something called an @Around advice, which is used to do more advanced stuff with Aspected Oriented Programming (AOP).

What we would want to do here is, we would want to be able to use an annotation called @Around advice to find the timing, and know how much time is a method execution taking. Once you have the around advice, I can define the pointcut to track the business executions. You don't have a result, because we would use something called *ProceedingJoinPoint*; this would allow you to continue with the exception of the method. So I would intercept it, I would allow the method to proceed, and then find the time taken, so what I want to do to find out the method execution time in here is I'll say:

```java
@Around("execution(*com.imh.spring.aop.springaop.business.*.*(..))")	//Pointcut which needs to be used to intercept
public void afterReturning (ProceedingJoinPoint joinPoint){
	logger.info("{} returned with value {}", joinPoint);	//Print the interceptive calls
	//startTime = x
	//allow execution of method
	//end Time = y
}
```

The way I can ask the method to continue execution is by saying joinPoint.proceed();. So the method would execute now, this would throw an exception. I can actually  surround with the 

```java
@Around("execution(* com.imh.spring.aop.springaop.business.*.*(..))")	//Pointcut which needs to be used to intercept
public void around(ProceedingJoinPoint joinPoint) throws Throwable{
		
	long startTime = System.currentTimeMillis();		 		 //startTime = x
	joinPoint.proceed();										 //allow execution of method
	long timeTaken = System.currentTimeMillis() - startTime;	//end Time = y
	logger.info("Time taken by {} is {}", joinPoint, timeTaken); //Print the interceptive calls
}
```

Let's quickly look at what we are doing. So we are using the pointcut to intercepting business methods using around advice so that we can intercept the call of a method and execute it. And then after the execution, do something, so before the execution I can do something, after the execution I can do something. So this is much more flexible than the @Before and @After advices because here I can actually do something arounD the invocation, so before the invocation I'm doing something, I continue the execution, and after the execution I'm doing something.

```java
@Around("execution(* com.imh.spring.aop.springaop.business.*.*(..))")	//Pointcut which needs to be used to intercept
public void around(ProceedingJoinPoint joinPoint) throws Throwable{
	
	//Before the invocation: startTime = x
	long startTime = System.currentTimeMillis();
	
	//I continue the execution: allow execution of method
	joinPoint.proceed();
	
	//After the invocation: end Time = y 
	long timeTaken = System.currentTimeMillis() - startTime;
	logger.info("Time taken by {} is {}", joinPoint, timeTaken);
}
```
Let's run the application and see how much time takes it.

```
Console output:
[2m2020-02-18 08:20:37.359[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.a.springaop.SpringAopApplication  [0;39m [2m:[0;39m Started SpringAopApplication in 0.776 seconds (JVM running for 1.194)
[2m2020-02-18 08:20:37.361[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$e869e331[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 08:20:37.361[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$e869e331[0;39m [2m:[0;39m  Allowed execution for execution(void com.imh.spring.aop.springaop.SpringAopApplication.run(String[]))
[2m2020-02-18 08:20:37.368[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$e869e331[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 08:20:37.368[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$e869e331[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething())
[2m2020-02-18 08:20:37.371[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$e869e331[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 08:20:37.372[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$e869e331[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao1.retrieveSomething())
[2m2020-02-18 08:20:37.375[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business1  [0;39m [2m:[0;39m In Business - Dao1
[2m2020-02-18 08:20:37.375[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mnAspect$$EnhancerBySpringCGLIB$$d1552654[0;39m [2m:[0;39m Time taken by execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething()) is 7
[2m2020-02-18 08:20:37.375[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$3e8baaf4[0;39m [2m:[0;39m after execution of execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething())
[2m2020-02-18 08:20:37.375[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$3e8baaf4[0;39m [2m:[0;39m execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething()) returned with value null
[2m2020-02-18 08:20:37.376[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mlication$$EnhancerBySpringCGLIB$$cf3cb6d[0;39m [2m:[0;39m null
[2m2020-02-18 08:20:37.376[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$e869e331[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 08:20:37.376[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$e869e331[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething())
[2m2020-02-18 08:20:37.379[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$e869e331[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 08:20:37.379[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$e869e331[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao2.retrieveSomething())
[2m2020-02-18 08:20:37.382[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business2  [0;39m [2m:[0;39m In Business - Dao2
[2m2020-02-18 08:20:37.382[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mnAspect$$EnhancerBySpringCGLIB$$d1552654[0;39m [2m:[0;39m Time taken by execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething()) is 6
[2m2020-02-18 08:20:37.382[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$3e8baaf4[0;39m [2m:[0;39m after execution of execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething())
[2m2020-02-18 08:20:37.382[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$3e8baaf4[0;39m [2m:[0;39m execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething()) returned with value null
[2m2020-02-18 08:20:37.382[0;39m [32m INFO[0;39m [35m7422[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mlication$$EnhancerBySpringCGLIB$$cf3cb6d[0;39m [2m:[0;39m null
```
So all that stuff is now being printed out, so you can find out how much time a method is taking to execute. So here you can expand the aspect, so if you want to also track data then you can just see execution(* com.imh.spring.aop.springaop..*.*(..)), and it would track all the things which are executed inside this thing *com.imh.spring.aop.springaop..*.**. You can play it around on the pointcut and try and understand this specific thing much more.

In this step we looked @Around advice, we looked at intercepting a method allowing to proceed doing something before if and after it. This is much more powerful advice than all the other stuff which we looked at. So this would end the different kinds of aspect that are present with Spring Aop and AspectJ in general. So, the things that we looked are @Before, @After, @AfterReturning, @Afterhrowing, and around these are the different kinds of advices which are presentin any typical AOP framework.

## Complete Code Example

##### /src/main/java/com/imh/spring/aop/springaop/SpringAopApplication.java

```java
package com.imh.spring.aop.springaop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.imh.spring.aop.springaop.business.Business1;
import com.imh.spring.aop.springaop.business.Business2;

@SpringBootApplication
public class SpringAopApplication implements CommandLineRunner{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Business1 business1;

	@Autowired
	private Business2 business2;

	public static void main(String[] args) {
		try {
			SpringApplication.run(SpringAopApplication.class, args);
		}
		catch (Exception e) {
			e.toString();
		}
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(business1.calculateSomething());
		logger.info(business2.calculateSomething());
	}
}
```

---

##### ##### /src/main/java/com/imh/spring/aop/springaop/aspect/MethodExecutionCalculationAspect.java

```java
package com.imh.spring.aop.springaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect			//AOP
@Configuration	//Configuration
public class MethodExecutionCalculationAspect {
		
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution(* com.imh.spring.aop.springaop.business.*.*(..))")	//Pointcut which needs to be used to intercept
	public void around(ProceedingJoinPoint joinPoint) throws Throwable{
		
		long startTime = System.currentTimeMillis();		 		 //startTime = x
		joinPoint.proceed();										 //allow execution of method
		long timeTaken = System.currentTimeMillis() - startTime;	//end Time = y
		logger.info("Time taken by {} is {}", joinPoint, timeTaken); //Print the interceptive calls
	}
}
```