## Step 07 - Best Practice : Use common Pointcut Configuration

In the previous step we defined @Before and @After around advice. We saw that we had to repeat the pointcut got multiple number of times, due to that the execution of this com.imh.spring.aop.springaop.business.*.* is present here in MethodExecutionCalculationAspect, and in other places as UserAccessAspect and AfterAopAspect as well too. 

```java
@Aspect			//AOP
@Configuration	//Configuration
public class MethodExecutionCalculationAspect {
		
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution(* com.imh.spring.aop.springaop.business.*.*(..))")	//Pointcut which needs to be used to intercept
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
```

Imagine a big project where these kind of pointcuts that repeat again and again, we don't really. What to do ? in this step we are going to look at best practices related to AOP which is to have a separate file where you define all the pointcuts. So ho do we do that ? that's what we would look at in this specific step.

```java
package com.imh.spring.aop.springaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect			//AOP
@Configuration	//Configuration
public class CommonJoinPointConfig {
		
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("execution(* com.imh.spring.aop.springaop.data.*.*(..))")
	public void dataLayerExecution() {};
}
```

What I did right now is we created a class called CommonJoinPointConfig where I created a data layer execution pointcut. So it says execution of the data layer. So any executionin the data layer, you try and intercept.

1:50
I'll do a copy qualified name of *dataLayerExecution()* and save this inside the UserAccessAspect.class instead of using this pointcut @Before("execution(* com.imh.spring.aop.springaop..*.*(..))"), I'll use this *@Around("com.in28minutes.spring.aop.springaop.aspect.CommonJoinPointConfig.trackTimeAnnotation()")*. So I'm giving the path to this method, where the pointcut is defined. So you would want to check if the user acces is checked on data layer, so let's run this and see if works as usual.

```java
package com.imh.spring.aop.springaop.aspect;

@Aspect			//AOP
@Configuration	//Configuration
public class UserAccessAspect {
		
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Before("execution(* com.imh.spring.aop.springaop..*.*(..))")								//Pointcut: old version
	@Before("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.dataLayerExecution()")	//Pointcut: efficient version
	public void before(JoinPoint joinPoint){
		//Advice
		//#1 - Implement the check for user access here. 
		logger.info(" Check for user access ");
		//#2 - What to do if everything is successful ?
		logger.info(" Allowed execution for {}", joinPoint);	//Print the interceptive calls
	}
}
```

02:21

Now I can reuse these great thing "com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.dataLayerExecution()" defined in UserAccesAspect and use it everywhere. So now I can use wherever I'm using data at execution, I can replace it with this *"com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.dataLayerExecution()"*. 

So now what I would do is I would define an aspect, a common aspect for the business layer execution as well, and replace it wherever I'm using business at execution.

```java
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
```

03:20
So all the pointcuts for your data layer and business layer, and any other ideas you have must be placed in here in the CommonJoinPointConfig class. You can define them in this common file. And that would help yohu in keeping all the pointcuts at a single place.

03:42
So now if you did, you would see that not everything would execute properly without any exception at all. This executed fine.

```java
020-02-18 11:44:23.429[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.a.springaop.SpringAopApplication  [0;39m [2m:[0;39m Started SpringAopApplication in 0.727 seconds (JVM running for 1.348)
[2m2020-02-18 11:44:23.437[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$f5f3e05f[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 11:44:23.438[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$f5f3e05f[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao1.retrieveSomething())
[2m2020-02-18 11:44:23.441[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business1  [0;39m [2m:[0;39m In Business - Dao1
[2m2020-02-18 11:44:23.442[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mnAspect$$EnhancerBySpringCGLIB$$dedf2382[0;39m [2m:[0;39m Time taken by execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething()) is 11
[2m2020-02-18 11:44:23.442[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c15a822[0;39m [2m:[0;39m after execution of execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething())
[2m2020-02-18 11:44:23.442[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c15a822[0;39m [2m:[0;39m execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething()) returned with value null
[2m2020-02-18 11:44:23.442[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$1a7dc89b[0;39m [2m:[0;39m null
[2m2020-02-18 11:44:23.445[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$f5f3e05f[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-18 11:44:23.445[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$f5f3e05f[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao2.retrieveSomething())
[2m2020-02-18 11:44:23.448[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business2  [0;39m [2m:[0;39m In Business - Dao2
[2m2020-02-18 11:44:23.448[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mnAspect$$EnhancerBySpringCGLIB$$dedf2382[0;39m [2m:[0;39m Time taken by execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething()) is 6
[2m2020-02-18 11:44:23.448[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c15a822[0;39m [2m:[0;39m after execution of execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething())
[2m2020-02-18 11:44:23.448[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c15a822[0;39m [2m:[0;39m execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething()) returned with value null
[2m2020-02-18 11:44:23.448[0;39m [32m INFO[0;39m [35m5656[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$1a7dc89b[0;39m [2m:[0;39m null
```

We looked at one of the best AOP practices, and they all have a coomon file CommonJoinPointConfig class for having all your pointcuts.

## Complete Code Example

##### /src/main/java/com/imh/spring/aop/springaop/aspect/CommonJoinPointConfig.java

```java

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
```

---

##### /src/main/java/com/imh/spring/aop/springaop/aspect/UserAccessAspect.java

```java

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
	
//	@Before("execution(* com.imh.spring.aop.springaop..*.*(..))")								//Pointcut: old version
	@Before("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.dataLayerExecution()")	//Pointcut: efficient version
	public void before(JoinPoint joinPoint){
		//Advice
		//#1 - Implement the check for user access here. 
		logger.info(" Check for user access ");
		//#2 - What to do if everything is successful ?
		logger.info(" Allowed execution for {}", joinPoint);	//Print the interceptive calls
	}
}
```

---

##### /src/main/java/com/imh/spring/aop/springaop/aspect/MethodExecutionCalculationAspect.java

```java

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
```

---

##### /src/main/java/com/imh/spring/aop/springaop/aspect/AfterAccessAspect.java

```java

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
```