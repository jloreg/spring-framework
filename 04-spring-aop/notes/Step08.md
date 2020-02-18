## Step 08 - Quick summary of other Pointcuts 

In this step let's look at a few tips.

So if I want to create a method to intercept any layer, so either business layer or data layer, or you can call it allLayer execution, you can actually define a pointcut using the method which we have already defined.

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
	
	@Pointcut("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution")
	public void allLayerExecution(){};
}
```

00:50
//Pointcut or joinPoints ??
So this would intercept all this stuff related to this configuration. Right now will intercept to both dataLayer and businessLayer executions. So this is how you can actually even combine the joinPoints.  And here I'am using and to combine the pointcuts. The other thing is you can also define pointcuts by using being names.

00:58
//Revisar bien
So let's say I would want to intercetp everything which is having a specific name, so bean named dao or bean. You can define a bean name method called *public void beanContainingDao()*, saying at pointcut you can say bean, and you can put in the regular expression *@Pointcut("bean(*Dao*)")*. So here we are looking for containing dao this to look fo any bean which has the name "Dao" in between.

```java
@Pointcut("bean(*Dao*)")
public void beanContainingDao(){}
```

All method calls on beans containing dao in the name are mached by the Pointcut. So this would actually intercept any beans which I defined with this specific name. 


You can also intercept all the calls within the data layer by using something called within. So I would want to intercept all calls within the data layer that I would need to do is within(com.in28minutes.spring.aop.springaop.data..**#**. It is very similar to whatever we have defined in here in dataLayerExecution(). This is using whitin here, one of the things within is you don't need that start # within @Pointcut("within (**#** com.in28minutes.spring.aop.springaop.data..*)"). So I'm saying within this package intercept all the calls.

```java
@Pointcut("within(com.in28minutes.spring.aop.springaop.data..*)")
public void dataLayerExecutionWithWithin(){}
```
//JoinPoint or pointcuts ?
02:23
In this step we looked at some of the other possibilities with pointcuts. We looked it within bean and we looked at how can we combine join point, all this join points are actually defined by Aspect and implemented by Spring AOP.

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
```