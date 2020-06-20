## Step 03 - Defining an @Before advice

In this step will define an aspect to intercept  the call to the business layer. In the previous steps we had set up our examples up. So let's now create a new class called BeforeAspect, and let's call this I'd want to intercept before the call. For now let's focus on what we would want to do inside this new class.

The fist thing I need to define is this UserAccessAspect.class is some configuration using @Configuration, and you need to say this is related to AOP using @Aspect annotation.  The other thing I would need to do is I would need to define, what kind of methods I would want disintercept, so what kind of method calls I would want to intercept. And then I would need to define a method called *public void before*, and in here I can define the logic of what to do when I intercept the method.

I need to define that this basic method *public void before*, needs to be called before execution of something so I will say @Before, and over here to do before method what I would need to pass in is something called a pointcut. I need to define which methods I would want to intercep. Let's say I would want to intercept the call to *public String calculateSomething()*

The format for defining something to intercept calls inside a specific package, is this * defines that any return type, so I want to intercept all methods irrespective of their return type in a specific package PACKAGE, any class in a specific package PACKAGE.#., and I would want to intercept all methods calls irrespective of their arguments .#(..), so that's basically what we are defining in execution(# PACKAGE.#.#(..)), so I'll take this and put it inside the @Before annotation.

```java
@Aspect			//AOP
@Configuration	//Configuration
public class UserAccessAspect {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//What kind of method calls I would intercept
	//execution(* PACKAGE.*.*(..)) --> darkspace defines return type + the name of the PACKAGE + the name of the class .* + the name of the method irrespective of their arguments #.#(..))
	
	@Before("execution(* com.imh.spring.aop.springaop.business.*.*(..))")
	public void before(){
		//#1 - Implement the check for user access here. 
		logger.info(" Check for user access ");
		//#2 - What to do if everything is successful ?
		logger.info(" Allowed execution for {}", joinPoint);	//Print the interceptive calls
	}
}
```

If you run the SpringAopApplication that we created, you can see that this SpringAOP application is running, and you can see that it prints intercepted method called in the run method. So two times it printed intercepted method here, two business calls it intercept it.

```
Console output:
[2m2020-02-14 22:22:28.132[0;39m [32m INFO[0;39m [35m9987[0;39m [2m---[0;39m [2m[           main][0;39m [36msAspect$$EnhancerBySpringCGLIB$$2b5e14dd[0;39m [2m:[0;39m  Check for user access 
2020-02-14 22:22:28.136  INFO 9987 --- [           main] c.i.s.aop.springaop.business.Business1   : In Business - Dao1
2020-02-14 22:22:28.136  INFO 9987 --- [           main] ication$$EnhancerBySpringCGLIB$$cfb29e39 : Dao1
2020-02-14 22:22:28.139  INFO 9987 --- [           main] sAspect$$EnhancerBySpringCGLIB$$2b5e14dd :  Check for user access 
2020-02-14 22:22:28.142  INFO 9987 --- [           main] ication$$EnhancerBySpringCGLIB$$cfb29e39 : Dao2
```

The problem is I have no idea all of the details. So how do I get the details ? I can add in an argument called JoinPoint,  *public void before(JoinPoint joinPoint)*, and saying *logger.info(" Allowed execution for {}", joinPoint);*. 

```java
	@Before("execution(* com.imh.spring.aop.springaop.business.*.*(..))")
	public void before(JoinPoint joinPoint){
		//#1 - Implement the check for user access here. 
		logger.info(" Check for user access ");
		//#2 - What to do if everything is successful ?
		logger.info(" Allowed execution for {}", joinPoint);	//Print the interceptive calls
	}
```

If I run the application again, It showns the entire method that is being intercepted.  So you can see the entire thing in here, so it's saying the intercepted method calls it actually to be named as it. The intercepted method call is com.imh.spring.aop.springaop.business.Business1.calculateSomething(), and com.imh.spring.aop.springaop.business.Business2.calculateSomething(). You can see that we are intercepting the call and printing something out.

```
[2m2020-02-14 22:33:39.967[0;39m [32m INFO[0;39m [35m10362[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36meAspect$$EnhancerBySpringCGLIB$$bd52f628[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-14 22:33:39.967[0;39m [32m INFO[0;39m [35m10362[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36meAspect$$EnhancerBySpringCGLIB$$bd52f628[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething())
[2m2020-02-14 22:33:39.974[0;39m [32m INFO[0;39m [35m10362[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business1  [0;39m [2m:[0;39m In Business - Dao1
[2m2020-02-14 22:33:39.974[0;39m [32m INFO[0;39m [35m10362[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$6f951274[0;39m [2m:[0;39m Dao1
[2m2020-02-14 22:33:39.975[0;39m [32m INFO[0;39m [35m10362[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36meAspect$$EnhancerBySpringCGLIB$$bd52f628[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-14 22:33:39.975[0;39m [32m INFO[0;39m [35m10362[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36meAspect$$EnhancerBySpringCGLIB$$bd52f628[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething())
[2m2020-02-14 22:33:39.978[0;39m [32m INFO[0;39m [35m10362[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business2  [0;39m [2m:[0;39m In Business - Dao2
[2m2020-02-14 22:33:39.978[0;39m [32m INFO[0;39m [35m10362[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$6f951274[0;39m [2m:[0;39m Dao2
```

The thing which we are doing is we use that before annotation. So this @Before annotation intercepts before the call has happened (the call to any class and method defined in com.imh.spring.aop.springaop.business). So this *public void before(JoinPoint joinPoint)* method is invoke before the actual method is really invoked (method defined in any class of the package com.imh.spring.aop.springaop.business). 

Tipically, things which should be using the before aspect, are things to check for acces. So for example I would want to check if a user has the right access. So I would want to make sure that the user has the right access before I would allow him to execute the method in those kind of situations, what I'll do is implement the check for user access before (see #1), and then if everything is successful, then allowed execution for this method (see @#2).

If you do user access check by using AOP, the thing is, thisis applicable to everything in the business layer, you don't really need to implement the access check at every point, in every method. This is what AOP loves us to do. So here security we are defining at one place. So we are defining at a single place and that is applicable for all the methods in the business layer.

Let's run this and see what would happen. It would just print check for user access before all the business methods; every time we checked for the access and allow the execution in.

```
[2m2020-02-15 11:06:25.445[0;39m [32m INFO[0;39m [35m3751[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.a.springaop.SpringAopApplication  [0;39m [2m:[0;39m Started SpringAopApplication in 0.828 seconds (JVM running for 1.341)
[2m2020-02-15 11:06:25.447[0;39m [32m INFO[0;39m [35m3751[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$a0ec9a62[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-15 11:06:25.447[0;39m [32m INFO[0;39m [35m3751[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$a0ec9a62[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething())
[2m2020-02-15 11:06:25.455[0;39m [32m INFO[0;39m [35m3751[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business1  [0;39m [2m:[0;39m In Business - Dao1
[2m2020-02-15 11:06:25.455[0;39m [32m INFO[0;39m [35m3751[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$c576829e[0;39m [2m:[0;39m Dao1
[2m2020-02-15 11:06:25.456[0;39m [32m INFO[0;39m [35m3751[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$a0ec9a62[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-15 11:06:25.456[0;39m [32m INFO[0;39m [35m3751[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$a0ec9a62[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething())
[2m2020-02-15 11:06:25.459[0;39m [32m INFO[0;39m [35m3751[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business2  [0;39m [2m:[0;39m In Business - Dao2
[2m2020-02-15 11:06:25.459[0;39m [32m INFO[0;39m [35m3751[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$c576829e[0;39m [2m:[0;39m Dao2
```

## Complete Code Example

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
	//execution(* PACKAGE.*.*(..)) --> darkspace defines return type + the name of the PACKAGE + the name of the class .* + the name of the method irrespective of their arguments #.#(..))
	
	@Before("execution(* com.imh.spring.aop.springaop.business.*.*(..))")	//
	public void before(JoinPoint joinPoint){
		
		//#1 - Implement the check for user access here. 
		logger.info(" Check for user access ");
		//#2 - What to do if everything is successful ?
		logger.info(" Allowed execution for {}", joinPoint);	//Print the interceptive calls
	}
}
```