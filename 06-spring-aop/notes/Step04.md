## Step 04 - Understand AOP Terminology - Pointcut, Advice, Aspect, Join Point, Weaving and Weaver

In this step let's play around with the pointcuts a little more. In the previous step used in execution point cut "execution ()". And we defined a simple pointcuts *# com.imh.spring.aop.springaop.business.#.#(..)*

So here we are saying @Before("execution(# com.imh.spring.aop.springaop.business.#.#(..))"), in this package (**com.imh.spring.aop.springaop.business**), any class (com.imh.spring.aop.springaop.business.**#**), we would want to intercept any method which is called (com.imh.spring.aop.springaop.business.#.**#**), with any kind of arguments (com.imh.spring.aop.springaop.business.#.#**(..)**)). And I don't care about the return type of it as well (**#** com.imh.spring.aop.springaop.business.#.#(..))). So that's what we have defined, and we saw that this *execution(# com.imh.spring.aop.springaop.business.#.#(..))* is intercepted to business calls. 

Let's see a few more examples of this. So now I'm removing business, so what it does, is now when there ar two dots next to each other, *execution(# com.imh.spring.aop.springaop..#.#(..))*, what would happen is this would start intercepting all calls inside this sub *package com.imh.spring.aop.springaop*. So inside this springaop, any calls will be intercepted, let's see what would happen now.

```
console output:
[2m2020-02-17 11:11:16.548[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.a.springaop.SpringAopApplication  [0;39m [2m:[0;39m Started SpringAopApplication in 0.652 seconds (JVM running for 1.035)
[2m2020-02-17 11:11:16.550[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c96327d[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-17 11:11:16.550[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c96327d[0;39m [2m:[0;39m  Allowed execution for execution(void com.imh.spring.aop.springaop.SpringAopApplication.run(String[]))
[2m2020-02-17 11:11:16.556[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c96327d[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-17 11:11:16.556[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c96327d[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.business.Business1.calculateSomething())
[2m2020-02-17 11:11:16.558[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c96327d[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-17 11:11:16.559[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c96327d[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao1.retrieveSomething())
[2m2020-02-17 11:11:16.561[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business1  [0;39m [2m:[0;39m In Business - Dao1
[2m2020-02-17 11:11:16.561[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$71201ab9[0;39m [2m:[0;39m Dao1
[2m2020-02-17 11:11:16.561[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c96327d[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-17 11:11:16.561[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c96327d[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.business.Business2.calculateSomething())
[2m2020-02-17 11:11:16.564[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c96327d[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-17 11:11:16.564[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$4c96327d[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao2.retrieveSomething())
[2m2020-02-17 11:11:16.566[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business2  [0;39m [2m:[0;39m In Business - Dao2
[2m2020-02-17 11:11:16.566[0;39m [32m INFO[0;39m [35m5893[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$71201ab9[0;39m [2m:[0;39m Dao2
```

You can see that a lot more things are getting intercepted, so you can see, check for user access, allowed execution for SpringAopApplication.run. So this is the original run of the SpringBoot application, that is getting intercepted because that's inside the package come in com.imh.spring.aop.springaop.SpringAopApplication.run, and also you see that the business method call is getting intercepted. You would see that the dao method call is getting intercepted, the business method call, and the data is also getting intercepted. So it's very powerful the way you define your pointcut is very very important. You don't want to be wrong with how you define your pointcut.

So here *"execution(# com.imh.spring.aop.springaop..#.#(..))")*, and when I put business *"execution(# com.imh.spring.aop.business..#.#(..))")*, I would intercept the business stuff, when I now put data in here *"execution(# com.imh.spring.aop.data..#.#(..))")*, and I call the business method, the business method intern will invoke the data method. So now, If you run the application, you would see that only the data method to get intercepted, so you can see that now there is only the data methods which are getting the dao methods which are getting the interceptions.

```
console output:
[2m2020-02-17 09:47:35.387[0;39m [32m INFO[0;39m [35m4108[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.a.springaop.SpringAopApplication  [0;39m [2m:[0;39m Started SpringAopApplication in 0.651 seconds (JVM running for 1.041)
[2m2020-02-17 09:47:35.390[0;39m [32m INFO[0;39m [35m4108[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b6669ec8[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-17 09:47:35.390[0;39m [32m INFO[0;39m [35m4108[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b6669ec8[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao1.retrieveSomething())
[2m2020-02-17 09:47:35.397[0;39m [32m INFO[0;39m [35m4108[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business1  [0;39m [2m:[0;39m In Business - Dao1
[2m2020-02-17 09:47:35.397[0;39m [32m INFO[0;39m [35m4108[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$daf08704[0;39m [2m:[0;39m Dao1
[2m2020-02-17 09:47:35.397[0;39m [32m INFO[0;39m [35m4108[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b6669ec8[0;39m [2m:[0;39m  Check for user access 
[2m2020-02-17 09:47:35.398[0;39m [32m INFO[0;39m [35m4108[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36msAspect$$EnhancerBySpringCGLIB$$b6669ec8[0;39m [2m:[0;39m  Allowed execution for execution(String com.imh.spring.aop.springaop.data.Dao2.retrieveSomething())
[2m2020-02-17 09:47:35.401[0;39m [32m INFO[0;39m [35m4108[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.aop.springaop.business.Business2  [0;39m [2m:[0;39m In Business - Dao2
[2m2020-02-17 09:47:35.401[0;39m [32m INFO[0;39m [35m4108[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$daf08704[0;39m [2m:[0;39m Dao2
```
One of the things I would recommend you to do is to try and play around with this pointcut *"execution(# com.imh.spring.aop.springaop..#.#(..))")*, so try and have different packages in here, create different beans and try an play around with it.

---

Let's do a quick review of the terminology which is used by AOP. 

One of the things which I've been using is Pointcut. Pointcut is this expression *execution(# com.imh.spring.aop.springaop..#.#(..))*, so the expression which defines what kind of method I would want to intercept, **it's called a pointcut**.

The second important terminology is this. This is called an advice. What should I do when I do the interception. So I intercepted a method called, what should I do.**The logic here inside the run method is called advice**. So this is basically what we are giving as the advice, do this (#1 and #2), thats the advice we are giving.

```java
public void before(JoinPoint joinPoint){	
	//Advice
	//#1 - Implement the check for user access here. 
	logger.info(" Check for user access ");
	//#2 - What to do if everything is successful ?
	logger.info(" Allowed execution for {}", joinPoint);	//Print the interceptive calls
}
```
**The combination of pointcut and advice is called an Aspect**. So an Aspect is a combination of your jointPoint plus your advice. What kind of methods to intercept plus what to do, thats your aspect. 
 
 The last important terminology is the joinPoint. The JoinPoint is specific interception of a method call. So the Business1.class that calculate something is getting called, it's being intercepted, and that data is present inside the joinPoint; joinPoint is the specific execution instance. So if this *execution(# com.imh.spring.aop.springaop..#.#(..))* is intercepting hundred method calls, then there would be hundred jointPoints. 
 
Process where this whole thing *execution(# com.imh.spring.aop.springaop..#.#(..))*  gets viewed around your code is called Weaving. So what is happening here is, when I'm defining this @Aspect, the AOP Spring Framework is ensuring that this *execution(# com.imh.spring.aop.springaop..#.#(..))* is getting  executed at the right moment. **This process is called Viewing**. And the framework which does this is called the viewer. So things which we are talking about are Weaving and Weaver, the process of implementing the AOP around your method calls, it's called Weaving, and the framework which implements it is called Weaver.
 
 So let's quickly revise all these concepts. 
 
 This *execution(* com.imh.spring.aop.springaop..*.*(..))* is a pointcut, it defines what kind of methods we would want to intercept. What kind of executions we would want to intercept.
 
This is a Advice, "what do we want to do", a combination of pointcut and advice is called an Aspect. 
```java
//Advice
//#1 - Implement the check for user access here. 
logger.info(" Check for user access ");
//#2 - What to do if everything is successful ?
```

A specific execution interception, it's called a jointPoint, and the framework which does the entire logic of making sure that the aspect is invoked the right point is called the Weaver, and the process of doing that is called Weaving, that's all.
```java
public void before(JoinPoint joinPoint){
	//Advice
	//#1 - Implement the check for user access here. 
	logger.info(" Check for user access ");
	//#2 - What to do if everything is successful ?
	logger.info(" Allowed execution for {}", joinPoint);	//Print the interceptive calls
}
```
So those are all the important things that you would need to understand about AOP. In this step what will look that is the pointcut and also some of the terminology which is associated with AOP.

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
	//execution(* PACKAGE.*.*(..)) --> the type of return to be processed + darkspace + the name of the package + the name of the class .* + the name of the method .* + the argument of the method (..) 
	//Weaving & Weaver
	@Before("execution(* com.imh.spring.aop.springaop..*.*(..))")	//Pointcut
	public void before(JoinPoint joinPoint){
		//Advice
		//#1 - Implement the check for user access here. 
		logger.info(" Check for user access ");
		//#2 - What to do if everything is successful ?
		logger.info(" Allowed execution for {}", joinPoint);	//Print the interceptive calls
	}
}
```