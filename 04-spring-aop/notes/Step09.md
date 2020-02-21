## Step 09 - Creating Custom Annotation and an Aspect for Tracking Time 

In the previous steps we looked at how to define an @Around advice, and we implemented performance tracking using @Around advice. So what we did there was we implemented a MethodExecutionCalculationAspect class where we defined a Pointcut, and we intercepted all the methods which are defined by this Pointcut.

In this example we would want to actually have an annotation defined that we have to track the performance for that specific method *public String retrieveSomething* defined in Dao1 class. So instead of tracking everything @Around("execution(# com.imh.spring.aop.springaop.#.#.#(..))"), and instead of tracking all the methods in the business layer @Around("execution(# com.imh.spring.aop.springaop.business.#.#(..))"#), I would want to implement an annotation. So if you put an annotation on this dao method (see #1), then I would want to track the time for that. Let's implement that feature in this specific step.

The annotation I would wan to use is @TrackTime. So if somebody puts an annotation at @TrackTime on their method, then we would want to track the time for that. 

```java
package com.imh.spring.aop.springaop.data;

import org.springframework.stereotype.Repository;
import com.imh.spring.aop.springaop.aspect.TrackTime;

@Repository	//@Repository is something which talk's with the database; @Repository is for Data layer. 
public class Dao1 {

	/* Ideally, when you are naming your Dao methods, I prefer calling them retrieve instead of get. Retrieve is something which indicates that I'm trying 
	 * to retrieve from our database, or from my external thing. When I use get to represent something, it might be confused with a getter, so get or setter,
	 * that kind of stuff.*/
	@TrackTime
	public String retrieveSomething(){	//#1
		return "Dao1";
	}

}
```

So I create the annotation @TrackTime in package com.imh.spring.aop.springaop.aspect, and I define two things. One is I would want to use TrackTime only on methods, I don't want to use it on classes, and also the annotation information I would want it to be available at runtime, even when the program is executing, I would want to be able to have that information, because that is what is used to check and track the time for that specific method.

```java
package com.imh.spring.aop.springaop.aspect;

//#1: Methods
//#2: Runtime
public @interface TrackTime {
	
}
```

How do I do that ? There are a couple of annotations, so I can say @Target(ElementType.METHOD) to intercept methods, and @Retention(RetentionPolicy.RUNTIME) to be able to have this information on a method at runtime.

```java
package com.imh.spring.aop.springaop.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)		//Methods
@Retention(RetentionPolicy.RUNTIME)	//Runtime
public @interface TrackTime {
	
}
```

So now, I would want to use @TrackTime on the method *public String retrieveSomething()* in the Dao1 class. Once we have defined the annotation in the package com.imh.spring.aop.springaop.aspect TrackTime class, what we can do is use this annotation to define a Pointcut. So I'll copy the Qualified Name of TrackTime (com.imh.spring.aop.springaop.aspect.TrackTime), and I can go to the CommonJoinPointConfig class, and define a @Pointcut("@annotation(com.imh.spring.aop.springaop.aspect.TrackTime)"); be careful about the closing bracket when defining Pointcuts: (){};

This is how we would actually define a simple Pointcut. Now I can use the @TimeTrack annotation in the MethodExecutionCalculationAspect class, instead of the @Around("execution(* com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution()").

```java
package com.imh.spring.aop.springaop.aspect;

@Aspect			//AOP
@Configuration	//Configuration
public class MethodExecutionCalculationAspect {
		
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Around("execution(* com.imh.spring.aop.springaop.business.*.*(..))")							//Pointcut: old version
//	@Around("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution()")	//Pointcut: efficient version
	@Around("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.trackTimeAnnotation()")	
	public void around(ProceedingJoinPoint joinPoint) throws Throwable{
		
		//Before the invocation: startTime = x
		long startTime = System.currentTimeMillis();
				
		//I continue the execution: allow execution of method
	    	Object retVal = joinPoint.proceed();
		
		//After the invocation: end Time = y
		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Time taken by {} is {}", joinPoint, timeTaken);	//Print the interceptive calls
		
		return retVal;
	}
}
```

So instead of tracking every method in the business layer, we would be only tracking those methods which have the at @TrackTime in annotation defined on it. We now have at @TrackTime in annotation defined in method *public String retrieveSomething()* on the Dao1 class. Let's run the application and see what would happen.

```
Console output:
[2m2020-02-20 10:16:45.158[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.a.springaop.SpringAopApplication  [0;39m [2m:[0;39m Started SpringAopApplication in 0.742 seconds (JVM running for 1.107)
[2m2020-02-20 10:16:45.168[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mssAspect$$EnhancerBySpringCGLIB$$1c25ecc[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-20 10:16:45.168[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mssAspect$$EnhancerBySpringCGLIB$$1c25ecc[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao1.retrieveSomething())
[2m2020-02-20 10:16:45.172[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mnAspect$$EnhancerBySpringCGLIB$$eaada1ef[0;39m [2m:[0;39m Time taken by execution(String com.imh.spring.aop.springaop.data.Dao1.retrieveSomething()) is 3
[2m2020-02-20 10:16:45.172[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business1  [0;39m [2m:[0;39m In Business - Dao1
[2m2020-02-20 10:16:45.172[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$57e4268f[0;39m [2m:[0;39m after execution of execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething())
[2m2020-02-20 10:16:45.172[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$57e4268f[0;39m [2m:[0;39m execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething()) returned with value Dao1
[2m2020-02-20 10:16:45.172[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$264c4708[0;39m [2m:[0;39m Dao1
[2m2020-02-20 10:16:45.175[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mssAspect$$EnhancerBySpringCGLIB$$1c25ecc[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-20 10:16:45.175[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mssAspect$$EnhancerBySpringCGLIB$$1c25ecc[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao2.retrieveSomething())
[2m2020-02-20 10:16:45.177[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business2  [0;39m [2m:[0;39m In Business - Dao2
[2m2020-02-20 10:16:45.178[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$57e4268f[0;39m [2m:[0;39m after execution of execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething())
[2m2020-02-20 10:16:45.178[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$57e4268f[0;39m [2m:[0;39m execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething()) returned with value Dao2
[2m2020-02-20 10:16:45.178[0;39m [32m INFO[0;39m [35m5215[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$264c4708[0;39m [2m:[0;39m Dao2
```

You can see that the only thing for which the time is track, is for the Dao1 that executes *public String calculateSomething()*. If I search for time taken by execution, anywhere else it would not be there. So If I want to track more methods, let's say I would want to track the business method also, I can say @TimeTrack annotation over the *public String calculateSomething()* method on Business1 class, and track the time for this basic method (see #1).

```java
package com.imh.spring.aop.springaop.business;

@Service	//@Service	is for Business layer
public class Business1 {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Dao1 dao1;

	@TrackTime	//#1
	public String calculateSomething(){
		//Business Logic
		String value = dao1.retrieveSomething();
		logger.info("In Business - {}", value);
		return value;
	}
}
```

If your run the application you would see that now, @Tracktime would be executed twice, one in Dao1.retrieveSomething()) with time 4, and another one in Business1.calculateSomething()) with time 10.

```
Console output:
2020-02-20 10:18:40.085  INFO 5280 --- [  restartedMain] c.i.s.a.springaop.SpringAopApplication   : Started SpringAopApplication in 0.718 seconds (JVM running for 1.098)
2020-02-20 10:18:40.094  INFO 5280 --- [  restartedMain] sAspect$$EnhancerBySpringCGLIB$$e4eec7b5 :  Check for user access 
2020-02-20 10:18:40.095  INFO 5280 --- [  restartedMain] sAspect$$EnhancerBySpringCGLIB$$e4eec7b5 :  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao1.retrieveSomething())
2020-02-20 10:18:40.098  INFO 5280 --- [  restartedMain] nAspect$$EnhancerBySpringCGLIB$$cdda0ad8 : Time taken by execution(String com.imh.spring.aop.springaop.data.Dao1.retrieveSomething()) is 4
2020-02-20 10:18:40.099  INFO 5280 --- [  restartedMain] c.i.s.aop.springaop.business.Business1   : In Business - Dao1
2020-02-20 10:18:40.099  INFO 5280 --- [  restartedMain] nAspect$$EnhancerBySpringCGLIB$$cdda0ad8 : Time taken by execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething()) is 10
2020-02-20 10:18:40.099  INFO 5280 --- [  restartedMain] sAspect$$EnhancerBySpringCGLIB$$3b108f78 : after execution of execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething())
2020-02-20 10:18:40.099  INFO 5280 --- [  restartedMain] sAspect$$EnhancerBySpringCGLIB$$3b108f78 : execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething()) returned with value Dao1
2020-02-20 10:18:40.099  INFO 5280 --- [  restartedMain] lication$$EnhancerBySpringCGLIB$$978aff1 : Dao1
2020-02-20 10:18:40.102  INFO 5280 --- [  restartedMain] sAspect$$EnhancerBySpringCGLIB$$e4eec7b5 :  Check for user access 
2020-02-20 10:18:40.102  INFO 5280 --- [  restartedMain] sAspect$$EnhancerBySpringCGLIB$$e4eec7b5 :  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao2.retrieveSomething())
2020-02-20 10:18:40.104  INFO 5280 --- [  restartedMain] c.i.s.aop.springaop.business.Business2   : In Business - Dao2
2020-02-20 10:18:40.105  INFO 5280 --- [  restartedMain] sAspect$$EnhancerBySpringCGLIB$$3b108f78 : after execution of execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething())
2020-02-20 10:18:40.105  INFO 5280 --- [  restartedMain] sAspect$$EnhancerBySpringCGLIB$$3b108f78 : execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething()) returned with value Dao2
2020-02-20 10:18:40.105  INFO 5280 --- [  restartedMain] lication$$EnhancerBySpringCGLIB$$978aff1 : Dao2
```

So, if you want to implement a custom annotation and do some functionality on the methods which define that annotation, we look at how to do that using AOP in this specific example. We did a lot of different kinds of advices, we implemented @Around custom annotations, we implemented performance tracking, we used different kinds of advices, we understood all the different concepts related to AOP.

## Complete Code Example

##### /src/main/java/com/imh/spring/aop/springaop/aspect/TrackTime.java

```java
package com.imh.spring.aop.springaop.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)			//Methods: I would want to use TrackTime only on methods, I don't want to use it on classes.
@Retention(RetentionPolicy.RUNTIME)	//Runtime: I would want it to be available at run time even when the program is executing.
public @interface TrackTime {

}
```

---

##### /src/main/java/com/imh/spring/aop/springaop/aspect/CommonJoinPointConfig.java

```java
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
```

---

##### /src/main/java/com/imh/spring/aop/springaop/aspect/MethodExecutionCalculationAspect.java

```java
package com.imh.spring.aop.springaop.aspect;

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
//	@Around("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution()")	//Pointcut: efficient version
	@Around("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.trackTimeAnnotation()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
		
		//Before the invocation: startTime = x
		long startTime = System.currentTimeMillis();
				
		//I continue the execution: allow execution of method
	    Object retVal = joinPoint.proceed();
		
		//After the invocation: end Time = y
		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Time taken by {} is {}", joinPoint, timeTaken);	//Print the interceptive calls
		
		return retVal;
	}	
}
```