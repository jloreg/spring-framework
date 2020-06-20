## Step 05 - Using @After, @AfterReturning, @AfterThrowing advices

In the previous step we implemented a @Before Aspect. In this step we look at something called @After Aspect. 

If you want to do something after the value is returned , then we can use something called @AfterReturning, and I would intercept the business calls only *@AfterReturning("execution(* com.imh.spring.aop.springaop.business.*.*(..))")*, and after returning actually I can get the return value also. 

So I would need to configure it *@AfterReturning("execution(*com.imh.spring.aop.springaop.business.*.*(..))")* with one more argument so what we'll do is we'll set value = execution(# com.imh.spring.aop.springaop.business.#.#(..)), and returning = "result"; you can put the name of the argument, so where the value will come into, so this is actually it would be *Object result*,because we don't know the type. So returning = "result", what would happen is the value which is return, will be mapped to this *Object result*, and the second argument I would want to pass in *logger.info("{} returned with value {}", joinPoint, result);* is that the result

```java 
	@AfterReturning(value = "execution(* com.imh.spring.aop.springaop.business.*.*(..))",	//Pointcut which needs to be used to intercept
			returning = "result")	
	public void afterReturning (JoinPoint joinPoint, Object result){	//take the result and put it into this argument name "result"
		logger.info("{} returned with value {}", joinPoint, result);	//Print the interceptive calls
	}
```
	
So what we are doing here @AfterReturning is very simple. We are saying that value is equal to the pointcut which needs to be used to intercept, and I would want to take the result *returning = "result"* and put it into this argument  *Object result* named result, so this is the name of the argument in, and using logger.info to print the result.

Let's run this.
```
console output:
[2m2020-02-18 06:11:54.246[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.a.springaop.SpringAopApplication  [0;39m [2m:[0;39m Started SpringAopApplication in 0.906 seconds (JVM running for 1.654)
[2m2020-02-18 06:11:54.248[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b01fba4c[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 06:11:54.249[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b01fba4c[0;39m [2m:[0;39m  Allowed execution for execution(void com.imh.spring.aop.springaop.SpringAopApplication.run(String[]))
[2m2020-02-18 06:11:54.256[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b01fba4c[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 06:11:54.256[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b01fba4c[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething())
[2m2020-02-18 06:11:54.259[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b01fba4c[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 06:11:54.260[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b01fba4c[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao1.retrieveSomething())
[2m2020-02-18 06:11:54.263[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business1  [0;39m [2m:[0;39m In Business - Dao1
[2m2020-02-18 06:11:54.263[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mssAspect$$EnhancerBySpringCGLIB$$641820f[0;39m [2m:[0;39m execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething()) returned with value Dao1
[2m2020-02-18 06:11:54.263[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$d4a9a288[0;39m [2m:[0;39m Dao1
[2m2020-02-18 06:11:54.263[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b01fba4c[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 06:11:54.263[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b01fba4c[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething())
[2m2020-02-18 06:11:54.266[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b01fba4c[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 06:11:54.266[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b01fba4c[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao2.retrieveSomething())
[2m2020-02-18 06:11:54.268[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business2  [0;39m [2m:[0;39m In Business - Dao2
[2m2020-02-18 06:11:54.269[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mssAspect$$EnhancerBySpringCGLIB$$641820f[0;39m [2m:[0;39m execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething()) returned with value Dao2
[2m2020-02-18 06:11:54.269[0;39m [32m INFO[0;39m [35m3720[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$d4a9a288[0;39m [2m:[0;39m Dao2
```

You can see that *String com.imh.spring.aop.springaop.business.Business1.calculateSomething()) returned with value Dao1* is being printed, so calculateSomething() returned with value Dao1. So we are intercepting the call after it has completed, so after execution it's returning the value dao1. So we're printing after returning. So the thing is @AfterReturning get executed only when the execution gets completed successfully. 

So if you want to intercept an exception, then you can use another one called @AfterThrowing, this would intercept any exceptions that are thrown. With throwing I can get the result of the exception and say it *throwing = "exception"*, and add an argument *Object exception*; go to the Declaration of @AfterThrowing to see the arguments that can be used.

```java
@AfterThrowing(
		value = "execution(* com.imh.spring.aop.springaop.business.*.*(..))",
		throwing = "exception")
public void afterThrowing (JoinPoint joinPoint, Object exception){
	logger.info("{} throw exception {}", joinPoint, exception);	//Print the interceptive calls
}
```
The last kind of After interception is the common one. So whether it's throwing an exception or returning a value, you can use the after annotation to intercept that. If you run it you'll see that this also gets executed.

```java
@After(value = "execution(* com.imh.spring.aop.springaop.business.*.*(..))")
public void after(JoinPoint joinPoint){
	logger.info("after execution of {}", joinPoint);
}
```

One of the important things that we discuss in this video are,what are the things you can do with an @After aspect. So we used @AfterReturning, @AfterThrowing, @After annotations to define different methods for doing something **after a method is called**. The @AfterReturning is called **when a method is executes it successfully**, the @AfterThrowing is called **when a method thrown an exception**, @After is called **in both these scenarios** whether it's throwing an exception or returning a value.
 
## Complete Code Example

##### /src/main/java/com/imh/spring/aop/springaop/aspect/AfterAccessAspect.java

```java
package com.imh.spring.aop.springaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect			//AOP
@Configuration	//Configuration
public class AfterAccessAspect {
		
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@AfterReturning(value = "execution(* com.imh.spring.aop.springaop.business.*.*(..))",	//Pointcut which needs to be used to intercept
			returning = "result")	
	public void afterReturning (JoinPoint joinPoint, String result){	//take the result and put it into this argument name "result"
		logger.info("{} returned with value {}", joinPoint, result);	//Print the interceptive calls
	}
	

	@AfterThrowing(
			value = "execution(* com.imh.spring.aop.springaop.business.*.*(..))",
			throwing = "exception")
	public void afterThrowing (JoinPoint joinPoint, Object exception){
		logger.info("{} throw exception {}", joinPoint, exception);	//Print the interceptive calls
	}
	
	@After(value = "execution(* com.imh.spring.aop.springaop.business.*.*(..))")
	public void after(JoinPoint joinPoint){
		logger.info("after execution of {}", joinPoint);
	}

}
```