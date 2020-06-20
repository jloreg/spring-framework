## Step 08 - Quick summary of other Pointcuts 

In this step let's look at a few tips regarding the pointcuts. So if I want to create a method to intercept any layer, either business layer or data layer, you can call it *public void allLayerExecution(){}*, and define a pointcut using the method which we have already defined @Pointcut("execution(* com.imh.spring.aop.springaop.*.*.*(..))")
 
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
	
	//Works
	@Pointcut("execution(* com.imh.spring.aop.springaop.*.*.*(..))")
	public void allLayerExecution(){};
	
	/* #1: Combine the joinPoints doesn't work with @Around annotation, see MethodExecutionCalculationAspect class
	@Pointcut("com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.dataLayerExecution() && com.imh.spring.aop.springaop.aspect.CommonJoinPointConfig.businessLayerExecution()")	
	public void allLayerExecution2(){};
	
	@Pointcut("dataLayerExecution() && businessLayerExecution()")
	public void allLayerExecution3(){};
	*/
}
```

So this *public void allLayerExecution(){};* would intercept all the stuff related to this configuration (see #1). Right now will intercept to both dataLayer and businessLayer executions, here with *public void allLayerExecution2()* and here with *public void allLayerExecution3()*, so this is how you can actually even **combine the joinPoints**.

The other thing is you can also define Pointcuts by using beans names. So let's say I would want to intercept everything which is having a specific name, so bean named "dao" or bean starting with "*dao", or containing dao "*dao*". You can define a method called *public void beanContainingDao()*, saying at Pointcut the regular expression *@Pointcut("bean(*Dao*)")*. So here we are looking for containing "dao", this will look fo any bean which has the name "dao" in between.

```java
@Pointcut("bean(*dao*)")
public void beanContainingDao(){}
```

So, all method calls on beans containing "dao" in the name are matched by the Pointcut. So this would actually intercept any beans which I defined with this specific name. 

You can also intercept all the calls within the data layer by using something called within. So if I would want to intercept all calls within the data layer, all that I would need to do is *within(com.in28minutes.spring.aop.springaop.data..**#**)")*. It is very similar to whatever we have defined here in the dataLayerExecution() method (see #1). One of the advantages of use within, is that you don't need the expression starts with **#** as in *@Pointcut("execution(# com.imh.spring.aop.springaop.data.*.*(..))")*, so you can say Pointcut("within (com.in28minutes.spring.aop.springaop.data..#)"). With this expression I'm saying that within this package com.in28minutes.spring.aop.springaop.data..#, the Pointcut intercepts all the calls to the data layer.

```java
//#1
@Pointcut("within(com.in28minutes.spring.aop.springaop.data..*)")
public void dataLayerExecutionWithWithin(){}
```
In this step we looked at some of the other possibilities with Pointcuts. We looked it **within** bean and we looked at how can we **combine** JoinPoints, all this JoinPoints are actually defined by AspectJ and implemented by Spring AOP.

## Complete Code Example

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